/**
 * @author Valar Morghulis
 * @Date 2023/5/21
 */
package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.Carinfo;
import com.project.smartcharge.pojo.Device;
import com.project.smartcharge.service.*;
import com.project.smartcharge.system.respon.BillResponse;
import com.project.smartcharge.system.respon.DeviceResponse;
import com.project.smartcharge.system.respon.Response;
import com.project.smartcharge.system.util.FeeCount;
import com.project.smartcharge.system.util.MyDate;
import com.project.smartcharge.system.util.MyToken;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;

/**
 * 复杂请求类接口的具体实现类，用于用户提出复杂请求的业务处理
 */
@EnableAsync
@Service("complexService")
@ComponentScan(basePackages = "com.project.smartcharge.config")

public class ComplexServiceImpl implements ComplexService {

    /**
     * 满冲模式的监听队列，用于处理订单请求
     */
    /*private static final BlockingDeque<Integer> lowModeBlockingDeque = new
    LinkedBlockingDeque<>(6);
    private static final BlockingDeque<Integer> fastModeBlockingDeque =
            new LinkedBlockingDeque<>(6);
*/
    private static final Logger logger = LoggerFactory.getLogger(ComplexServiceImpl.class);

    Date sysDate;
    Date setDate;
    int[] deviceWait = new int[5];

    @Resource
    BlockingDeque<Integer> fastModeBlockingDeque;

    @Resource
    BlockingDeque<Integer> lowModeBlockingDeque;

    @Resource
    BillService billService;
    @Resource
    DeviceService deviceService;
    @Resource
    UserService userService;
    @Resource
    CarService carService;


    @Autowired
    ExecutorService executor;

    /**
     * 开启服务前，先启动两个线程监听类
     */
    @PostConstruct
    private void init() {
        logger.info("检测到服务开启,线程启动中。。。。。");
        Arrays.fill(deviceWait, -1);
        executor.execute(() -> monitorBillQueue(true, fastModeBlockingDeque));
        executor.execute(() -> monitorBillQueue(false, lowModeBlockingDeque));
        //系统初始时间
        setDate = new Date();
        sysDate = setDate;


    }

    /**
     * 在销毁服务之前进行资源回收和清理
     *
     * @throws InterruptedException 阻断
     */
    @PreDestroy
    private void preDestroy() throws InterruptedException {

        logger.info("检测到服务关闭,线程销毁中");
        //进行所有未完成的订单结算
        List<Bill> bills = billService.selectChargingBill();
        for (Bill bill : bills) {
            Integer billID = bill.getBillID();
            Integer userID = bill.getUserID();
            cancelBill(userID, billID);
            Thread.sleep(2000);
        }
        executor.shutdown();
    }


    /**
     * 根据userID展示用户的车辆信息
     *
     * @param authorization 用户令牌
     * @return 用户车辆的JSON字符串
     */
    @Override
    public String selectCarByUserID(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        if (userID != -1) {
            Carinfo carinfo = carService.selectCarByUserID(userID);
            if (carinfo != null)
                return Response.NormalResponse.generate(true, 0, "查询成功",
                        JSON.toJSONString(carinfo));
            return Response.NormalResponse.generate(true, 400, "用户没有车辆!", "");
        }
        return Response.SimpleResponse.generate(true, 401, "用户身份已过期");
    }


    /**
     * 用来检测该充电桩是否充电完成或者是被中途取消
     *
     * @param bill   订单对象
     * @param deviceID 接受请求的充电桩id
     */
    void monitorChargingStatus(Bill bill, int deviceID) {


        //充电开始时间,yyyy-MM-dd HH:mm类型的字符串
        String chargeStartTimeString = bill.getChargeStartTime();
        //HH:mm类型的字符串
        String chargeDuration = bill.getChargeDuration();
        //预计充电结束时间,yyyy-MM-dd HH:mm类型的字符串
        String chargeEndTimeString = MyDate.addTimeInDateFormatString(chargeStartTimeString,
                chargeDuration);
        //检测时间状态
        try {
            Date durationDate = MyDate.getNeedTimeInDate(chargeEndTimeString);
            //如果未达到终止时间，或者订单没有被取消则继续进行
            while (getTime().before(durationDate) && !billService.selectBillByPrimaryKey(bill.getBillID()).getIsDone()) {
                //每十秒钟检测一次
                Thread.sleep(5000);
            }
            cancelBill(bill.getUserID(), bill.getBillID());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void monitorBillQueue(boolean chargeMode, BlockingDeque<Integer> BlockingDeque) {
        String name = Thread.currentThread().getName();
        if (chargeMode)
            logger.info(name + " 即将开始监测队列 fastModeBlockingDeque");
        else
            logger.info(name + " 即将开始监测队列 lowModeBlockingDeque");

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //获取空闲充电桩
            List<Device> devices = deviceService.selectFreeDeviceByDeviceType(chargeMode);
            logger.info(name + " 查询了充电桩空闲表单一次");
            //先检查当前模式下是否有等待区空闲的充电桩
            //如果充电桩有空闲，就从等待区选择相应订单进行执行
            if (!devices.isEmpty()) {
                try {
                    //拿到该订单序号
                    logger.info(name + " 即将调取订单队列");

                    //充电桩等待区为空，直接从等待队列调度
                    Integer it = BlockingDeque.takeFirst();

                    logger.info(name + "拿到订单 " + it + " 开始处理");
                    Thread.sleep(2000);
                    executor.execute(() -> startCharging(it,
                            devices.get(0).getDeviceID()));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    logger.info(name + "没有获取到空闲充电桩,即将休眠");
                    //当前线程休息十秒后继续检测
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        }

    }


    /**
     * 条件符合,开始充电
     *
     * @param billID   当前需要处理的订单id
     * @param deviceID 接受请求的充电桩id
     */
    //D B
    private void startCharging(int billID, Integer deviceID) {
        String name = Thread.currentThread().getName();

        logger.info(name + "开始处理预订单 id 为 " + billID + " " + "的订单请求");
        //拿到三大业务相关对象
        //首先拿到Bill对象
        Bill bill = billService.selectBillByPrimaryKey(billID);
        logger.info(name + "拿到了订单： " +bill.toString());
        //然后再找到相应的充电桩
        Device device = deviceService.selectDeviceByID(deviceID);
        logger.info(name + "拿到了设备： " +device.toString());
        //然后找到对应的汽车
        Carinfo carinfo = carService.selectCarByUserID(bill.getUserID());
        logger.info(name + "拿到了汽车： " +carinfo.toString());

        Boolean chargeMod = bill.getChargeMod();
        //设置当前汽车状态(当前充电桩id为Bill中的充电桩id，设置等待状态为马上开始充电=1)
        carinfo.setCarRank(1);
        carinfo.setDeviceID(deviceID);
        carinfo.setWaitingStatu((byte) 1);
        carinfo.setChargeMode(chargeMod);
        //设置为正在充电
        device.setChargeStatu(true);

        //设置bill的开始时间和结束时间和持续时间
        //首先拿到bill的充电模式

        Integer chargeAmount = bill.getChargeAmount();
        //持续时间
        String s = FeeCount.calculateChargeDurationInString(chargeMod, chargeAmount);
        //当前时间
        String nowTimeInString = MyDate.getNeedTimeInString(getTime());
        logger.info(name + " 拿到当前系统时间为 "+ nowTimeInString);
        bill.setChargeDuration(s);
        bill.setChargeStartTime(nowTimeInString);
        bill.setDeviceID(deviceID);

        //然后更新表
        deviceService.updateDeviceByPrimaryKeySelective(device);
        carService.updateByPrimaryKeySelective(carinfo);
        billService.updateBillByPrimaryKeySelective(bill);

        monitorChargingStatus(bill , deviceID);
    }

    /*
     * ---------------------一下对订单发出的复杂请求（涉及到多表查询）------------------------
     * ---------------------一下对订单发出的复杂请求（涉及到多表查询）------------------------
     * ---------------------一下对订单发出的复杂请求（涉及到多表查询）------------------------
     * **/

    /**
     * 用户提交订单请求
     *
     * @param amount        充电度数申请
     * @param fast          此次请求是否是快充
     * @param totalAmount   车辆电池大小
     * @param authorization token令牌
     * @return 更新成功的JSON字符串
     */
    @Override
    public String createBill(int amount, boolean fast, int totalAmount, String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        //判断是否身份过期
        if (userID == -1)
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
        Bill userChargingBill = billService.selectChargingBillByUserID(userID);
        if (userChargingBill != null) {
            return Response.SimpleResponse.generate(true, 500, "已存在充电请求");
        }

        //确定该订单的状态,先装入bill表
        int l = billService.selectMaxBillID();
        String duration = FeeCount.calculateChargeDurationInString(fast, amount);
        Bill bill = new Bill();
        bill.setChargeAmount(amount);
        bill.setChargeDuration(duration);
        bill.setBillID(l + 1);
        bill.setIsDone(false);
        bill.setChargeMod(fast);
        bill.setUserID(userID);

        boolean b = billService.addBillByPrimaryKeySelective(bill);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (b)
        //如果是快充，则查看当前快充的等待队列
        {
            Carinfo carinfo = carService.selectCarByUserID(userID);
            carinfo.setWaitingStatu((byte) 0);
            carinfo.setChargeMode(false);
            carinfo.setCarRank(carService.selectMaxCarRankByMode(fast) + 1);
            if (fast) {
                //先更新汽车
                if (fastModeBlockingDeque.size() != 6) {
                    carService.updateByPrimaryKeySelective(carinfo);

                    //将该订单id加入到等待队列
                    fastModeBlockingDeque.add(l + 1);

                    return Response.SimpleResponse.generate(true, 0,
                            "订单提交成功,您的排位位数为: " + (carinfo.getCarRank()));
                } else {
                    return Response.SimpleResponse.generate(true, 500, "等待队列已满，请稍后重试");
                }
            } else {
                if (lowModeBlockingDeque.size() != 6) {
                    carService.updateByPrimaryKeySelective(carinfo);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    //将该订单id加入到等待队列
                    lowModeBlockingDeque.add(l + 1);
                    return Response.SimpleResponse.generate(true, 0,
                            "订单提交成功,您的排位位数为:" + (carinfo.getCarRank()));
                } else {
                    return Response.SimpleResponse.generate(true, 500, "等待队列已满，请稍后重试");
                }
            }
        }
        return Response.SimpleResponse.generate(true, 401, "12345645");
    }

    /**
     * @param authorization 令牌token
     * @return 是否删除成功的JSON字符串
     */
    @Override
    public String deleteCurrentBill(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);

        Bill bill = new Bill();
        bill = billService.selectChargingBillByUserID(userID);
        if (userID == -1)
            return Response.SimpleResponse.generate(true, 401, "身份已过期");
        return cancelBill(userID, bill.getBillID());

    }

    /**
     * 获取指定订单
     *
     * @param authorization 令牌token
     * @param billID        订单ID
     * @return JSON字符串
     */
    @Override
    public String selectBillByid(String authorization, Integer billID) {
        Bill bill = billService.selectBillByPrimaryKey(billID);
        if (bill == null)
            return Response.SimpleResponse.generate(true, 404, "未找到订单");
        Double chargeFee = bill.getChargeFee();
        Double totalFee = bill.getTotalFee();
        Boolean chargeMod = bill.getChargeMod();
        Boolean isDone = bill.getIsDone();
        Integer chargeAmount = bill.getChargeAmount();
        String chargeStartTime = bill.getChargeStartTime();
        String chargeEndTime = bill.getChargeEndTime();
        String chargeDuration = bill.getChargeDuration();
        String creat_at = MyDate.getNeedTimeInString(bill.getCreated_at());
        Integer userID = bill.getUserID();
        Integer deviceID = bill.getDeviceID();
        String update_at = MyDate.getNeedTimeInString(bill.getUpdated_at());

        BillResponse.CheckBillByIDResp checkBillByIDResp = new BillResponse.CheckBillByIDResp();

        checkBillByIDResp.setId(billID);
        checkBillByIDResp.setChargeEndTime(chargeEndTime);
        checkBillByIDResp.setChargeStartTime(chargeStartTime);
        checkBillByIDResp.setServiceFee(chargeFee);
        checkBillByIDResp.setTotalFee(totalFee);
        checkBillByIDResp.setChargeAmount(chargeAmount);
        checkBillByIDResp.setCreated_at(creat_at);
        checkBillByIDResp.setUpdated_at(update_at);
        checkBillByIDResp.setPileId(deviceID);
        checkBillByIDResp.setUserId(userID);

        return JSON.toJSONString(checkBillByIDResp);
    }

    /**
     * 根据用户的id取消他的订单（包括充电区退出或者等待区退出）
     *
     * @param userID 传入的userID
     * @param billID 传入的billID
     * @return 成功返回true 否则false
     */
    @Override
    public String cancelBill(int userID, int billID) {
        //查看该汽车
        Carinfo carinfo = carService.selectCarByUserID(userID);
        //查看该订单
        Bill bill = billService.selectBillByPrimaryKey(billID);
        //查看该设备
        //
        Device device=null;
        if (bill.getDeviceID() != null) {
            device = deviceService.selectDeviceByID(bill.getDeviceID());
        }
        //如果没有对应的订单
        if (carinfo.getWaitingStatu() == -1) {
            return Response.SimpleResponse.generate(true, 500, "取消错误,请检查相应的订单更新状态");
        }
        //如果是在等待区退出
        else if (carinfo.getWaitingStatu() == 0) {

            Boolean chargeMode = carinfo.getChargeMode();
            //int deviceID = carinfo.getDeviceID();
            //更新阻塞队列
            //是否是快充订单
            /*if (deviceID != 0) {
                deviceWait[deviceID - 1] = -1;
            }*/
            //如果是快充订单
            if (chargeMode)
                fastModeBlockingDeque.removeFirstOccurrence(billID);
                //如果不是
            else
                lowModeBlockingDeque.removeFirstOccurrence(billID);

            //更新订单
            boolean b = billService.cancelChargingRequestInWaiting(bill, getTime());

            //更新汽车
            boolean b1 = carService.cancelChargingRequestInWaiting(carinfo);

            return Response.SimpleResponse.generate(b && b1, 0, "订单取消成功，如产生费用，请结算您的剩余费用");
        }
        //如果是在充电区退出
        else {
            //立即结束相关订单
            Boolean chargeMode = carinfo.getChargeMode();

            //对订单的更新
            boolean b1 = billService.cancelChargingRequestInCharging(bill, chargeMode, getTime());

            boolean b2 = carService.cancelChargingRequestInCharging(carinfo);

            boolean b = deviceService.cancelChargingRequestInCharging(bill, device, getTime());

            if (b && b1 && b2)
                return Response.SimpleResponse.generate(true, 0, "订单已结束，如产生费用，请结算您的剩余费用");

        }
        return Response.SimpleResponse.generate(false, 400, "");
    }

    /**
     * 用户注册车辆信息
     *
     * @param authorization token令牌
     * @param carinfo       车辆信息
     * @return 是否注册成功的JSON字符串
     */
    @Override
    public String carRegister(String authorization, Carinfo carinfo) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        //如果令牌没有过期
        if (userID != -1) {
            carinfo.setUserID(userID);
            carinfo.setCarRank(-1);
            carinfo.setWaitingStatu((byte) -1);
            boolean b = carService.carRegister(carinfo);
            return Response.SimpleResponse.generate(b, 0, "您的汽车注册成功");
        } else
            return Response.SimpleResponse.generate(true, 401, "身份已过期");
    }

    /**
     * 用户删除自己对应的车辆信息
     *
     * @param authorization 令牌，
     * @return JSON返回
     */
    @Override
    public String carDeleteByUserID(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        if (userID == -1)
            Response.SimpleResponse.generate(true, 401, "身份认证已过期");
        boolean b = carService.carDeleteByUserID(userID);
        if (b) {
            return Response.SimpleResponse.generate(b, 0, "删除成功");
        }
        return Response.SimpleResponse.generate(true, 500, "删除失败,您还未注册汽车或者汽车正忙!");
    }

    /**
     * 接受ping请求
     *
     * @return pong
     */
    @Override
    public String ping() {
        return Response.SimpleResponse.generate(true, 0, "pong");
    }

    /**
     * 获取用户当前的充电排队号码
     *
     * @param authorization 令牌token
     * @return 当前查询结果的JSON队列
     */
    @Override
    public String selectCarRank(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        if (userID != -1) {
            int carRank = carService.selectCarRankByUserID(userID);
            return Response.SimpleResponse.generate(true, 0, "查询成功,当前您的汽车排位是: " + carRank);
        }
        return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
    }

    /**
     * 更新系统时间
     *
     * @param time          传入的参数
     * @param authorization 令牌
     * @return 更新是否成功
     */
    @Override
    public String updateTime(Long time, String authorization) {
        int i = MyToken.parseJWTGetUserCode(authorization);
        if (i == -1)
            return Response.SimpleResponse.generate(true, 401, "身份已过期");
        else if (i != 2)
            return Response.SimpleResponse.generate(true, 403, "权限不足");
        Date setDate = new Date(time);
        if (sysDate.after(setDate))
            return Response.SimpleResponse.generate(true, 500, "时间无法倒退，设置失败");


        sysDate = setDate;
        this.setDate = new Date();
        return Response.SimpleResponse.generate(true, 0, "修改成功");


    }

    /**
     * 当前时间作为接口
     *
     * @return 当前时间
     */
    @Override
    public String getTimeByInterface() {
        long diff = new Date().getTime() - setDate.getTime();
        Date date = new Date(sysDate.getTime() + diff);
        return Response.timeResponse.generate(true, date);
    }

    /**
     * 获取当前系统日期对象
     *
     * @return 系统日期对象sysDate
     */
    private Date getTime() {
        long diff = new Date().getTime() - setDate.getTime();
        Date date = new Date(sysDate.getTime() + diff);
        return date;
    }

    /**
     * 用户在等待区中发起更改充电模式请求
     *
     * @param bill 预更新的订单对象
     * @return 对应结果的JSON字符串
     */
    @Override
    public String updateBillByMode(Bill bill, String authorization) {
        //首先查看修改后的订单
        Boolean chargeMod = bill.getChargeMod();

        //判断用户的汽车是不是已经在充电中
        Carinfo carinfo = carService.selectCarByUserID(bill.getUserID());
        //如果没有对应的订单或者汽车已经在充电中则直接返回false
        if (carinfo.getWaitingStatu() != 0)
            return Response.SimpleResponse.generate(true, 500, "订单信息或汽车信息有误");
        //否则就符合条件，先删除原先订单
        if (chargeMod) {
            if (lowModeBlockingDeque.size() < 6) {
                fastModeBlockingDeque.removeFirstOccurrence(bill.getBillID());
                lowModeBlockingDeque.add(bill.getBillID());
            } else {
                return Response.SimpleResponse.generate(true, 500, "队列满，修改失败");
            }

        } else {
            if (fastModeBlockingDeque.size() < 6) {
                lowModeBlockingDeque.removeFirstOccurrence(bill.getBillID());
                lowModeBlockingDeque.add(bill.getBillID());
            } else {
                return Response.SimpleResponse.generate(true, 500, "队列满，修改失败");
            }
        }
        billService.updateBillByPrimaryKeySelective(bill);
        //然后更新一下受影响的订单序列，找到排队在该订单之后的车辆序列，将其排名上升1（待封装）
        carService.updateRankOfMode(carinfo.getCarRank(), carinfo.getChargeMode());
        return Response.SimpleResponse.generate(true, 0, "更新成功");
    }


    /**
     * 用户在等待区中发起更改充电度数请求
     *
     * @param bill 预更新的订单对象
     * @return 对应结果的JSON字符串
     */
    @Override
    public String updateBillByChargeAmount(Bill bill) {
        //首先判断用户汽车是不是在等待区
        Carinfo carinfo = carService.selectCarByUserID(bill.getUserID());
        //如果没有对应的订单或者汽车已经在充电中则直接返回false
        if (carinfo.getWaitingStatu() != 0) {
            Response.SimpleResponse.generate(true, 500, "汽车正在充电中或者尚未提交订单！");
        }
        //否则就直接更新订单
        return Response.SimpleResponse.generate(billService.updateBillByPrimaryKeySelective(bill)
                , 0, "修改成功");
    }

    /**
     * 管理员查看所有的充电订单
     *
     * @return 所有充电请求的详细信息的JSON字符串
     */
    @Override
    public String selectHistoryBillByAdmin(String authorization) {
        int userCode = MyToken.parseJWTGetUserCode(authorization);
        if (userCode == 2) {
            String s = billService.selectAllBill();
            return Response.NormalResponse.generate(!s.equals(""), 0, "查询成功", s);
        } else if (userCode != -1)
            return Response.NormalResponse.generate(true, 403, "权限不足", null);
        else
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
    }

    /**
     * 管理员查看所有的充电请求
     *
     * @param authorization 令牌token
     * @param page          当前页数
     * @return 所有充电请求的
     */
    @Override
    public String selectHistoryBillByUser(String authorization, int page) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        if (userID == -1)
            return Response.SimpleResponse.generate(true, 401, "身份已过期");
        List<Bill> bills = billService.selectAllUserBillByUserID(userID, page);

        if (bills != null)
            return Response.NormalResponse.generate(true, 0, "查询成功",
                    JSONArray.toJSONString(bills));

        return Response.SimpleResponse.generate(true, 404, "用户没有对应订单");

    }


    /*
     * ------------------------以下是对设备的操作------------------------------------
     *-------------------------以下是对设备的操作------------------------------------
     * ------------------------以下是对设备的操作------------------------------------
     */

    /**
     * 查看用户充电详情
     *
     * @param authorization 传入的userID
     * @return 当前充电详情的JSON字符串
     */
    @Override
    public String selectChargeStatusByUserID(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        if (userID == -1)
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
        BillResponse.checkOneBillResponse checkOneBillResponse =
                new BillResponse.checkOneBillResponse();
        Bill bill = billService.selectChargingBillByUserID(userID);
        Carinfo carinfo = carService.selectCarByUserID(userID);

        //如果订单为空
        if (bill == null)
            return Response.NormalResponse.generate(true, 404, "没有该订单", "");

        //如果该车没有在充电中
        if (carinfo.getWaitingStatu() == 0) {
            checkOneBillResponse.setStatus("等待中");
            checkOneBillResponse.setWaitingArea(true);
            checkOneBillResponse.setWaitingArea(false);

        } else {
            checkOneBillResponse.setStatus("充电中");
            checkOneBillResponse.setChargingArea(true);
        }
        //设置应答请求为200
        checkOneBillResponse.setCode(0);
        //设置对应的模式
        checkOneBillResponse.setFast(bill.getChargeMod());
        //设置车辆状态
        checkOneBillResponse.setPosition(carinfo.getCarRank());
        //如果有充电桩
        if (bill.getDeviceID() != null) {
            Integer deviceID = bill.getDeviceID();
            checkOneBillResponse.setPile(deviceID);
        }

        //如果车辆已经开始充电
        if (carinfo.getDeviceID() != null)
            //设置设备id
            checkOneBillResponse.setDeviceID(carinfo.getDeviceID());
        //设置amount？？？？
        checkOneBillResponse.setAmount(bill.getChargeAmount());
        checkOneBillResponse.setTotalAmount(carinfo.getBatterySize());

        return JSON.toJSONString(checkOneBillResponse);

    }

    /**
     * 管理员查看所有的充电桩
     *
     * @return 所有充电桩设备的json字符串
     */
    @Override
    public String selectAllDevice(String authorization) {
        int userCode = MyToken.parseJWTGetUserCode(authorization);
        if (userCode == 2) {
            List<Device> devices = deviceService.selectAllDevice();
            List<DeviceResponse.selectDeviceTable> deviceTables = new ArrayList<>();
            for (Device device : devices) {
                DeviceResponse.selectDeviceTable selectDeviceTable =
                        new DeviceResponse.selectDeviceTable();
                //充电时长
                String chargeTimeCount = device.getChargeTimeCount();
                //充电次数
                Boolean deviceType = device.getDeviceType();
                //充电量
                double chargingAmount = FeeCount.calculateChargingAmount(deviceType,
                        chargeTimeCount);
                Double chargeFeeCount = device.getChargeFeeCount();
                double serviceCount = chargingAmount * 0.8;
                //设置充电时长
                selectDeviceTable.setChargeTime(chargeTimeCount);
                //设置充电id
                selectDeviceTable.setId(device.getDeviceID());
                //设置充电次数
                selectDeviceTable.setChargeTimes(device.getChargeTimesCount());
                //设置充电量
                selectDeviceTable.setChargeAmount(chargingAmount);
                //设置服务费
                selectDeviceTable.setServiceFee(serviceCount);
                //设置充电费
                selectDeviceTable.setChargeFee(chargeFeeCount);
                //设置总费用
                selectDeviceTable.setTotalFee(serviceCount + chargeFeeCount);

                //添加
                deviceTables.add(selectDeviceTable);
            }
            return JSONArray.toJSONString(deviceTables);
        } else if (userCode == -1)
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
        return Response.SimpleResponse.generate(true, 403, "权限不足");
    }

    /**
     * 查看指定设备详情
     *
     * @param deviceID      传入的设备id
     * @param authorization http请求对象
     * @return 返回封装好的JSON字符串
     */
    @Override
    public String selectDeviceByDeviceID(int deviceID, String authorization) {

        int userCode = userService.userCodeCheck(authorization);
        Device device = deviceService.selectDeviceByID(deviceID);

        if (userCode == 2) {
            DeviceResponse.selectDeviceDetailByID selectDeviceDetailByID =
                    new DeviceResponse.selectDeviceDetailByID();
            if (device != null) {
                String chargeTimeCount = device.getChargeTimeCount();
                double v = FeeCount.calculateChargingAmount(device.getDeviceType(),
                        chargeTimeCount);
                Boolean workingStatu = device.getWorkingStatu();
                Boolean chargeStatu = device.getChargeStatu();
                if (workingStatu && chargeStatu)
                    selectDeviceDetailByID.setStatus("充电中");
                else if (workingStatu && (!chargeStatu))
                    selectDeviceDetailByID.setStatus("运行中");
                else
                    selectDeviceDetailByID.setStatus("故障");
                selectDeviceDetailByID.setChargeAmount(v);
                selectDeviceDetailByID.setCode(0);
                selectDeviceDetailByID.setChargeTimes(device.getChargeTimesCount());
                selectDeviceDetailByID.setChargeTime(chargeTimeCount);
                selectDeviceDetailByID.setId(deviceID);

                return JSON.toJSONString(selectDeviceDetailByID);
            }

        } else if (userCode == -1) {
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
        }
        return Response.SimpleResponse.generate(true, 403, "权限不足");
    }

    /**
     * 查看当前设备下等待充电的用户详情
     *
     * @param deviceID      传入的设备id
     * @param authorization authorization token令牌
     * @return 返回封装好的JSON字符串
     */
    @Override
    public String selectPileWaitingConditionByPileID(int deviceID, String authorization) {
        int i = MyToken.parseJWTGetUserCode(authorization);
        if (i != 2)
            return Response.SimpleResponse.generate(true, 403, "没有权限!");

        //查看在当前充电桩进行的订单
        Device device = deviceService.selectDeviceByID(deviceID);
        Bill bill = billService.selectChargingBillByDeviceID(deviceID);
        List<Response.UserWaitingInfoResponse.UserWaiting> users = new ArrayList<>();
        if (bill != null) {
            //准备封装
            Response.UserWaitingInfoResponse.UserWaiting t =
                    new Response.UserWaitingInfoResponse.UserWaiting();
            t.setAmount(bill.getChargeAmount());
            t.setId(bill.getUserID());
            t.setStatus("充电中");
            t.setTotalAmount(t.getTotalAmount());
            t.setWaitTime(bill.getChargeDuration());
            users.add(t);
            if (deviceWait[deviceID] != -1) {
                Bill b = billService.selectBillByPrimaryKey(deviceWait[deviceID]);
                Response.UserWaitingInfoResponse.UserWaiting a =
                        new Response.UserWaitingInfoResponse.UserWaiting();
                a.setAmount(b.getChargeAmount());
                a.setId(b.getUserID());
                a.setStatus("等待中");
                a.setTotalAmount(t.getTotalAmount());
                a.setWaitTime(b.getChargeDuration());
                users.add(a);
            }
        }
        return Response.UserWaitingInfoResponse.generate(true, "查询成功", 0, deviceID, users);
    }

    /**
     * 根据充电桩id查看当前在当前充电桩等候服务的充电信息
     *
     * @param deviceID      要查询的deviceID
     * @param authorization 验证管理员身份
     * @return 当前充电桩服务信息的JSON字符串
     */
    @Override
    public String selectCarsInfoInDevice(int deviceID, String authorization) {
        //验证用户权限
        int userCode = MyToken.parseJWTGetUserCode(authorization);
        if (userCode == 2) {
            //查询出数据
            List<Carinfo> carinfos = carService.selectServingCarByDeviceID(deviceID);
            if (carinfos != null) {
                String s = JSONArray.toJSONString(carinfos);
                return Response.NormalResponse.generate(true, 0, "查询成功", s);
            } else
                return Response.NormalResponse.generate(true, 0, "当前没有等待车辆", "");
        } else
            return Response.NormalResponse.generate(true, 403, "权限不足或身份认证已过期", "");
    }


    /**
     * 管理员开关机device
     *
     * @param deviceID            传入的deviceID
     * @param deviceWorkingStatus 表示即将设置的device状态（true:开启，false:关闭）
     * @param authorization       身份令牌
     * @return 改变状态成功与否的JSON字符串
     */
    @Override
    public String updateDeviceWorkingStatus(int deviceID, int deviceWorkingStatus,
                                            String authorization) {
        //验证用户权限
        int userCode = MyToken.parseJWTGetUserCode(authorization);
        //
        if (userCode == 2) {
            //找到当前充电设备是否有充电请求（包括正在充电请求和等待充电请求）
            Device device = deviceService.selectDeviceByID(deviceID);
            //如果管理员发出的是开机请求，则检查是否处于关机状态
            if (deviceWorkingStatus == 1) {
                //如果设备本来就开机
                if (device.getWorkingStatu()) {
                    return Response.SimpleResponse.generate(true, 500, "设备状态错误");
                }
                //否则如果设备是关机，则开机
                else {
                    device.setWorkingStatu(true);
                    return updateSuccess(deviceID, deviceWorkingStatus, device);
                }
            }
            //如果是关机请求
            else {
                //如果它不在充电中
                if (!device.getChargeStatu()) {
                    device.setWorkingStatu(false);

                    return updateSuccess(deviceID, deviceWorkingStatus, device);
                } else {
                    Bill bill = billService.selectChargingBillByDeviceID(deviceID);
                    if (bill != null) {  //订单销毁
                        cancelBill(bill.getUserID(), bill.getBillID());
                        //重新生成订单
                        createBill(bill.getChargeAmount(), bill.getChargeMod(),
                                0, MyToken.createJWT(bill.getUserID(), null,
                                        null));
                        if (deviceWait[deviceID - 1] != -1) {
                            if (deviceID <= 2) {
                                fastModeBlockingDeque.add(deviceWait[deviceID - 1]);
                                deviceWait[deviceID - 1] = -1;
                            } else {
                                lowModeBlockingDeque.add(deviceWait[deviceID - 1]);
                                deviceWait[deviceID - 1] = -1;
                            }

                        }
                    }
                    return Response.SimpleResponse.generate(true, 500, "设备关机失败");
                }

            }

        } else if (userCode == -1)
            return Response.SimpleResponse.generate(true, 401, "身份状态已过期");
        return Response.SimpleResponse.generate(true, 403, "权限不足");
    }

    private String updateSuccess(int deviceID, int deviceWorkingStatus, Device device) {
        boolean b = deviceService.updateDeviceByPrimaryKeySelective(device);
        DeviceResponse.updateDeviceStatusResp updateDeviceStatusResp =
                new DeviceResponse.updateDeviceStatusResp();

        if (b) {
            updateDeviceStatusResp.setId(deviceID);
            updateDeviceStatusResp.setStatus(deviceWorkingStatus);
            updateDeviceStatusResp.setFast(device.getDeviceType());
            updateDeviceStatusResp.setCreated_at(MyDate.getNeedTimeInString(device.getCreated_at()));
            updateDeviceStatusResp.setUpdated_at(MyDate.getNeedTimeInString(device.getUpdated_at()));
            updateDeviceStatusResp.setDelete_at("null");
        }
        return JSON.toJSONString(updateDeviceStatusResp);
    }


    /**
     * 更新订单
     *
     * @param amount        充电量
     * @param fast          是否是快充
     * @param totalAmount   电池容量
     * @param authorization 令牌token
     * @return 返回更新是否成功的JSON
     */
    @Override
    public String updateBill(Integer amount, boolean fast, Integer totalAmount,
                             String authorization) {
        int UID = MyToken.parseJWTGetUserID(authorization);
        Bill bill = new Bill();
        bill = billService.selectChargingBillByUserID(UID);
        Carinfo car = new Carinfo();
        car = carService.selectCarByUserID(UID);
        if (car.getWaitingStatu() == 0) {
            if (bill.getChargeMod() == fast) {
                bill.setChargeAmount(totalAmount - amount);
                return updateBillByChargeAmount(bill);
            } else {
                bill.setChargeMod(fast);
                bill.setChargeAmount(totalAmount - amount);
                return updateBillByMode(bill, authorization);
            }
        } else {
            return Response.SimpleResponse.generate(true, 429, "无法修改订单");
        }
    }


}

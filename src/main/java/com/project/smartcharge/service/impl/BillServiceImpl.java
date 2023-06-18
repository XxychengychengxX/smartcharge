/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.project.smartcharge.mapper.BillMapper;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.BillExample;
import com.project.smartcharge.service.BillService;
import com.project.smartcharge.system.util.FeeCount;
import com.project.smartcharge.system.util.MyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * BillService接口的具体实现类
 */
@Service("billService")
@Transactional
public class BillServiceImpl implements BillService {
    @Autowired
    BillMapper billMapper;


    @Override
    public List<Bill> selectChargingBill() {
        BillExample billExample = new BillExample();
        BillExample.Criteria criteria = billExample.createCriteria();
        criteria.andIsDoneEqualTo(false);

        return billMapper.selectByExample(billExample);
    }

    /**
     * 根据传入的Bill对象来更新表中对应的Bill项
     *
     * @param Bill 传入的订单对象
     * @return 成功返回true 否则false
     */
    @Override
    public boolean updateBillByPrimaryKeySelective(Bill Bill) {
        //封装的充电请求
        int update = billMapper.updateByPrimaryKeySelective(Bill);
        return update == 1;
    }

    /**
     * 根据传入的BillID查找具体的订单并通过对象返回
     *
     * @param BillID 传入的BillID
     * @return 查询到的Bill对象
     */
    @Override
    public Bill selectBillByPrimaryKey(int BillID) {
        return billMapper.selectByPrimaryKey(BillID);
    }

    /**
     * 管理员查看当前所有设备状态
     *
     * @return 当前所有订单的JSON字符串
     */
    @Override
    public String selectAllBill() {
        return JSONArray.toJSONString(billMapper.selectByExample(null));
    }


    /**
     * 查看自身正在充电的充电请求
     *
     * @param userID 用户ID
     * @return 返回组成的JSON数组
     */
    @Override
    public Bill selectChargingBillByUserID(int userID) {
        BillExample billExample = new BillExample();
        BillExample.Criteria criteria = billExample.createCriteria();

        criteria.andUserIDEqualTo(userID).andIsDoneEqualTo(false);

        List<Bill> bills = billMapper.selectByExample(billExample);
        if (bills.size() != 0)
            return bills.get(0);
        else
            return null;
    }

    /**
     * 查看自身的充电请求详细信息(主要是用户)
     *
     * @return 所查看充电请求的详细json字符串
     */
    @Override
    public List<Bill> selectAllUserBillByUserID(int userID, int page) {
        if (page != -1)
            PageHelper.startPage(page, 5);

        BillExample BillExample = new BillExample();
        BillExample.Criteria criteria = BillExample.createCriteria();
        criteria.andUserIDEqualTo(userID);
        //查找该用户的当前订单
        List<Bill> bills = billMapper.selectByExample(BillExample);
        //返回构成的JSON数组
        return bills;

    }

    @Override
    public boolean deleteByPrimaryKey(Integer billID) {
        return billMapper.deleteByPrimaryKey(billID) == 1;
    }

    @Override
    public boolean deleteByUserID(Integer userID) {
        BillExample BillExample = new BillExample();
        BillExample.Criteria criteria = BillExample.createCriteria();
        criteria.andUserIDEqualTo(userID);
        return billMapper.deleteByExample(BillExample) == 1;
    }

    @Override
    public Bill selectChargingBillByDeviceID(int deviceID) {
        BillExample billExample = new BillExample();
        BillExample.Criteria criteria = billExample.createCriteria();
        criteria.andDeviceIDEqualTo(deviceID).andIsDoneEqualTo(false);
        List<Bill> bills = billMapper.selectByExample(billExample);
        if (!bills.isEmpty())
            return bills.get(0);
        return null;
    }

    /**
     * 在充电区取消充电请求后对bill的更新
     *
     * @param bill       传入的bill对象
     * @param chargeMode 当前的充电状态
     * @param sysDate    系统时间
     * @return 更新成功返回true
     */
    @Override
    public boolean cancelChargingRequestInCharging(Bill bill, boolean chargeMode, Date sysDate) {
        String startTimeString = bill.getChargeStartTime();
        Date chargeStartTime;
        Date endTimeDate;

        try {
            chargeStartTime =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTimeString);
            String chargeDuration = bill.getChargeDuration();
            String endTimeString = MyDate.addTimeInDateFormatString(startTimeString,
                    chargeDuration);
            endTimeDate = MyDate.getNeedTimeInDate(endTimeString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        double chargingCost = 0;
        double serviceCost = 0;
        //总费用
        double totalCost = 0;
        //拿到结束时间
        if (sysDate.before(endTimeDate)) {
            chargingCost = FeeCount.calculateChargingCost(chargeStartTime, sysDate,
                    true);
            serviceCost = FeeCount.calculateServiceCost(chargeStartTime, sysDate,
                    true);
            totalCost = chargingCost + serviceCost;
            String timeInterval = MyDate.getTimeIntervalByDate(chargeStartTime, sysDate);
            bill.setChargeDuration(timeInterval);
            bill.setChargeFee(chargingCost);
            bill.setServiceFee(serviceCost);
            bill.setTotalFee(totalCost);
            bill.setChargeEndTime(MyDate.getNeedTimeInString(sysDate));
            bill.setIsDone(true);
        } else {
            chargingCost = FeeCount.calculateChargingCost(chargeStartTime, endTimeDate,
                    true);
            serviceCost = FeeCount.calculateServiceCost(chargeStartTime, endTimeDate,
                    true);
            totalCost = chargingCost + serviceCost;
            String timeInterval = MyDate.getTimeIntervalByDate(chargeStartTime, endTimeDate);
            bill.setChargeDuration(timeInterval);
            bill.setChargeFee(chargingCost);
            bill.setServiceFee(serviceCost);
            bill.setTotalFee(totalCost);
            bill.setChargeEndTime(MyDate.getNeedTimeInString(endTimeDate));
            bill.setIsDone(true);
        }
        /*
         *-------------------------------以下是多重更新--------------------------
         *-------------------------------以下是多重更新--------------------------
         *-------------------------------以下是多重更新--------------------------
         */
        //对订单的更新(持续时间，各项费用统计，结束时间，是否结束状态）

        return updateBillByPrimaryKeySelective(bill);
    }

    /**
     * 返回当前的订单表中的订单数目
     *
     * @return 当前订单表的数据条数
     */
    @Override
    public int selectMaxBillID() {
        BillExample billExample = new BillExample();
        billExample.setOrderByClause("billID DESC");
        List<Bill> bills = billMapper.selectByExample(billExample);
        if (bills.size() != 0)
            return bills.get(0).getBillID();
        return 0;
    }

    /**
     * 在等待区取消充电请求后对bill的更新
     *
     * @param bill    传入的bill对象
     * @param sysDate 系统时间
     * @return 更新成功返回true
     */
    @Override
    public boolean cancelChargingRequestInWaiting(Bill bill, Date sysDate) {
        //更新订单
        bill.setIsDone(true);
        bill.setTotalFee(0.0);
        bill.setChargeFee(0.0);
        bill.setServiceFee(0.0);

        String needTimeInString = MyDate.getNeedTimeInString(sysDate);
        bill.setChargeDuration("未开始充电,可能是订单已取消");
        bill.setChargeEndTime(needTimeInString);

        int update = billMapper.updateByPrimaryKeySelective(bill);
        return update == 1;
    }

    @Override
    public boolean addBillByPrimaryKeySelective(Bill Bill) {
        int insert = billMapper.insertSelective(Bill);
        return insert == 1;
    }
}

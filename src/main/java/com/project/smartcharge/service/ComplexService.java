/**
 * @author Valar Morghulis
 * @Date 2023/5/21
 */
package com.project.smartcharge.service;

import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.Carinfo;

/**
 * 复杂业务请求的接口，绝大多数业务在这里处理
 */
public interface ComplexService {


    /*
     * ------------------------以下是对订单的复杂操作---------------------------------
     *-------------------------以下是对订单的复杂操作------------------------------------
     * ------------------------以下是对订单的复杂操作---------------------------------
     */

    /**
     * 管理员查看所有的充电请求
     *
     * @return 所有充电请求的
     */
    String selectHistoryBillByAdmin(String authorization);

    /**
     * 管理员查看所有的充电请求
     *
     * @param authorization 令牌token
     * @param page 当前页数
     * @return 所有充电请求的
     */
    String selectHistoryBillByUser(String authorization,int page);


    /**
     * 根据用户的id取消他的订单（包括充电区退出或者等待区退出）
     *
     * @param UserID 传入的userID
     * @param billID 订单编号
     * @return 返回对应更新状态的JSON字符串
     */
    String cancelBill(int UserID, int billID);

    /**
     * 删除当前订单
     * @param authorization 令牌token
     * @return 删除是否成功的JSON字符串
     */
    String deleteCurrentBill(String authorization);
    /**
     * 用户提交订单请求
     *
     * @param amount        充电度数申请
     * @param fastMode      此次请求是否是快充
     * @param totalAmount   车辆电池大小
     * @param authorization token令牌
     * @return 更新成功的JSON字符串
     */
    String createBill(int amount, boolean fastMode, int totalAmount, String authorization);

    /**
     * 用户在等待区中发起更改充电模式请求
     *
     * @param bill 预更新的订单对象
     * @param authorization 令牌token
     * @return 成功 ture反之false
     */
    String updateBillByMode(Bill bill,String authorization);

    /**
     * 用户在等待区中发起更改充电度数请求
     *
     * @param bill 预更新的订单对象
     * @return 成功或者失败状态的JSON字符串
     */
    String updateBillByChargeAmount(Bill bill);

    /**
     * 获取指定订单
     * @param authorization 令牌token
     * @param billID 订单ID
     * @return JSON字符串
     */
    String selectBillByid(String authorization,Integer billID);


    /*
     * ------------------------以下是对设备的复杂操作---------------------------------
     *-------------------------以下是对设备的复杂操作------------------------------------
     * ------------------------以下是对设备的复杂操作---------------------------------
     */


    /**
     * 管理员开关机device
     *
     * @param deviceID            传入的deviceid
     * @param deviceWorkingStatus 表示即将设置的device状态（true:开启，false:关闭）
     * @param authorization       转载信息的request
     * @return 改变状态成功返回true，反之返回false
     */
    String updateDeviceWorkingStatus(int deviceID, int deviceWorkingStatus,
                                     String authorization);

    /**
     * 根据充电桩id查看当前在当前充电桩等候服务的充电信息(管理员)
     *
     * @param deviceID      要查询的deviceID
     * @param authorization 令牌token
     * @return 当前充电桩服务信息的JSON字符串
     */
    String selectCarsInfoInDevice(int deviceID, String authorization);

    /**
     * 管理员查看所有的充电桩
     *
     * @param authorization jwt字符串
     * @return 所有充电桩设备的json字符串
     */
    String selectAllDevice(String authorization);

    /**
     * 查看指定设备详情
     *
     * @param deviceID      传入的设备id
     * @param authorization http请求对象
     * @return 返回封装好的JSON字符串
     */
    String selectDeviceByDeviceID(int deviceID, String authorization);

    /**
     * 查看当前设备下等待充电的用户详情
     *
     * @param deviceID      传入的设备id
     * @param authorization http请求对象
     * @return 返回封装好的JSON字符串
     */
    String selectPileWaitingConditionByPileID(int deviceID, String authorization);


    /*
     * ------------------------以下是对汽车的操作---------------------------------
     *-------------------------以下是对汽车的操作------------------------------------
     * ------------------------以下是对汽车的操作---------------------------------
     */

    /**
     * 用户注册车辆信息
     *
     * @param carinfo       车辆信息
     * @param authorization token令牌
     * @return 是否注册成功的JSON字符串
     */
    String carRegister(String authorization, Carinfo carinfo);

    /**
     * 用户删除自己对应的车辆信息
     *
     * @param authorization 用户令牌，
     * @return true表示车辆信息更新成功，反之返回false
     */
    String carDeleteByUserID(String authorization);

    /**
     * 查看用户充电详情
     *
     * @param authorization token令牌
     * @return 当前充电详情的JSON字符串
     */
    String selectChargeStatusByUserID(String authorization) ;

    /**
     * 根据userID展示用户的车辆信息
     *
     * @param authorization 用户令牌
     * @return 用户车辆的JSON字符串
     */
    String selectCarByUserID(String authorization);

    /**
     * 接受ping请求
     *
     * @return pong
     */
    String ping();

    /**
     * 当前时间作为接口调用
     *
     * @return 当前时间
     */
    String getTimeByInterface();

    /**
     * 修改订单
     * @param amount 充电度数
     * @param fast 是否快充
     * @param totalAmount 电池大小
     * @param authorization 用户令牌
     * @return 是否更新成功的JSON字符串
     */
    String updateBill(Integer amount, boolean fast, Integer totalAmount, String authorization);

    /**
     * 获取用户当前的充电排队号码
     * @param authorization 令牌token
     * @return 当前查询结果的JSON队列
     */
    String selectCarRank(String authorization);

    /**
     * 更新系统时间
     *
     * @param time          指定的date
     * @param authorization 令牌
     * @return 更新是否成功
     */
    String updateTime(Long time, String authorization);
}

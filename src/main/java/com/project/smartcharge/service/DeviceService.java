/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service;

import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.Device;


import java.util.Date;
import java.util.List;

/**
 * device业务逻辑的接口，一般是提供给ComplexService进行调用
 */
public interface DeviceService {
    /*
     * -------------------管理员相关-----------------------
     */

    /**
     * 管理员查看所有充电桩
     *
     * @return 所有充电转信息的Json字符串
     */
    List<Device> selectAllDevice();

    /**
     * 管理员查看某个充电桩的具体信息
     *
     * @param deviceID 传入的deviceid
     * @return 充电桩信息的JSON字符串
     */
    String checkDeviceByAdmin(int deviceID);



    /*
     * -------------------客户相关-----------------------
     */

    /**
     * 指定的设备提供充电并且开始计时
     *
     * @param deviceID 设备id
     * @return 开始提供充电返回true，否则返回false
     */
    boolean deviceChargeStart(int deviceID);

    /**
     * 指定设备完成充电请亲
     *
     * @param deviceID       设备id
     * @param chargeAddTime  设备即将加上的充电时常统计
     * @param chargeFeeCount 设备这次完成的充电订单的统计费(不包括服务费)
     * @return 成功完成返回true，反之false
     */
    boolean deviceChargeEnd(int deviceID, String chargeAddTime, double chargeFeeCount);




    /*
    -------------------------以下提供给complexService的调用----------------------------
    -------------------------以下提供给complexService的调用----------------------------
    -------------------------以下提供给complexService的调用----------------------------
     */

    /**
     * 根据设备id查询设备的工作状态（是否在工作中）
     *
     * @param deviceID 传入的设备id
     * @return 当前所查询设备的工作状态
     */
    Device selectDeviceByID(int deviceID);

    /**
     * 查看某个充电模式下的空闲设备信息
     *
     * @param chargeMode 充电模式
     * @return 返回该类型的设备列表
     */
    List<Device> selectFreeDeviceByDeviceType(boolean chargeMode);

    /**
     * 更新设备信息
     *
     * @param device 传入的所需的device的信息(只更新传入的变量中有值的对象)
     * @return 成功返回true 反之false
     */
    boolean updateDeviceByPrimaryKeySelective(Device device);

    /**
     * 在充电区取消充电请求后对device的更新,需要更新完成后的订单对象
     *
     * @param bill    完成充电后的订单状态
     * @param device  当前的设备对象
     * @param sysDate 系统时间
     * @return 成功返回true反之false
     */
    boolean cancelChargingRequestInCharging(Bill bill, Device device, Date sysDate);



}

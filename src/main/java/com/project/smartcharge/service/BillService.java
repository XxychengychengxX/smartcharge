/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service;

import com.project.smartcharge.pojo.Bill;

import java.util.Date;
import java.util.List;

/**
 * Bill业务逻辑的接口，一般是提供给ComplexService进行调用
 */
public interface BillService {

    List<Bill> selectChargingBill();


    /**
     * 根据传入的Bill对象来更新表中对应的Bill项
     *
     * @param Bill 传入的订单对象
     * @return 成功返回true 否则false
     */
    boolean updateBillByPrimaryKeySelective(Bill Bill);

    /**
     * 查找所有的订单并且通过JSON字符串返回（理论上只有管理员能这么操作）
     *
     * @return 当前所有订单的JSON字符串
     */
    String selectAllBill();

    /**
     * 根据传入的BillID查找具体的订单并通过对象返回
     *
     * @param BillID 传入的BillID
     * @return 查询到的Bill对象
     */
    Bill selectBillByPrimaryKey(int BillID);

    /**
     * 查看自身的充电请求历史记录
     *
     * @param userID 用户id
     * @param page   当前页数
     * @return 所查看充电请求的详细json字符串
     */
    List<Bill> selectAllUserBillByUserID(int userID, int page);

    /**
     * 查看自身正在充电的充电请求
     *
     * @param userID 用户ID
     * @return 返回组成的JSON数组
     */
    Bill selectChargingBillByUserID(int userID);

    /*
     * 用户更改充电请求，只能在等待区进行修改
     *
     * @return true表示更改成功，反之false
    boolean userUpdateBillByPrimaryKeySelective(Bill Bill);*/

    /**
     * 添加订单表项
     *
     * @param Bill 传入的订单表项
     * @return 成功返回true 反之返回false
     */
    boolean addBillByPrimaryKeySelective(Bill Bill);

    /**
     * 根据用户id来删除订单
     *
     * @param userID 需要删除的订单的userID属性
     * @return 成功返回true 反之false
     */
    boolean deleteByUserID(Integer userID);

    /**
     * 根据订单id来删除订单
     *
     * @param BillID 需要删除的订单的userID属性
     * @return 成功返回true 反之false
     */

    boolean deleteByPrimaryKey(Integer BillID);

    /**
     * 在等待区取消充电请求后对bill的更新
     *
     * @param bill    传入的bill对象
     * @param sysDate 系统时间
     * @return 更新成功返回true
     */
    boolean cancelChargingRequestInWaiting(Bill bill, Date sysDate);

    /**
     * 在充电区取消充电请求后对bill的更新
     *
     * @param bill       传入的bill对象
     * @param chargeMode 当前的充电状态
     * @param sysDate 系统时间
     * @return 更新成功返回true
     */
    boolean cancelChargingRequestInCharging(Bill bill, boolean chargeMode, Date sysDate);

    /**
     * 返回当前的订单表中的订单数目
     *
     * @return 当前订单表的当前最大ID
     */
    int selectMaxBillID();

    /**
     * 查找当前充电桩正在充电的订单
     * @param deviceID 当前充电桩正在充电
     * @return 订单对象
     */
    Bill selectChargingBillByDeviceID(int deviceID);
}

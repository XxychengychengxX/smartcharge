/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service;


import com.project.smartcharge.pojo.Carinfo;

import java.util.List;

/**
 * carinfo业务逻辑的接口，一般是提供给ComplexService进行调用
 */
public interface CarService {

    /**
     * 用户填写对应的车辆信息
     *
     * @param carinfo 车子的信息，刚进入时某些项为空
     * @return true表示车辆信息更新成功，反之返回false
     */
    boolean carRegister(Carinfo carinfo);

    /**
     * 用户删除自己对应的车辆信息
     *
     * @param userID 用户的id
     * @return true表示车辆信息更新成功，反之返回false
     */
    boolean carDeleteByUserID(int userID);

    /**
     * 查看本车在该充店模式下的排队号码
     *
     * @param chargeMod 查询的充电模式
     * @return 排队编号
     */
    int selectMaxCarRankByMode(boolean chargeMod);

    /**
     * 查找用户对应的车辆
     *
     * @param userID 用户id
     * @return 返回该车的详细信息
     */
    Carinfo selectCarByUserID(int userID);

    /**
     * 更新车况信息
     *
     * @param carinfo 预更新的车况信息
     * @return 更新成功返回true, 反之返回false
     */
    boolean updateByPrimaryKeySelective(Carinfo carinfo);

    /**
     * 根据用户id查看本车的排队号码
     *
     * @return 用户汽车的排队号码
     */
    int selectCarRankByUserID(int userID);


    /**
     * 更新具有订单的该汽车之后的汽车排名
     *
     * @param carRank    当前要更新的该排名之后的汽车，例如这里传参为3，更新>=4的让他们减一
     * @param chargeMode 更新的车辆的快充模式
     */
    void updateRankOfMode(Integer carRank, boolean chargeMode);

    /**
     * 管理员查看对应充电桩id的服务车辆信息（包括充电中和等待充电中）
     *
     * @param deviceID 要查看的deviceID
     * @return 车辆信息的JSON字符串
     */
    List<Carinfo> selectServingCarByDeviceID(int deviceID);

    /**
     * 在等待区取消充电请求后对carinfo的更新
     *
     * @param carinfo 传入的carinfo对象
     * @return 更新成功返回true
     */
    boolean cancelChargingRequestInWaiting(Carinfo carinfo);

    /**
     * 在充电区取消充电请求后对carinfo的更新
     *
     * @param carinfo 传入的carinfo对象
     * @return 更新成功返回true
     */
    boolean cancelChargingRequestInCharging(Carinfo carinfo);


}

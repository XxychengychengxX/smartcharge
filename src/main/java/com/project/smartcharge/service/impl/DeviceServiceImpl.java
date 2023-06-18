/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.project.smartcharge.mapper.DeviceMapper;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.Device;
import com.project.smartcharge.pojo.DeviceExample;
import com.project.smartcharge.service.DeviceService;
import com.project.smartcharge.system.util.MyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * deviceService接口的具体实现类
 */
@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public List<Device> selectAllDevice() {
        return deviceMapper.selectByExample(null);
    }


    /**
     * 更新设备信息
     *
     * @param device 传入的所需的device的信息
     * @return 成功返回true 反之false
     */
    @Override
    public boolean updateDeviceByPrimaryKeySelective(Device device) {
        return deviceMapper.updateByPrimaryKeySelective(device) == 1;
    }

    /**
     * 在充电区取消充电请求后对device的更新,需要更新完成后的订单对象
     *
     * @param bill    完成充电后的订单状态
     * @param device  当前的设备对象
     * @param sysDate 系统时间
     * @return 成功返回true反之false
     */
    @Override
    public boolean cancelChargingRequestInCharging(Bill bill, Device device, Date sysDate) {
        //拿到要更新的信息
        String chargeEndTime = bill.getChargeEndTime();
        String chargeStartTime = bill.getChargeStartTime();
        //HH:mm的时间间隔字符串
        String timeIntervalInString = MyDate.getTimeIntervalByString(chargeStartTime,
                chargeEndTime);
        //HH:mm的充电桩充电市场的字符串
        String chargeTimeCount = device.getChargeTimeCount();

        String addTimeInDate = MyDate.addTimeInHourString(chargeTimeCount, timeIntervalInString);
        //更新设备
        device.setChargeTimesCount(device.getChargeTimesCount() + 1);//充电次数
        device.setChargeTimeCount(addTimeInDate);//充电持续时长
        device.setChargeStatu(false);
        device.setChargeFeeCount(device.getChargeFeeCount() + bill.getTotalFee());

        return updateDeviceByPrimaryKeySelective(device);
    }



    /**
     * 根据设备id查询设备的工作状态（是否在工作中）
     *
     * @param deviceID 传入的设备id
     * @return 当前所查询设备的工作状态
     */
    @Override
    public Device selectDeviceByID(int deviceID) {
        Device device = deviceMapper.selectByPrimaryKey(deviceID);
        return device;

    }

    /**
     * 管理员查看某个充电桩的具体信息
     *
     * @param deviceID 传入的deviceid
     * @return 充电桩信息的JSON字符串
     */
    @Override
    public String checkDeviceByAdmin(int deviceID) {
        return JSON.toJSONString(deviceMapper.selectByPrimaryKey(deviceID));
    }

    /**
     * 指定的设备提供充电并且开始计时
     *
     * @param deviceID 设备id
     * @return 开始提供充电返回true，否则返回false
     */
    @Override
    public boolean deviceChargeStart(int deviceID) {
        Device device = new Device();
        device.setDeviceID(deviceID);
        //将它的充电状态设为充电中
        device.setChargeStatu(true);
        return deviceMapper.updateByPrimaryKeySelective(device) == 1;
    }

    /**
     * 指定设备完成充电请亲
     *
     * @param deviceID       设备id
     * @param chargeAddTime  设备即将要加上的充电时长统计
     * @param chargeFeeCount
     * @return 成功返回true, 反之false
     */
    @Override
    public boolean deviceChargeEnd(int deviceID, String chargeAddTime, double chargeFeeCount) {
        Device device = deviceMapper.selectByPrimaryKey(deviceID);
        //将设备的充电完成次数+1

        Integer chargeTimesCount = device.getChargeTimesCount();
        device.setChargeTimesCount(chargeTimesCount + 1);


        //设置当前的充电持续时常并且设置
        String chargeTimeCount = device.getChargeTimeCount();
        String newChargeTimeCount = MyDate.addTimeInHourString(chargeTimeCount, chargeAddTime);
        device.setChargeTimeCount(newChargeTimeCount);

        //设置设备并不在充电中
        device.setChargeStatu(false);

        //更新设备的充电费用统计
        Double chargeFeeCount1 = device.getChargeFeeCount();
        device.setChargeFeeCount(chargeFeeCount1 + chargeFeeCount);

        //更新操作
        return deviceMapper.updateByPrimaryKeySelective(device) == 1;
    }


    /**
     * 查看某个充电模式下未关机的设备信息
     *
     * @param chargeMode 充电模式
     * @return 返回该类型的设备列表
     */
    @Override
    public List<Device> selectFreeDeviceByDeviceType(boolean chargeMode) {
        DeviceExample deviceExample = new DeviceExample();
        DeviceExample.Criteria criteria = deviceExample.createCriteria();

        criteria.andDeviceTypeEqualTo(chargeMode).andWorkingStatuEqualTo(true).andChargeStatuEqualTo(false);

        return deviceMapper.selectByExample(deviceExample);

    }


}

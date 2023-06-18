package com.project.smartcharge.service.impl;


import com.project.smartcharge.pojo.Device;
import com.project.smartcharge.service.DeviceService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)

@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml", "classpath:/static" +
        "/appContext_service.xml", "classpath:/static/springmvc.xml"})
class DeviceServiceImplTest {

    @Resource
    private DeviceService deviceService;

    @Test
    void selectAllDevice() {
        System.out.println("deviceService.selectAllDevice() = " + deviceService.selectAllDevice());
    }

    @Test
    void updateDeviceByPrimaryKey() {
        Device device = new Device();
        device.setWorkingStatu(true);
        device.setChargeStatu(false);
        device.setDeviceID(1);
        System.out.println("deviceService.updateDeviceByPrimaryKeySelective(device) = " + deviceService.updateDeviceByPrimaryKeySelective(device));
    }


    @Test
    void selectDeviceByID() {
        int deviceID = 1;
        System.out.println("deviceService.selectDeviceByID(deviceID) = " + deviceService.selectDeviceByID(deviceID));
    }

    @Test
    void checkDevice() {
        int deviceID = 2;
        System.out.println("deviceService.checkDeviceByAdmin(deviceID) = " + deviceService.checkDeviceByAdmin(deviceID));
    }

    @Test
    void deviceChargeStart() {
        int device = 1;
        System.out.println("deviceService.deviceChargeStart(device) = " + deviceService.deviceChargeStart(device));
    }

    @Test
    void deviceChargeEnd() {
        int deviceID = 1;
        String timeDuration = "3:00";
        int chargeFeeCount = 30;
        System.out.println("deviceService.deviceChargeEnd(1,\"3:00\") = " +
                deviceService.deviceChargeEnd(deviceID, timeDuration, chargeFeeCount));
    }

    @Test
    void selectFreeDeviceByDeviceType() {
        List<Device> devices = deviceService.selectFreeDeviceByDeviceType(true);
        if (devices != null)
            devices.forEach(System.out::println);
    }


}
package com.project.smartcharge.service.impl;

import com.project.smartcharge.service.ComplexService;
import com.project.smartcharge.system.util.MyToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j

@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml", "classpath:/static" +
        "/appContext_service.xml", "classpath:/static/springmvc.xml"})
class ComplexServiceImplTest {
    @Autowired
    ComplexService complexService;

    @Test
    void cancelBill() {

    }

    @Test
    void createBill() {
    }

    @Test
    void updateBillByMode() {
    }

    @Test
    void updateBillByChargeAmount() {
    }

    @Test
    void selectAllBill() {
        String jwt = MyToken.createJWT(1, "wangchen", 2);
        System.out.println("complexService.selectHistoryBillByAdmin(jwt) = " + complexService.selectHistoryBillByAdmin(jwt));
    }

    @Test
    void selectAllDevice() {
        String jwt = MyToken.createJWT(1, "wangchen", 2);
        System.out.println("complexService.selectAllDevice(jwt) = " + complexService.selectAllDevice(jwt));
    }

    @Test
    void selectHistoryBillByUser() {
        String jwt = MyToken.createJWT(1, "wangchen", 2);
        //查询第几页的i订单
        int page = 2;
        System.out.println("complexService.selectHistoryBillByUser(jwt) = " + complexService.selectHistoryBillByUser(jwt, page));
    }


    @Test
    void selectCarsInfoInDevice() {
        String jwt = MyToken.createJWT(1, "wangchen", 2);
        int deviceID = 1;
        System.out.println("complexService.selectCarsInfoInDevice(deviceID, jwt) = " + complexService.selectCarsInfoInDevice(deviceID, jwt));
    }

    @Test
    void transDeviceWorkingStatus() {

    }

    @Test
    void selectCarByUserID() {
    }

    @Test
    void monitorChargingStatus() {
    }

    @Test
    void deleteBill() {
        String jwt = MyToken.createJWT(1, "wangchen", 2);
        int billID=3;
        System.out.println("complexService.deleteCurrentBill(jwt,billID) = " + complexService.deleteCurrentBill(jwt));
    }

    @Test
    void testCancelBill() {
    }

    @Test
    void carRegister() {
    }

    @Test
    void carDeleteByUserID() {
    }

    @Test
    void ping() {
        System.out.println("complexService.ping() = " + complexService.ping());
    }

    @Test
    void time() {
        System.out.println("complexService.getTime() = " + complexService.getTimeByInterface());
    }

    @Test
    void testCreateBill() {
    }

    @Test
    void testUpdateBillByMode() {
    }

    @Test
    void testUpdateBillByChargeAmount() {
    }



    @Test
    void selectChargeStatusByUserID() {
    }

    @Test
    void testSelectAllDevice() {
    }

    @Test
    void selectDeviceByDeviceID() {
    }

    @Test
    void selectPileWaitingConditionByPileID() {
    }

    @Test
    void testSelectCarsInfoInDevice() {
    }

    @Test
    void updateDeviceWorkingStatus() {
    }
}
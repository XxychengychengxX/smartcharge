package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.service.BillService;
import com.project.smartcharge.system.util.MyDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml", "classpath:/static" +
        "/appContext_service.xml", "classpath:/static/springmvc.xml"})
class BillServiceImplTest {

    @Autowired
    BillService billService;

    @Test
    void updateBillByPrimaryKeySelective() {
        long currentTimeMillis = System.currentTimeMillis();
        long l = currentTimeMillis - 30 * 60 * 1000;
        Date beforeTime = new Date(l);
        Date date = new Date(currentTimeMillis);

        Bill Bill = new Bill();
        double chargeFee = 30.0;
        double serviceFee = 15.5;

        int BillID = 2;
        //int userID = 1;
        //int deviceID = 1;
        boolean chargeMode = true;
        String now = MyDate.getNeedTimeInString(date);
        String before = MyDate.getNeedTimeInString(beforeTime);

        //更新第一个订单
        Bill.setBillID(BillID);
        //设置充电模式
        Bill.setChargeMod(chargeMode);
        //设置该订单的发起人为1
        //Bill.setUserID(userID);
        //设置该订单的处理设备为1
        // Bill.setDeviceID(deviceID);
        //更新订单的时间
        Bill.setChargeEndTime(now);//结束时间
        Bill.setChargeDuration(MyDate.getTimeIntervalByString(before, now));//充电持续时间


        //设置订单为完成
        Bill.setIsDone(true);
        //设置订单的状态
        Bill.setChargeFee(chargeFee);
        Bill.setServiceFee(serviceFee);
        Bill.setTotalFee(chargeFee + serviceFee);


        System.out.println("billService.updateBillByPrimaryKeySelective(Bill) = " + billService.updateBillByPrimaryKeySelective(Bill));
    }

    @Test
    void selectBillByPrimaryKey() {
        int billID = 1;
        Bill Bill = billService.selectBillByPrimaryKey(billID);
        System.out.println("Bill = " + Bill);
    }

    @Test
    void selectAllBill() {
        String s = billService.selectAllBill();
        System.out.println("s = " + s);
    }

    @Test
    void selectUserBillByUserID() {
        int userID = 1;
        int page = 1;
        List<Bill> bills = billService.selectAllUserBillByUserID(userID, page);
        String s = JSONArray.toJSONString(bills);
        System.out.println("s = " + s);
    }

    @Test
    void deleteByUserID() {
        int userID = 1;
        billService.deleteByUserID(userID);
    }

    @Test
    void deleteByPrimaryKey() {
        int billID = 1;
        billService.deleteByUserID(billID);
    }

    @Test
    void userUpdateBill() {
        Bill Bill = new Bill();
        System.out.println("billService.updateBillByPrimaryKeySelective(Bill) = " + billService.updateBillByPrimaryKeySelective(Bill));
    }

    @Test
    void addBillByPrimaryKey() {
        Bill bill = new Bill();
        int billID = 3;
        int userID = 3;
        int deviceID = 1;
        boolean chargeMode = true;
        Date date = new Date();
        String generateTime = MyDate.getNeedTimeInString(date);
        MyDate.getNowTimeInString();
        //更新第一个订单
        bill.setBillID(billID);
        //设置充电模式
        bill.setChargeMod(chargeMode);
        //设置该订单的发起人为1
        bill.setUserID(userID);
        //设置订单的充电度数
        bill.setChargeAmount(10);

        //设置订单为完成
        bill.setIsDone(false);

        System.out.println("billService.addBillByPrimaryKeySelective(bill) = " + billService.addBillByPrimaryKeySelective(bill));

    }
}
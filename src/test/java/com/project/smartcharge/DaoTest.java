/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge;

import com.project.smartcharge.mapper.BillMapper;
import com.project.smartcharge.mapper.DeviceMapper;
import com.project.smartcharge.mapper.UserMapper;
import com.project.smartcharge.pojo.Bill;
import com.project.smartcharge.pojo.Device;
import com.project.smartcharge.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml"})
public class DaoTest {
    @Autowired
    UserMapper userMapper;

    @Autowired
    BillMapper BillMapper;

    @Autowired
    DeviceMapper deviceMapper;

    /*
    -----------------------------User表测试------------------------
     */

    @Test
    public void selectAllUserTest() {
        List<User> users = userMapper.selectByExample(null);
        users.forEach(user -> System.out.println(user.toString()));
    }

    @Test
    public void insertUserTest() {
        /*
        new User("")
        */
        User user = new User();
        user.setUsername("wangchen");
        user.setUserCode(1);
        user.setPassword("123456b");
        userMapper.insert(user);
        selectAllUserTest();
    }

    @Test
    public void deleteUserByPrimaryKeyTest() {
        userMapper.deleteByPrimaryKey(2);
        selectAllUserTest();
    }

    @Test
    public void deleteUserAllTest() {
        userMapper.deleteByExample(null);
        selectAllUserTest();
    }

    @Test
    public void selectUserTest() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
    }

    /*
     * ---------------------------device表测试--------------------------
     * */

    @Test
    public void creatDeviceTest() {
        Device device = new Device();
        //设置是否在开机状态
        device.setWorkingStatu(false);
        //设置是否在提供充电服务
        device.setChargeStatu(false);
        //设置是否是快充桩
        device.setDeviceType(true);
        //设置运行以来的充电费用
        device.setChargeFeeCount(0.0);
        //设置运行以来的充电次数统计
        device.setChargeTimesCount(0);
        //快充充电桩
        device.setChargeRate(30);
        //设置运行时间
        device.setChargeTimeCount("00:00:00");

        deviceMapper.insert(device);
    }


    /*
    ------------------------------Bill表测试-----------------------------
     */
    @Test
    public void creatBillTest() {
        Bill Bill = new Bill();

    }
}

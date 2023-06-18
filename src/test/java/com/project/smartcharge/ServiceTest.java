/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge;

import com.project.smartcharge.pojo.Carinfo;
import com.project.smartcharge.pojo.User;
import com.project.smartcharge.service.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml", "classpath:/static" +
        "/appContext_service.xml", "classpath:/static/springmvc.xml"})
public class ServiceTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserService userService;
    @Resource
    BillService billService;
    @Resource
    DeviceService deviceService;
    @Resource
    CarService carService;
    @Resource
    ComplexService complexService;

    /*
     *-----------------------------userService直接测试-----------------------------
     *-----------------------------userService直接测试-----------------------------
     *-----------------------------userService直接测试-----------------------------
     * */
    @Test
    public void userRegisterTest() {
        User user = new User();
        user.setUsername("chenzuxin");
        user.setPassword("123456b");
        user.setUserCode(1);
        System.out.println(userService.userRegister(user));
    }

    @Test
    public void userLoginTest() {
        String username = "linqiushi";
        String rightPassword = "123456a";
        String wrongPassword = "123456b";
        System.out.println(userService.userLogin(username, rightPassword));

    }

    @Test
    public void userRoleCheckTest() {
        int userCode = userService.userCodeCheck("chenzuxin");
        if (userCode == 1)
            System.out.println("该用户是用户");
        else if (userCode == 2)
            System.out.println("该用户是管理员！");
    }


    /*
     *-----------------------------carService直接测试-----------------------------
     *-----------------------------carService直接测试-----------------------------
     *-----------------------------carService直接测试-----------------------------
     * */
    @Test
    public void carRegisterTest() {
        Carinfo carinfo = new Carinfo();
        carinfo.setWaitingStatu((byte) -1);
        carinfo.setUserID(1);
        carinfo.setBatterySize(109.5);
        carinfo.setCarDeck("闽A12345");


    }

    @Test
    public void carDeleteTest() {
        Carinfo carinfo = new Carinfo();
        carinfo.setUserID(1);


    }


}

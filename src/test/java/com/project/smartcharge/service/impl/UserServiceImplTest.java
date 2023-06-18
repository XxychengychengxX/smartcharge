package com.project.smartcharge.service.impl;

import com.project.smartcharge.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(value = {"classpath:/static/appContext_dao.xml", "classpath:/static" +
        "/appContext_service.xml", "classpath:/static/springmvc.xml"})
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void userLogin() {
        String username = "linqiushi";
        String rightPassword = "123456a";
        String wrongPassword = "123456b";

        System.out.println("userService.userLogin(username, rightPassword) = " + userService.userLogin(username, rightPassword));
    }

    @Test
    void userRegister() {

        User user = new User();
        user.setUserCode(1);
        user.setUsername("linqiushi3");
        user.setPassword("123456a");
        System.out.println("userService.userRegister(user) = " + userService.userRegister(user));

    }

    @Test
    void userDeletedByPrimaryKey() {
        User user = new User();
        int userId = 8;
        System.out.println("userService.userDeletedByPrimaryKey(userId) = " + userService.userDeletedByPrimaryKey(userId));
    }

    @Test
    void userCodeCheck() {
        String username = "wanghuishi";
        userService.userCodeCheck(username);
    }
}
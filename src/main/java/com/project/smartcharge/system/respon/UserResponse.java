/**
 * @author Valar Morghulis
 * @Date 2023/5/24
 */
package com.project.smartcharge.system.respon;

import com.project.smartcharge.pojo.User;
import lombok.Data;

import java.util.List;


/**
 * 用于封装用户登录，注册，角色验证查询返回信息的类 内部类使用@Data注解
 */
public class UserResponse {

    /**
     * 这个类封装的是UserRegisterResponse，格式：（之后不再赘述，查看接口文档） { "code": 0, "message": "注册成功" }
     */
    @Data
    public static class UserRegisterResponse {
        private int code;
        private String message;
    }

    @Data
    public static class UserLoginResponse {
        private int code;
        private String expireTime;
        private String token;
        private String message;

    }


    @Data
    public static class UserWaitingInfoResponse{
        private int code;

        List<User> users;
    }


}

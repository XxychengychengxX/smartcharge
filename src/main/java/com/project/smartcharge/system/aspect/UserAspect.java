/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.system.aspect;

import com.alibaba.fastjson.JSON;
import com.project.smartcharge.pojo.User;
import com.project.smartcharge.system.respon.UserResponse;
import com.project.smartcharge.system.util.MyToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserAspect {

    private static final Logger logger = LoggerFactory.getLogger(UserAspect.class);

    /**
     * 用户注册的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.UserService.userRegister(..))")
    public void userRegisterOperation() {
    }

    /**
     * 管理员登录的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.UserService.userLogin(..))")
    public void adminLoginOperation() {
    }

    /**
     * 在用户注册之后进行日志记录
     * @param joinPoint 切入点,这里是userService中的userRegister
     * @param retString 切入点的返回值
     */
    @AfterReturning(value = "com.project.smartcharge.system.aspect.UserAspect" +
            ".userRegisterOperation()", returning = "retString")
    public void afterUserRegister(JoinPoint joinPoint,String retString) {
        UserResponse.UserRegisterResponse userRegisterResponse =
                 JSON.parseObject(retString,UserResponse.UserRegisterResponse.class);
        int code = userRegisterResponse.getCode();
        Object[] args = joinPoint.getArgs();
        if (args.length!=0) {
            User user = (User) args[0];
            String username = user.getUsername();

            if (code == 200)
                logger.info("新用户注册到系统,用户名是 : " + username);
            else
                logger.warn("新用户注册失败,用户名是 : " + username);
        }
    }

    /**
     * 在用户登录之后进行日志记录
     * @param retString 切入点的返回值
     */
    /*@AfterReturning(value = "com.project.smartcharge.system.aspect.UserAspect.adminLoginOperation" +
            "()",returning = "retString")
    public void afterAdminLogin(String retString){
        UserResponse.UserLoginResponse userLoginResponse =
                 JSON.parseObject(retString,UserResponse.UserLoginResponse.class);
        String token = userLoginResponse.getToken();
        int i = MyToken.parseJWTGetUserCode(token);
        if (i==2)
        {
            String username = MyToken.parseJWTGetUsername(token);
            logger.info("管理员 "+username+" 已登录到系统");
        }
    }*/
}

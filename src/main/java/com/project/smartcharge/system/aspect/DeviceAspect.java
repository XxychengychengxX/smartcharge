/**
 * @author Valar Morghulis
 * @Date 2023/5/20
 */
package com.project.smartcharge.system.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.smartcharge.system.util.MyToken;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component

public class DeviceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DeviceAspect.class);

    /**
     * 管理员改变了设备信息的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.impl.ComplexServiceImpl" +
            ".updateDeviceWorkingStatus(..))")
    public void updateDeviceWorkingStatus() {
    }

    /**
     * 管理员查看设备等待车辆的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.impl.ComplexServiceImpl" +
            ".selectCarsInfoInDevice(..))")
    public void selectCarsInfoInDevice() {
    }

    /**
     * 管理员查看所有设备信息的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.impl.ComplexServiceImpl" +
            ".selectAllDevice(..))")
    public void selectAllDevice() {
    }

    /**
     * 管理员改变了设备信息的日志记录
     *
     * @param joinPoint 连接点
     * @param retString 返回的字符串
     */
    @AfterReturning(value = "updateDeviceWorkingStatus()", returning = "retString")
    public void afterAdminUpdateDevice(JoinPoint joinPoint, String retString) {
        Object[] args = joinPoint.getArgs();
        int deviceID = (int) args[0];
        int workingStatus = (int) args[1];
        String authorization = (String) args[2];
        JSONObject jsonObject = JSONObject.parseObject(retString);
        if (jsonObject != null) {
            String username = MyToken.parseJWTGetUsername(authorization);
            if (workingStatus == 1)
                logger.info("管理员 " + username + " 开机了编号为 " + deviceID + " 的设备");
            else
                logger.info("管理员 " + username + " 关机了编号为 " + deviceID + " 的设备");


        }
    }

    /**
     * 管理员查看所有设备信息后的日志记录
     *
     * @param joinPoint 连接点
     * @param retString 返回的字符串
     */
    @AfterReturning(value = "selectAllDevice()"
            , returning = "retString")
    public void afterSelectAllDevice(JoinPoint joinPoint, String retString) {
        Object[] args = joinPoint.getArgs();
        String authorization = (String) args[0];

        String username = MyToken.parseJWTGetUsername(authorization);

        logger.info("管理员 " + username + " 查看了所有设备");


    }

    /**
     * 管理员查看某充电桩等待车辆信息后的日志记录
     *
     * @param joinPoint 连接点
     * @param retString 返回字符串
     */
    @AfterReturning(value = "selectCarsInfoInDevice()", returning = "retString")
    public void afterAdminSelectCarsInfoInDevice(JoinPoint joinPoint, String retString) {
        Object[] args = joinPoint.getArgs();
        int deviceID = (int) args[0];
        String authorization = (String) args[1];
        JSONObject jsonObject = JSON.parseObject(retString);
        Integer code = jsonObject.getInteger("code");
        String username = MyToken.parseJWTGetUsername(authorization);
        if (code == 200) {
            logger.info("管理员 " + username + " 查看了编号为 " + deviceID + " 的设备");

        } else
            logger.warn("管理员 " + username + " 查看了编号为 " + deviceID + " 的设备,查看失败");


    }
}

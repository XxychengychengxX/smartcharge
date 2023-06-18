/**
 * @author Valar Morghulis
 * @Date 2023/6/4
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
public class BillAspect {

    private static final Logger logger = LoggerFactory.getLogger(BillAspect.class);

    /**
     * 用户创建新订单的切点
     */
    @Pointcut("execution(* com.project.smartcharge.service.impl.ComplexServiceImpl.createBill(..))")
    public void createBill() {
    }

    /**
     * 管理员查看历史所有订单的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.impl.ComplexServiceImpl" +
            ".selectHistoryBillByAdmin(..))")
    public void selectHistoryBillByAdmin() {
    }

    /**
     * 结束订单的切点
     */
    @Pointcut("execution(public * com.project.smartcharge.service.impl.ComplexServiceImpl" +
            ".cancelBill(..))")
    public void billComplete() {
    }

    /**
     * 管理元查看历史所有订单之后的通知
     *
     * @param joinPoint 连接点
     * @param retString 对应的切点返回的数据
     */
    @AfterReturning(value = "selectHistoryBillByAdmin()", returning =
            "retString")
    public void afterSelectAllBillByAdmin(JoinPoint joinPoint, String retString) {
        JSONObject jsonObject = JSON.parseObject(retString);
        Object[] args = joinPoint.getArgs();
        String authorization = (String) args[0];
        String username = MyToken.parseJWTGetUsername(authorization);
        Integer code = jsonObject.getInteger("code");
        if (code == 200)
            logger.info("管理员 " + username + " 查看了订单表所有订单 ");
    }

    /**
     * 订单创建通知
     * @param joinPoint 连接点
     * @param retString 返回的字符串
     */
    @AfterReturning(value = "createBill()", returning =
            "retString")
    public void afterUserCreateBill(JoinPoint joinPoint, String retString) {
        JSONObject jsonObject = JSON.parseObject(retString);
        Object[] args = joinPoint.getArgs();
        String authorization = (String) args[3];
        String username = MyToken.parseJWTGetUsername(authorization);
        int userID = MyToken.parseJWTGetUserID(authorization);
        Integer code = jsonObject.getInteger("code");
        if (code == 200)
            logger.info("用户 id 为 " + userID + " 的 " + username + " 用户创建了新订单请求 ");
    }

    /**
     * 订单完成通知（不管是取消订单还是订单正常完成）
     *
     * @param joinPoint 连接点
     * @param retString 返回的字符串
     */
    /*@AfterReturning(value = "billComplete()", returning = "retString")
    public void afterBillComplete(JoinPoint joinPoint, String retString) {
        JSONObject jsonObject = JSON.parseObject(retString);
        Object[] args = joinPoint.getArgs();
        int userID = (int) args[0];
        int billID = (int) args[1];
        Integer code = jsonObject.getInteger("code");
        if (code == 200)
            logger.info("用户 id 为 " + userID + " 的订单 id 为 " + billID + " 的订单已完成 ");
    }*/
}

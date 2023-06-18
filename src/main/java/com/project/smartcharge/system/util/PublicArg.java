/**
 * @author Valar Morghulis
 * @Date 2023/5/24
 */
package com.project.smartcharge.system.util;

/**
 * 公用变量说明，例如充电时间，jwt的签名密钥等等
 */
public class PublicArg {
    public static final String JWTSecretKey =
            "123456abc789defghxcakdskhfkdfbjzxkkajldjsanldsfsxzgxhdssdsfsgs";


    //峰时(1.0元/度，10:00~15:00，18:00~21:00)
    public static final double HIGH_TIME_CHARGE_FEE = 1.0;
    //平时(0.7元/度，7:00~10:00，15:00~18:00，21:00~23:00)
    public static final double LOW_TIME_CHARGE_FEE = 0.4;
    //谷时(0.4元/度，23:00~次日7:00)。
    public static final double NORMAL_TIME_CHARGE_FEE = 0.7;
    //服务费单价：0.8元/度
    public static final double SERVICE_FEE = 0.8;

    //快充功率
    public static final double FAST_CHARGING_RATE =30;

    //慢冲功率
    public static final double LOW_CHARGING_RATE =7;


}

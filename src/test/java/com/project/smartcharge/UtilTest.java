/**
 * @author Valar Morghulis
 * @Date 2023/5/27
 */
package com.project.smartcharge;

import com.project.smartcharge.system.util.MD5;
import com.project.smartcharge.system.util.MyToken;
import org.junit.jupiter.api.Test;


public class UtilTest {

    @Test
    void MD5DigestTest() {
        String s = MD5.MD5Encode("123456b");
        String s1 = MD5.MD5Encode("123456a");
        String s2 = MD5.MD5Encode(s);
        String s3 = MD5.MD5Encode(s1);
        System.out.println(s);
        System.out.println(s1);
        System.out.println("s.equals(s1) = " + s.equals(s1));
        System.out.println("s2.equals(s3) = " + s2.equals(s3));

    }

    @Test
    public void JWTGenerateAndParseTest() {
        String liucanJWT = MyToken.createJWT(4,"gujun", 2);
        System.out.println("liucanJWT = " + liucanJWT);

        String s = MyToken.parseJWTGetUsername(liucanJWT);
        System.out.println("s = " + s);

    }

    /*
    -------------------------------以下是MyDate类的方法测试---------------------------
    -------------------------------以下是MyDate类的方法测试---------------------------
    -------------------------------以下是MyDate类的方法测试---------------------------
     */
    @Test
    public void MyDateGetTimeSlotTest() {

    }


    @Test
    public void MydateGotTokenExpireTimeTest() {

    }


    @Test
    public void MyDateAddTimeTest() {

    }
}

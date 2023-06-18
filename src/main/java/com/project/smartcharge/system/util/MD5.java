/**
 * @author Valar Morghulis
 * @Date 2023/5/18
 */
package com.project.smartcharge.system.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密密码的工具类
 */
public class MD5 {

    //md5加密算法，只能加密无法解密
    public static String MD5Encode(String s) {

        String retString = "";
        try {
            //获得md5加密的对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            //获得加密后的数据
            byte[] digest = md5.digest(s.getBytes());

            retString=bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return retString;
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Character.forDigit((b >> 4) & 0xF, 16));
            sb.append(Character.forDigit(b & 0xF, 16));
        }
        return sb.toString();
    }
}

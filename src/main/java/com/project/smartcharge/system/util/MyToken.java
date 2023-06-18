/**
 * @author Valar Morghulis
 * @Date 2023/5/20
 */
package com.project.smartcharge.system.util;

import io.jsonwebtoken.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 生成密钥的工具类
 */
public class MyToken {

    public static String createJWT(Integer id, String username, Integer userCode) {
        Date tokenExpireTime = MyDate.getTokenExpireTime();
        //添加主题，用户权限验证
        JwtBuilder jwtBuilder = Jwts.builder().setSubject("userRoleConfirm")
                .setIssuedAt(MyDate.getNowTimeInDate())
                .setExpiration(tokenExpireTime)
                .claim("id", id).claim("username", username).claim("userCode", userCode).signWith(SignatureAlgorithm.HS256, PublicArg.JWTSecretKey
                );
        String s = jwtBuilder.compact();
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    /**
     * 根据传入的jwt来解析用户名
     *
     * @param jwt 传入的jwt
     * @return 对应的用户名的字符串, 如果签名过期则返回空串
     */
    public static String parseJWTGetUsername(String jwt) {
        String s = jwt.substring(7);
        String decode = URLDecoder.decode(s, StandardCharsets.UTF_8);

        /*String decode = URLDecoder.decode(jwt, StandardCharsets.UTF_8);
        String s = decode.substring(7);*/
        try {
            //进行用户名的解析
            //开始解析jwt对象
            Jws<Claims> claimsJws =
                    Jwts.parserBuilder().setSigningKey(PublicArg.JWTSecretKey).build().parseClaimsJws(decode);

            Claims claims = claimsJws.getBody();
            Date expiration = claims.getExpiration();
            if (expiration.after(new Date()))
                //从jwtToken中解析出username
                return claims.get("username", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        return "";
    }

    /**
     * 根据传入的jwt来解析用户id
     *
     * @param jwt 传入的jwt
     * @return 对应的用户名的字符串
     */
    public static int parseJWTGetUserID(String jwt) {
        String s = jwt.substring(7);
        String decode = URLDecoder.decode(s, StandardCharsets.UTF_8);
        //进行用户名的解析
        //开始解析jwt对象
        Jws<Claims> claimsJws =
                Jwts.parserBuilder().setSigningKey(PublicArg.JWTSecretKey).build().parseClaimsJws(decode);
        Claims claims = claimsJws.getBody();
        Date expiration = claims.getExpiration();
        if (expiration.after(new Date()))
            //从jwtToken中解析出username
            return claims.get("id", Integer.class);
        return -1;
    }

    /**
     * 根据传入的jwt来解析用户身份
     *
     * @param jwt 传入的jwt
     * @return 对应的用户名的字符串
     */
    public static int parseJWTGetUserCode(String jwt) {
        String s = jwt.substring(7);
        String decode = URLDecoder.decode(s, StandardCharsets.UTF_8);

        //进行用户名的解析
        //开始解析jwt对象
        Jws<Claims> claimsJws =
                Jwts.parserBuilder().setSigningKey(PublicArg.JWTSecretKey).build().parseClaimsJws(decode);
        Claims claims = claimsJws.getBody();
        Date expiration = claims.getExpiration();
        if (expiration.after(new Date()))
            //从jwtToken中解析出username
            return claims.get("userCode", Integer.class);
        return -1;

    }
}

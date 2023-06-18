/**
 * @author Valar Morghulis
 * @Date 2023/5/22
 */
package com.project.smartcharge.system.respon;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 封装了响应给controller的JSON字符串的对象
 */
public class ResponseGenerate {

    /**
     * 封装通常更新响应的JSON字符串
     *
     * @param res     数据库查询结果成功否
     * @param message 传入的信息
     * @return 通常更新响应的JSON字符串
     */
    public static String generateNormalUpdateResp(boolean res, int code,String message) {

        SimpleResponse normalUpdateResp =
                new SimpleResponse();
        //如果持久层出了问题
        if (res) {
            normalUpdateResp.setCode(code);
            normalUpdateResp.setMessage(message);
            return JSON.toJSONString(normalUpdateResp);
        }
        normalUpdateResp.setCode(500);
        normalUpdateResp.setMessage("持久层出错,请检查服务器后重试");
        return JSON.toJSONString(normalUpdateResp);
    }

    /**
     * 封装通常查询响应的JSON字符串
     *
     * @param res     数据库查询结果成功否
     * @param message 传入的信息
     * @param resBody 查询出来的数据库结果
     * @return 通常查询响应的JSON字符串
     */
    public static String generateNormalSelectResp(boolean res, int code, String message,
                                                  String resBody) {
        NormalSelectResponse normalSelectResponse = new NormalSelectResponse();
        //如果持久层出了问题
        if (res) {
            normalSelectResponse.setCode(code);
            normalSelectResponse.setMessage(message);
            normalSelectResponse.setResBody(resBody);
            return JSON.toJSONString(normalSelectResponse);
        } else {
            normalSelectResponse.setCode(500);
            normalSelectResponse.setMessage("持久层出错,请检查服务器后重试");
            normalSelectResponse.setResBody("");
            return JSON.toJSONString(normalSelectResponse);
        }
    }

    /**
     * 查询表元素封装，一般来说响应给前端的字符串是 { ”code“：200（表示完成对应请求）||500（表示出错） ”msg“：（查询成功或者失败） ”resbody“：（查询出来的信息）
     * }
     */
    @Data
    public static class NormalSelectResponse {
        int code;
        String message;

        String resBody;
    }

    /**
     * 更新表元素封装，一般来说响应给前端的字符串是 { ”code“：200（表示完成对应请求）||500（表示出错） ”message“：（更新成功或者更新失败） }
     */
    @Data
    public static class SimpleResponse {
        int code;
        String message;

    }

}

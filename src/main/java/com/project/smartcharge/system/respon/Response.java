/**
 * @author Valar Morghulis
 * @Date 2023/6/2
 */
package com.project.smartcharge.system.respon;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public class Response {
    /**
     * 更新表元素封装，一般来说响应给前端的字符串是 { ”code“：200（表示完成对应请求）||500（表示出错） ”message“：（更新成功或者更新失败） }
     */
    @Data
    public static class SimpleResponse {
        private int code;
        private String message;

        public static String generate(boolean res, int code, String message) {
            SimpleResponse r = new SimpleResponse();
            if (res) {
                r.setCode(code);
                r.setMessage(message);
            } else {
                r.setCode(500);
                r.setMessage("服务器出错,请检查服务器后重试");
            }
            return JSON.toJSONString(r);
        }

    }

    /**
     * 查询表元素封装，一般来说响应给前端的字符串是 { ”code“：200（表示完成对应请求）||500（表示出错） ”msg“：（查询成功或者失败） ”resbody“：（查询出来的信息）
     * }
     */
    @Data
    public static class NormalResponse {
        private int code;
        private String message;
        private String resBody;

        public static String generate(boolean res, int code, String message, String resBody) {
            NormalResponse r = new NormalResponse();
            if (res) {
                r.setCode(code);
                r.setMessage(message);
                r.setResBody(resBody);

            } else {
                r.setCode(500);
                r.setMessage("服务器出错,请检查服务器后重试");
            }
            return JSON.toJSONString(r);
        }
    }

    /**
     * 查询元素封装，封装单个请求
     */
    @Data
    public static class OneBillResponse {
        private int code;
        private int amount;
        private boolean chargingArea;
        private boolean fast;
        private int deviceID;
        private boolean waitingArea;
        private String status;
        private int totalAmount;//总价钱
        private int position;//排队号码

        public static String generate(boolean res, int code, int amount, boolean chargingArea,
                                      boolean fast,
                                      int deviceID, boolean waitingArea, String status,
                                      int totalAmount, int position) {
            OneBillResponse r = new OneBillResponse();
            if (res) {
                r.setCode(code);
                r.setAmount(amount);
                r.setChargingArea(chargingArea);
                r.setFast(fast);
                r.setDeviceID(deviceID);
                r.setWaitingArea(waitingArea);
                r.setStatus(status);
                r.setTotalAmount(totalAmount);
                r.setPosition(position);
            } else {
                r.setCode(500);
            }
            return JSON.toJSONString(r);
        }
    }

    /**
     * 查询元素封装，封装单个请求详单
     */
    @Data
    public static class BillDetailResponse {
        private int chargeAmount;
        private String chargeEndTime;
        private int chargeFee;
        private String chargeStartTime;
        private String created_at;
        private String deleted_at;
        private int id;
        private int pileId;
        private int serviceFee;
        private int totalFee;
        private String updated_at;
        private int userId;


        public static String generate(boolean res, int chargeAmount, String chargeEndTime,
                                      int chargeFee,
                                      String chargeStartTime, String created_at, String deleted_at,
                                      int id, int pileId, int serviceFee, int totalFee,
                                      String updated_at, int userId) {
            BillDetailResponse r = new BillDetailResponse();
            if (res) {
                r.setChargeAmount(chargeAmount);
                r.setChargeEndTime(chargeEndTime);
                r.setChargeFee(chargeFee);
                r.setChargeStartTime(chargeStartTime);
                r.setCreated_at(created_at);
                r.setDeleted_at(deleted_at);
                r.setId(id);
                r.setPileId(pileId);
                r.setServiceFee(serviceFee);
                r.setTotalFee(totalFee);
                r.setUpdated_at(updated_at);
                r.setUserId(userId);
            }
            return JSON.toJSONString(r);
        }

    }

    /**
     * 查询元素封装，封装服务器时间
     */
    @Data
    public static class timeResponse {
        private long time;
        private int code;

        public static String generate(boolean res, Date date) {
            timeResponse r = new timeResponse();

            if (res) {
                r.setCode(200);
                r.setTime(date.getTime());
            }
            return JSON.toJSONString(r);
        }
    }

    /**
     * 查询元素封装，封装用户注册回应
     */
    @Data
    public static class UserRegisterResponse {
        private int code;
        private String message;

        public static String generate(boolean res, int code, String message) {
            UserRegisterResponse r = new UserRegisterResponse();
            if (res) {
                r.setCode(code);
                r.setMessage(message);
            }
            return JSON.toJSONString(r);
        }
    }

    /**
     * 查询元素封装，封装用户登录回应
     */
    @Data
    public static class UserLoginResponse {
        private int code;
        private String expireTime;
        private String token;
        private String message;

        //这里加入static方法
        public static String generate(boolean res, int code, String expireTime, String token,
                                      String message) {
            UserResponse.UserLoginResponse r = new UserResponse.UserLoginResponse();
            if (res) {
                r.setCode(code);
                r.setExpireTime(expireTime);
                r.setToken(token);
                r.setMessage(message);
            } else {
                r.setCode(500);
                r.setMessage("服务器出错,请检查服务器后重试");
            }
            return JSON.toJSONString(r);
        }

    }


    /**
     * 元素封装，封装用户Token
     */
    @Data
    public static class UserTokenResponse {
        private int code;
        private String expire;

        public static String generate(boolean res, int code, String expire) {
            UserTokenResponse r = new UserTokenResponse();
            if (res) {
                r.setCode(code);
                r.setExpire(expire);
            } else {
                r.setCode(500);
            }
            return JSON.toJSONString(r);
        }
    }


    /**
     * 充电桩等待详情返回消息
     */
    @Data
    public static class UserWaitingInfoResponse {
        private int code;
        private int id;
        private String message;
        private List<UserWaiting> users;

        public static String generate(boolean res, String message, int code
                                      ,int deviceID,List<UserWaiting> users) {
            UserWaitingInfoResponse r = new UserWaitingInfoResponse();
            if (res) {
                r.setId(deviceID);
                r.setMessage(message);
                r.setCode(code);
                r.setUsers(users);
            } else
                r.setCode(500);

            return JSON.toJSONString(r);
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        public static class UserWaiting {
            int id;
            int amount;
            String status;
            int totalAmount;
            String waitTime;
        }
    }
}

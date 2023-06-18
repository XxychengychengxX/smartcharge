/**
 * @author Valar Morghulis
 * @Date 2023/5/24
 */
package com.project.smartcharge.system.respon;

import lombok.Data;

/**
 * 特殊的order封装（与其他组对接口封装）
 */
public class BillResponse {

    @Data
    public static class checkOneBillResponse {
        int code;
        int amount;
        boolean chargingArea;
        boolean fast;
        int deviceID;
        boolean waitingArea;
        int pile;
        String status;//

        double totalAmount;//总价钱

        int position;//排队号码
    }

    @Data
    public static class CheckBillByIDResp {
        int chargeAmount;
        String chargeEndTime;
        double chargeFee;
        String chargeStartTime;
        String created_at;
        String deleted_at;
        int id;
        int pileId;
        double serviceFee;
        double totalFee;
        String updated_at;
        int userId;
    }
}


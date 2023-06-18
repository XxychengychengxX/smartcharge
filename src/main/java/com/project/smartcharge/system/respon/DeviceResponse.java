/**
 * @author Valar Morghulis
 * @Date 2023/5/24
 */
package com.project.smartcharge.system.respon;

import com.project.smartcharge.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 特殊的device封装（与其他组对接口封装）
 */
public class DeviceResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class selectDeviceDetailByID {
        private int code;

        private String status;
        private int id;

        private double chargeAmount;

        private int chargeTimes;

        private String chargeTime;


    }

    @Data
    public static class selectDeviceTable {
        private double chargeAmount;
        private double chargeFee;
        private String chargeTime;
        private int chargeTimes;
        private int id;
        private double serviceFee;
        private double totalFee;

    }

    @Data
    public static class updateDeviceTableResp {
        private int code;

        private String message;
    }

    @Data
    public static class selectDeviceWaitingStatusResp {
        List<User> users;
        private int code;
    }

    @Data
    public static class updateDeviceStatusResp {
        String created_at;
        boolean fast;
        int id;
        int status;
        String updated_at;

        String delete_at;
    }
}
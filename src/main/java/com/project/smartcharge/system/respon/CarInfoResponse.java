/**
 * @author Valar Morghulis
 * @Date 2023/5/24
 */
package com.project.smartcharge.system.respon;

import lombok.Data;

/**
 * 特殊的carinfo封装（与其他组对接口封装）
 */
public class CarInfoResponse {

    @Data
    public static class updateCarTableResp {
        private int code;

        private String message;
    }

    @Data
    public static class selectCarTableResp {
        private int code;

        private String message;

        private String res;
    }
}

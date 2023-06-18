/**
 * @author Valar Morghulis
 * @Date 2023/5/30
 */
package com.project.smartcharge.controller;

import com.project.smartcharge.service.ComplexService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@ComponentScan(basePackages = "com.project.smartcharge.service")
@RequestMapping(value = "/charge", produces = "text/html;charset=utf-8")
public class BillController {

    @Resource
    ComplexService complexService;


    @GetMapping("/bill/{billId}")
    public String getBillByID(@RequestHeader("Authorization") String authorization,
                              @PathVariable("billId") Integer billID) {
        return complexService.selectBillByid(authorization, billID);
    }

    /**
     * 用户查询当前订单
     *
     * @param authorization 令牌token
     * @return 查看当前充电订单的JSON数组
     */
    @GetMapping
    public String checkOnesBill(@RequestHeader("Authorization") String authorization) {
        return complexService.selectChargeStatusByUserID(authorization);
    }

    /**
     * 更新订单
     *
     * @param amount        充电量
     * @param fast          是否是快充
     * @param totalAmount   电池容量
     * @param authorization 令牌token
     * @return 是否更新成功的JSON数组
     */
    @PutMapping
    public String updateBill(Integer amount, boolean fast, Integer totalAmount,
                             @RequestHeader("Authorization") String authorization) {

        return complexService.updateBill(amount, fast, totalAmount, authorization);
    }

    /**
     * 用户创建订单，创建充电请求
     *
     * @param amount        充电度数申请
     * @param fast          此次请求是否是快充
     * @param totalAmount   车辆电池大小
     * @param authorization token令牌
     * @return 更新成功的JSON字符串
     */
    @PostMapping
    public String createBill(int amount, boolean fast, int totalAmount,
                             @RequestHeader("Authorization") String authorization) {
        return complexService.createBill(amount, fast, totalAmount, authorization);
    }

    /**
     * 取消订单
     *
     * @param authorization 令牌token
     * @return 提示字符串
     */
    @DeleteMapping
    public String cancelBill(@RequestHeader("Authorization") String authorization) {
        return complexService.deleteCurrentBill(authorization);
    }


    /**
     * 取消订单
     *
     * @param authorization 令牌token
     * @return 提示字符串
     */
    @PostMapping("/finish")
    public String finishBIll(@RequestHeader("Authorization") String authorization) {
        return complexService.deleteCurrentBill(authorization);
    }

    /**
     * 查看
     *
     * @param authorization 令牌token
     * @return
     */
    @GetMapping("/checkAllBillsByAdmin")
    public String checkAllBillsByAdmin(@RequestHeader("Authorization") String authorization) {
        return complexService.selectHistoryBillByAdmin(authorization);
    }

    /**
     * 用户查询历史订单
     *
     * @param authorization 令牌token
     * @param page          第几页的订单
     * @return JSON字符串
     */
    @GetMapping("/userHistoryBills")
    public String userHistoryBills(@RequestHeader("Authorization") String authorization,
                                   @RequestParam(value = "page", required = false, defaultValue =
                                           "-1") Integer page) {
        return complexService.selectHistoryBillByUser(authorization, page);
    }


}

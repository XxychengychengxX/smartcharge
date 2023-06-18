/**
 * @author Valar Morghulis
 * @Date 2023/5/31
 */
package com.project.smartcharge.controller;

import com.project.smartcharge.service.ComplexService;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AnotherController {
    @Resource
    ComplexService complexService;

    @GetMapping("/piles")
    public String getAllPile(@RequestHeader("Authorization") String authorization) {
        return complexService.selectAllDevice(authorization);
    }

    @GetMapping("/report")
    public String getReport(@RequestHeader("Authorization") String authorization) {
        return complexService.selectAllDevice(authorization);
    }

    @GetMapping("/ping")
    public String ping() {
        return complexService.ping();
    }

    /**
     * 获取服务器系统时间
     *
     * @return 时间字符串
     */
    @PutMapping("/time")
    public String time(@RequestParam("time") long time,
                       @RequestHeader("Authorization") String authorization) {
        return complexService.updateTime(time,authorization);
    }
    /**
     * 获取服务器系统时间
     *
     * @return 时间字符串
     */
    @PutMapping("/time2")
    public String time2(@RequestParam("time") long time,
                       @RequestHeader("Authorization") String authorization) {
        return complexService.updateTime(time,authorization);
    }

    /**
     * 获取服务器系统时间
     *
     * @return 时间字符串
     */
    @GetMapping("/time")
    public String time() {
        return complexService.getTimeByInterface();
    }
}

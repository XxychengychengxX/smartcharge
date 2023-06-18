/**
 * @author Valar Morghulis
 * @Date 2023/5/28
 */
package com.project.smartcharge.controller;

import com.project.smartcharge.service.ComplexService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pile", produces = "text/html;charset=utf-8")
@CrossOrigin
@Transactional
public class PileController {

    @Resource
    ComplexService complexService;

    /**
     * GET 获取指定的充电桩状态
     *
     * @param pileId        充电桩id
     * @param authorization 发送过来的httpRequest对象(后面要进行用户信息的验证，注入等等)
     * @return 根据查询结果或者权限等等返回相应给前端的JSON字符串
     */
    @GetMapping("/{pileId}")
    public String selectPileByPileID(@PathVariable int pileId,
                                     @RequestHeader("Authorization") String authorization) {
        return complexService.selectDeviceByDeviceID(pileId, authorization);

    }

    /**
     * PUT 修改指定的充电桩状态
     *
     * @param pileId        充电桩id
     * @param authorization 发送过来的httpRequest对象(后面要进行用户信息的验证，注入等等)
     * @param status        欲改变的设备状态
     * @return 根据查询结果或者权限等等返回相应给前端的JSON字符串
     */
    @PutMapping(value = "/{pileId}/{status}")
    public String updatePileStatus(@PathVariable int pileId,
                                   @RequestHeader("Authorization") String authorization,
                                   @PathVariable int status) {
        return complexService.updateDeviceWorkingStatus(pileId, status, authorization);

    }

    /**
     * GET 获取指定的充电桩状态及待处理的用户请求
     *
     * @param pileId        充电桩id
     * @param authorization 请求头中含有的authorization参数
     * @return 根据查询结果或者权限等等返回相应给前端的JSON字符串
     */
    @GetMapping("/{pileId}/wait")
    public String selectPileWaitingConditionByPileID(@PathVariable int pileId,
                                                     @RequestHeader("Authorization") String authorization) {
        return complexService.selectPileWaitingConditionByPileID(pileId, authorization);

    }


}

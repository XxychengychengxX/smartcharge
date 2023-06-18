/**
 * @author Valar Morghulis
 * @Date 2023/5/30
 */
package com.project.smartcharge.controller;

import com.project.smartcharge.pojo.Carinfo;
import com.project.smartcharge.service.ComplexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController//Restful风格的控制器。返回的类型都自带@ResponseBody，即字符串
@RequestMapping(value = "/car", produces = "text/html;charset=utf-8" )//域名
@CrossOrigin//开启跨域访问，这里是允许所有跨域访问
public class CarController {

    @Autowired//自动注入的类型
    ComplexService complexService;

    /**
     * 用于车辆注册的CarInfo
     * @param authorization 令牌token
     * @param carinfo 传入的类实例,spring自动装配,前端数据匹配即可
     * @return 注册是否成功JSON字符串
     */
    @PostMapping("/register")//接收的是POST请求
    public String carRegister(@RequestHeader("Authorization") String authorization,
                              Carinfo carinfo) {
        return complexService.carRegister(authorization, carinfo);
    }

    /**
     * 用于用户删除自己的注册车辆
     *
     * @param authorization 令牌token
     * @return 是否删除成功的JSON字符串
     */
    @PutMapping("/delete")
    public String carDelete(@RequestHeader("Authorization") String authorization) {
        return complexService.carDeleteByUserID(authorization);
    }

    /**
     * 获取用户车辆信息
     *
     * @param authorization token令牌
     * @return 车辆信息的JSON字符串, 或者出错后返回401
     */
    @GetMapping(value = "/getCar", produces = "text/html;charset=utf-8")
    public String carSelect(@RequestHeader("Authorization") String authorization) {
        return complexService.selectCarByUserID(authorization);
    }

    /**
     * 获取
     * @param authorization 令牌token
     * @return 返回当前的汽车排位
     */
    @GetMapping("/selectCarRank")
    public String getCarRank(@RequestHeader("Authorization") String authorization){
        return complexService.selectCarRank(authorization);
    }
}

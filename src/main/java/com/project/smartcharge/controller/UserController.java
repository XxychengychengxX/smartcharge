/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.controller;

import com.project.smartcharge.pojo.User;
import com.project.smartcharge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping(value = "/register", produces = "text/html;charset=utf-8")
    public String userRegister( @RequestParam("username") String username,
                                @RequestParam("password")String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.userRegister(user);
    }

    @PostMapping(value = "/login", produces = "text/html;charset=utf-8")
    public String userLogin(@RequestParam("username") String username,
                            @RequestParam("password")String password) {
        return userService.userLogin(username, password);
    }

    @GetMapping(value = "/getUsername", produces = "text/html;charset=utf-8")
    public String getUsername(@RequestHeader("Authorization") String token) {

        return userService.getUsernameByToken(token);
    }

    @GetMapping(value = "/refresh", produces = "text/html;charset=utf-8")
    public String refresh(@RequestHeader("Authorization") String token) {

        return userService.getUsernameByToken(token);

    }


    @GetMapping(value = "/getUserID", produces = "text/html;charset=utf-8")
    public String getUserID(@RequestHeader("Authorization") String token) {

        return userService.getUsernameByToken(token);
    }
}

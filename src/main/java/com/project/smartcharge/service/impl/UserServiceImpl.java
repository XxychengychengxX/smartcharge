/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service.impl;

import com.alibaba.fastjson.JSON;
import com.project.smartcharge.mapper.UserMapper;
import com.project.smartcharge.pojo.Carinfo;
import com.project.smartcharge.pojo.User;
import com.project.smartcharge.pojo.UserExample;
import com.project.smartcharge.service.UserService;
import com.project.smartcharge.system.respon.Response;
import com.project.smartcharge.system.respon.UserResponse;
import com.project.smartcharge.system.util.MD5;
import com.project.smartcharge.system.util.MyDate;
import com.project.smartcharge.system.util.MyToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * userService接口的具体实现类
 */
@Service("userService")
@Transactional(timeout = 10)//表示所有的方法都需要事务注解
public class UserServiceImpl implements UserService {


    @Autowired
    @Qualifier("userMapper")
    public UserMapper userMapper;

    /**
     * 封装产生的登录成功的方法
     *
     * @param respCode 传入的respcode
     * @return 封装完成的String
     */
    private static String userRegisterResult(int respCode) {
        UserResponse.UserRegisterResponse userRegisterResponse =
                new UserResponse.UserRegisterResponse();
        userRegisterResponse.setCode(respCode);
        if (respCode == 200)
            userRegisterResponse.setMessage("注册成功");
        else
            userRegisterResponse.setMessage("用户名已存在,注册失败");
        return JSON.toJSONString(userRegisterResponse);
    }

    /**
     * 封装产生的登录成功的方法
     *
     * @param jwt        传入的jwt
     * @param expireTime 传入的字符串形式的过期时间
     * @return 封装完成的String
     */
    private static String userLoginSuccess(String jwt, String expireTime) {
        UserResponse.UserLoginResponse userLoginResponse =
                new UserResponse.UserLoginResponse();


        userLoginResponse.setCode(200);
        userLoginResponse.setExpireTime(expireTime);
        userLoginResponse.setToken(jwt);
        userLoginResponse.setMessage("登陆成功");
        return JSON.toJSONString(userLoginResponse);
    }

    private static String userLoginInWrongPassword() {
        UserResponse.UserLoginResponse userLoginResponse =
                new UserResponse.UserLoginResponse();
        //500是错误
        userLoginResponse.setCode(500);
        userLoginResponse.setMessage("密码不正确");
        return JSON.toJSONString(userLoginResponse);
    }

    private static String userLoginInUserNameMiss() {
        UserResponse.UserLoginResponse userLoginResponse =
                new UserResponse.UserLoginResponse();
        //500是错误
        userLoginResponse.setCode(400);
        userLoginResponse.setMessage("用户名不存在");
        return JSON.toJSONString(userLoginResponse);
    }

    /**
     * 通过jwt获取用户id
     *
     * @param jwt token字符串
     * @return 给出解析后的用户名
     */
    @Override
    public int getUserIDByToken(String jwt) {
        return MyToken.parseJWTGetUserID(jwt);
    }

    /**
     * 通过jwt获取用户名
     * @param jwt token字符串
     * @return 给出解析后的用户名
     */
    @Override
    public String getUsernameByToken(String jwt) {
        return MyToken.parseJWTGetUsername(jwt);
    }

    /**
     * 刷新token
     *
     * @param authorization 令牌token
     */
    @Override
    public String refresh(String authorization) {
        int userID = MyToken.parseJWTGetUserID(authorization);
        int i = MyToken.parseJWTGetUserCode(authorization);
        String s = MyToken.parseJWTGetUsername(authorization);
        String jwt = MyToken.createJWT(userID, s, i);

        String expireTime = MyDate.getNeedTimeInString(MyDate.getTokenExpireTime());
        UserResponse.UserLoginResponse userLoginResponse =
                new UserResponse.UserLoginResponse();
        userLoginResponse.setCode(200);
        userLoginResponse.setMessage("刷新成功");
        userLoginResponse.setToken(jwt);
        userLoginResponse.setExpireTime(expireTime);
        return JSON.toJSONString(userLoginResponse);
    }

    /**
     * 根据用户输入的用户名和密码来确认是否登录成功，若不成功则给出对应的信息
     *
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return 1表示用户不存在，2表示用户密码错误，3表示登录成功
     */
    @Override
    public String userLogin(String username, String password) {
        //用户名查找
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(userExample);
        //如果该用户名存在
        if (users.size() == 1) {
            //拿到用户密码并进行匹配
            User user = users.get(0);
            //jwt注入用户id和用户角色
            Integer userID = user.getUserID();
            Integer userCode = user.getUserCode();

            String inputPassword = user.getPassword();
            String s = MD5.MD5Encode(password);
            //如果密码正确，完成jwt token的注入
            if (s.equals(inputPassword)) {
                Carinfo carinfo = new Carinfo();
                carinfo.setCarRank(-1);
                carinfo.setCarDeck(username);
                carinfo.setWaitingStatu((byte)-1);
                carinfo.setBatterySize(100.0);
                carinfo.setUserID(userID);

                //拿到的是userID
                //根据传入的创建jwt
                String jwt = MyToken.createJWT(userID,username,userCode);
                //获取过期时间
                String expireTime = MyDate.getNeedTimeInString(MyDate.getTokenExpireTime());
                /* //加密
                String encode = URLEncoder.encode(jwt, StandardCharsets.UTF_8);

                //将信息保存到cookie
                Cookie cookie = new Cookie("Authorization",encode);
                cookie.setMaxAge(3600);

                //设置cookie的上下文路径
                cookie.setPath("/");
                httpServletResponse.addCookie(cookie);*/
                //设置过期时间为30分钟
                return Response.UserLoginResponse.generate(true,200,expireTime,jwt, "欢迎光临,登录成功!");
            }
            //若不正确
            else {
                return Response.UserLoginResponse.generate(true,500,null,null, "密码不正确");
            }
        }
        //不存在则返回1
        return Response.UserLoginResponse.generate(true,404,null,null, "用户名不存在");
    }

    /**
     * 用户注册
     *
     * @param user 传入的用户实体类信息
     * @return 成功返回true 反之false
     */
    @Override
    public String userRegister(User user) {
        //拿到用户名
        String username = user.getUsername();
        user.setUserCode(1);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (!users.isEmpty())
            return Response.UserLoginResponse.generate(true,500,null,null,"用户名已存在!");
        //拿到用户密码
        String password = user.getPassword();
        //进行加密处理
        String newPassWord = MD5.MD5Encode(password);
        user.setPassword(newPassWord);


        //然后插入操作,如果成功
        if (userMapper.insert(user) == 1)
            return userRegisterResult(200);
        //如果失败
        return userRegisterResult(500);
    }

    /**
     * 管理员根据用户逐渐删除用户
     *
     * @param id 用户主键id
     * @return 删除成功返回true，反之false
     */
    @Override
    public boolean userDeletedByPrimaryKey(int id) {
        //用户注销需求
        int i = userMapper.deleteByPrimaryKey(id);
        return i == 1;
    }

    /**
     * 根据传入的用户名来判断用户身份
     *
     * @param s 传入的令牌(可能保存在httpsession中)
     * @return 用户的userCode 1：普通客户 2：系统管理员
     */
    @Override
    public int userCodeCheck(String s) {

        String username = MyToken.parseJWTGetUsername(s);

        int userCode;
        //创建一个新的example对象
        UserExample userExample = new UserExample();
        //创建一个新的标准
        UserExample.Criteria criteria = userExample.createCriteria();
        //添加对应的标准方法
        criteria.andUsernameEqualTo(username);

        List<User> users = userMapper.selectByExample(userExample);
        //如果users表中不止有这个项
        if (users.size() != 1)
            throw new RuntimeException("user表信息不正确,请检查");
        else {
            //拿到对象
            User user = users.get(0);
            //拿到角色定位
            userCode = user.getUserCode();
        }
        return userCode;
    }
}

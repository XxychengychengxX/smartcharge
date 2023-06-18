/**
 * @author Valar Morghulis
 * @Date 2023/5/19
 */
package com.project.smartcharge.service;

import com.project.smartcharge.pojo.User;

/**
 * user业务逻辑的接口，一般是提供给ComplexService进行调用
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 传入的用户实体类信息
     * @return 成功返回true 反之false
     */
    String userRegister(User user);

    /**
     * 管理员根据用户逐渐删除用户
     *
     * @param id 用户主键id
     * @return 删除成功返回true，反之false
     */
    boolean userDeletedByPrimaryKey(int id);

    /**
     * 根据传入的令牌解析来判断用户身份
     *
     * @param authorization 传入的token令牌对象
     * @return 用户的userCode 1：普通客户 2：系统管理员
     */
    int userCodeCheck(String authorization);

    /**
     * 根据用户输入的用户名和密码来确认是否登录成功，若不成功则给出对应的信息
     *
     * @param username 用户输入的用户名
     * @param password 用户输入的密码
     * @return 1表示用户不存在，2表示用户密码错误，3表示登录成功
     */
    String userLogin(String username, String password);

    /**
     * 通过jwt获取用户名
     *
     * @param jwt token字符串
     * @return 给出解析后的用户名
     */
    String getUsernameByToken(String jwt);

    /**
     * 通过jwt获取用户id
     *
     * @param jwt token字符串
     * @return 给出解析后的用户名
     */
    int getUserIDByToken(String jwt);

    /**
     * 刷新token
     * @param authorization 令牌token
     */
    String refresh(String authorization);

}

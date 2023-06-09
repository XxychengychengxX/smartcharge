package com.project.smartcharge.pojo;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userID
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    private Integer userID;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.username
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userCode
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    private Integer userCode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userID
     *
     * @return the value of user.userID
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userID
     *
     * @param userID the value for user.userID
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.username
     *
     * @return the value of user.username
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.username
     *
     * @param username the value for user.username
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password
     *
     * @return the value of user.password
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password
     *
     * @param password the value for user.password
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userCode
     *
     * @return the value of user.userCode
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public Integer getUserCode() {
        return userCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userCode
     *
     * @param userCode the value for user.userCode
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Fri Jun 02 17:49:13 CST 2023
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userID=").append(userID);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", userCode=").append(userCode);
        sb.append("]");
        return sb.toString();
    }
}
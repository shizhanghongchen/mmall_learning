package com.mmall.pojo;

import java.util.Date;

/**
 * Created by wb-yxk397023 on 2018/7/25.
 */
public class User {

    /**
     * 用户主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 找回密码问题
     */
    private String question;

    /**
     * 找回密码答案
     */
    private String answer;

    /**
     * 角色(0=管理员,1=普通用户)
     */
    private Integer role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 全参构造器
     *
     * @param id         用户主键id
     * @param username   用户名
     * @param password   密码
     * @param email      邮箱
     * @param phone      电话
     * @param question   找回密码问题
     * @param answer     找回密码答案
     * @param role       角色(0=管理员,1=普通用户)
     * @param createTime 创建时间
     * @param updateTime 更新时间
     */
    public User(Integer id, String username, String password, String email, String phone, String question, String answer, Integer role, Date createTime, Date updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.question = question;
        this.answer = answer;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    /**
     * 无参构造器
     */
    public User() {
        super();
    }

    /**
     * 用户主键id
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 用户主键id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * 密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 邮箱
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 电话
     *
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 电话
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 找回密码问题
     *
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * 找回密码问题
     *
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    /**
     * 找回密码答案
     *
     * @return
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 找回密码答案
     *
     * @param answer
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 角色(0=管理员,1=普通用户)
     *
     * @return
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 角色(0=管理员,1=普通用户)
     *
     * @param role
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 创建时间
     *
     * @return
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     *
     * @return
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * User is toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", role=" + role +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
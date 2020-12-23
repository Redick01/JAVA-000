package com.a.mapper;

import java.util.Date;

public class Account {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.user_id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Long balance;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.freeze_amount
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Long freezeAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.create_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.update_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column account.dollar_balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    private Long dollarBalance;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.id
     *
     * @return the value of account.id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.id
     *
     * @param id the value for account.id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.user_id
     *
     * @return the value of account.user_id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.user_id
     *
     * @param userId the value for account.user_id
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.balance
     *
     * @return the value of account.balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Long getBalance() {
        return balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.balance
     *
     * @param balance the value for account.balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setBalance(Long balance) {
        this.balance = balance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.freeze_amount
     *
     * @return the value of account.freeze_amount
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Long getFreezeAmount() {
        return freezeAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.freeze_amount
     *
     * @param freezeAmount the value for account.freeze_amount
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setFreezeAmount(Long freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.create_time
     *
     * @return the value of account.create_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.create_time
     *
     * @param createTime the value for account.create_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.update_time
     *
     * @return the value of account.update_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.update_time
     *
     * @param updateTime the value for account.update_time
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column account.dollar_balance
     *
     * @return the value of account.dollar_balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public Long getDollarBalance() {
        return dollarBalance;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column account.dollar_balance
     *
     * @param dollarBalance the value for account.dollar_balance
     *
     * @mbggenerated Wed Dec 23 21:26:48 CST 2020
     */
    public void setDollarBalance(Long dollarBalance) {
        this.dollarBalance = dollarBalance;
    }
}
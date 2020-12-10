package com.order.mapper;

import java.util.Date;

public class Order {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.create_time
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.number
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.status
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.product_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private String productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.total_amount
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private Long totalAmount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.count
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private Integer count;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column order.user_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    private String userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.id
     *
     * @return the value of order.id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.id
     *
     * @param id the value for order.id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.create_time
     *
     * @return the value of order.create_time
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.create_time
     *
     * @param createTime the value for order.create_time
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.number
     *
     * @return the value of order.number
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.number
     *
     * @param number the value for order.number
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.status
     *
     * @return the value of order.status
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.status
     *
     * @param status the value for order.status
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.product_id
     *
     * @return the value of order.product_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public String getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.product_id
     *
     * @param productId the value for order.product_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.total_amount
     *
     * @return the value of order.total_amount
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public Long getTotalAmount() {
        return totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.total_amount
     *
     * @param totalAmount the value for order.total_amount
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.count
     *
     * @return the value of order.count
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public Integer getCount() {
        return count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.count
     *
     * @param count the value for order.count
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column order.user_id
     *
     * @return the value of order.user_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column order.user_id
     *
     * @param userId the value for order.user_id
     *
     * @mbggenerated Thu Dec 10 22:38:56 CST 2020
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
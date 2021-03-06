package com.stock.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int countByExample(StockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int deleteByExample(StockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int insert(Stock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int insertSelective(Stock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    List<Stock> selectByExample(StockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    Stock selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int updateByExampleSelective(@Param("record") Stock record, @Param("example") StockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int updateByExample(@Param("record") Stock record, @Param("example") StockExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int updateByPrimaryKeySelective(Stock record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table stock
     *
     * @mbggenerated Thu Dec 10 22:40:16 CST 2020
     */
    int updateByPrimaryKey(Stock record);

    int deleteStock(@Param("productId") String productId, int stockCount);

    int confirm(@Param("productId") String productId, int stockCount);

    int cancel(@Param("productId") String productId, int stockCount);
}
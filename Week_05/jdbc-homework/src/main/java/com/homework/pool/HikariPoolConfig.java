package com.homework.pool;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @Author Redick
 * @Date 2020/11/15 11:40 下午
 */
public class HikariPoolConfig {

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "root";

    /**
     * 增，改，删
     */
    private static final int INSERT = 1;
    private static final int UPDATE = 2;
    private static final int DELETE = 3;

    public Connection getConnection() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setReadOnly(false);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setMaxLifetime(1800000);
        dataSource.setMaximumPoolSize(10);
        return dataSource.getConnection();
    }

    /**
     * 简单的查询
     * Statement与preparedStatement主要区别是preparedStatement已经预编译过，执行速度更快
     */
    public void select() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            // 创建Statement对象
            statement = connection.prepareStatement("select id, `name` from user;");
            // 执行sql，获取结果集
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("数据为：id=" + id + "，" + "name=" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭结果集
                if (null != resultSet) {
                    resultSet.close();
                }
                // 关闭操作
                if (null != statement) {
                    statement.close();
                }
                // 关闭连接
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
    /**
     * 简单的增加
     */
    public void cud(int executeFlag) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();

            String sql = "";
            switch (executeFlag) {
                case INSERT:
                    sql = "insert into user (`name`) value ('哈哈');";
                    break;
                case UPDATE:
                    sql = "update user set `name`='嘻嘻' where id=1;";
                    break;
                case DELETE:
                    sql = "delete from user where id = 1;";
                    break;
                default:
            }

            // 执行sql
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭操作
                if (null != statement) {
                    statement.close();
                }
                // 关闭连接
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    /**
     * 事务操作
     */
    public void transaction() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            String sql1 = "select id from user where `name`='嘻嘻';";
            statement = connection.prepareStatement(sql1);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String sql2 = "update user set `name`='哈哈' where id=" + id + ";";
                connection.prepareStatement(sql2).execute();
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // 关闭操作
                if (null != statement) {
                    statement.close();
                }
                // 关闭连接
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public void insertValues(int num) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = "insert into tb_order (`order_no`, `user_id`, `refund_status`, `business_date`, `trans_amount`, `payment_amount`, `next_process_count`, `sharding_id`) values";
            StringBuffer values = new StringBuffer();
            int count = 0;
            while (count < num) {
                String orderNo = UUID.randomUUID().toString() + count;
                int userId = count;
                int shardingId = count % 10;
                String value = "";
                if (count == num - 1) {
                    value = "(" + "'" + orderNo + "'," + "'" + userId + "'," + "'" + "00" + "'," + "'" + "20201129" + "'," + "'" + count + "'," + "'" + count + "'," + "'" + 0 + "'," + "'" + shardingId + "');";
                } else {
                    value = "(" + "'" + orderNo + "'," + "'" + userId + "'," + "'" + "00" + "'," + "'" + "20201129" + "'," + "'" + count + "'," + "'" + count + "'," + "'" + 0 + "'," + "'" + shardingId + "'),";
                }
                count++;
                values.append(value);
            }
            sql = sql + values;
            System.out.println(sql);
            long start = System.currentTimeMillis();
            // 执行sql
            statement = connection.prepareStatement(sql);
            statement.execute();
            System.out.println("执行时间：" + String.valueOf(System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭操作
                if (null != statement) {
                    statement.close();
                }
                // 关闭连接
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        HikariPoolConfig config = new HikariPoolConfig();
        //config.cud(2);
        config.insertValues(1000000);
    }
}

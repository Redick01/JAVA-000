package com.homework.jdbc;

import java.sql.*;

/**
 * @Author Redick
 * @Date 2020/11/15 10:39 下午
 */
public class MysqlDBConfig {

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

    public static void main(String[] args) {
        MysqlDBConfig mysqlDBConfig = new MysqlDBConfig();
        mysqlDBConfig.transaction();
    }

    /**
     * 加载驱动，创建连接
     * @return
     */
    private Connection getConnection() {
        Connection connection = null;
        try {
            // 加载驱动
            Class.forName(DRIVER);
            // 创建连接
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
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
            statement = connection.prepareStatement("select id, name from user;");

            // 执行sql，获取结果集
            resultSet = statement.executeQuery();
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

            String sql1 = "select id, `name` from user where `name`='哈哈';";
            statement = connection.prepareStatement(sql1);
            resultSet = statement.executeQuery();
            int id = 0;
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("数据为：id=" + id + "，" + "name=" + name);
            }

            String sql2 = "update user set `name`='嘻嘻' where id=" + id + ";";
            connection.prepareStatement(sql2).execute();

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
}

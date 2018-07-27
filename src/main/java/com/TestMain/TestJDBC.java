package com.TestMain;

import java.sql.*;

/**
 * Created by panghaichen on 2018-07-27 11:27
 **/
public class TestJDBC {


    //本代码参考链接 ： http://www.cnblogs.com/hongten/archive/2011/03/29/1998311.html
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";
        String dbName = "test";  //本地数据库的库名
        String passwrod = "admin";  //自己安装数据库时配置的密码
        String userName = "root";   //一般默认
        String url = "jdbc:mysql://localhost:3306/" + dbName;  //本地mysql连接一般默认
        String sql = "select * from test"; //具体sql

        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, userName,
                    passwrod);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("id : " + rs.getInt(1) + " name : "
                        + rs.getString(2) );
            }

            // 关闭记录集
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // 关闭声明
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            // 关闭链接对象
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

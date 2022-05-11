package Proxy_;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.gjt.mm.mysql.Driver;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

/**
 * @Author qinsicheng
 * @Description 内容：代理模式
 * @Date 07/05/2022 11:21
 */
public class Proxy_ {
    public static void main(String[] args) throws SQLException {
        UserManger userManger = new UserManger();
        Collection<UserModel> userByDepID = userManger.getUserByDepID("010101");
        for (UserModel userModel : userByDepID) {
            System.out.println(userModel);
        }
    }

    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    @Test
    public void tst1() throws ClassNotFoundException, SQLException {
        //可以省略  但保险起见还是加上
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/design";
        String user = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("第 4 种方式~ " + connection);
    }

    @Test
    public void druid() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "root");
        map.put("password", "123456");
        map.put("url", "jdbc:mysql://localhost:3306/design");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(map);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 500000; i++) {
            Connection connection = dataSource.getConnection();

//            System.out.println(connection.getClass());
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("druid 连接池 操作 500000 耗时=" + (end - start));
    }


}

class UserModel {
    private String userID;
    private String name;
    private String depID;
    private String sex;

    @Override
    public String toString() {
        return "UserModel{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", depID='" + depID + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepID() {
        return depID;
    }

    public void setDepID(String depID) {
        this.depID = depID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

class UserManger {
    //根据部门编号获取该部门下所有员工
    public Collection<UserModel> getUserByDepID(String depID) throws SQLException {
        List<UserModel> list = new ArrayList<UserModel>();
        Connection conn = null;
        try {
            conn = this.getConnection();
            String sql = "SELECT * FROM tbl_user u,tbl_dep d WHERE u.depID=d.depiD";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserModel um = new UserModel();
                um.setUserID(rs.getString("userID"));
                um.setName(rs.getString("iname"));
                um.setDepID(rs.getString("depID"));
                um.setSex(rs.getString("sex"));
                list.add(um);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            conn.close();
        }
        return list;
    }

    private java.sql.Connection getConnection() throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/design";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "123456");
        java.sql.Connection connect = driver.connect(url, properties);
        return connect;
    }
}

class JDBCUtilsByDruid {
    private static DataSource ds;

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //关闭连接, 老师再次强调： 在数据库连接池技术中，close 不是真的断掉连接
    // 而是把使用的 Connection 对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
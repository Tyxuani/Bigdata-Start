package galaxy.hive;

import java.sql.*;

public class HiveJdbcClient {
    //hiverserver 版本使用此驱动Technorati 标记: hadoop,hive,jdbc
    //private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";
    /*hiverserver2 版本使用此驱动*/
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /*hiverserver 版本jdbc url格式*/
        //Connection con = DriverManager.getConnection("jdbc:hive://hostip:10000/default", "", "");

        /*hiverserver2 版本jdbc url格式*/
        Connection con = DriverManager.getConnection("jdbc:hive2://192.8.0.11:10000/default", "root", null);
        Statement stmt = con.createStatement();
        //参数设置测试
        //boolean resHivePropertyTest = stmt
        //        .execute("SET tez.runtime.io.sort.mb = 128");

//        boolean resHivePropertyTest = stmt
//                .execute("set hive.execution.engine=tez");
//        System.out.println(resHivePropertyTest);

        String tableName = "t_1";
//        stmt.executeQuery("drop table " + tableName);
//        ResultSet res;
//        Boolean ok = stmt.execute("create table t_j(id int,name String,add String) row format delimited fields terminated by ','");
//        System.out.println(ok);

//        //show tables
//        String sql = "show tables '" + tableName + "'";
        String sql = "show tables";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {

            System.out.println(res.getString(1));
        }

        //describe table
        sql = "describe t_j";
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }
//
//        // load data into table
//        // NOTE: filepath has to be local to the hive server
//        // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
//        String filepath = "/tmp/a.txt";
//        sql = "load data local inpath '" + filepath + "' into table " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//
        // select * query
        sql = "select * from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        System.out.println(res.getMetaData().getColumnCount());
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2) + "\t" + res.getString(3));
        }
//
//        // regular hive query
//        sql = "select count(1) from " + tableName;
//        System.out.println("Running: " + sql);
//        res = stmt.executeQuery(sql);
//        while (res.next()) {
//            System.out.println(res.getString(1));
//        }

    }
}

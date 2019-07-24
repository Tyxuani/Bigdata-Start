package galaxy.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.exceptions.DeserializationException;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Iterator;

public class HbaseClinet {

    private static Connection connect;
    private static Admin admin;
    private static Table table;

    public static void main(String[] args) throws IOException, DeserializationException {
        action();
    }

    private static void action() throws IOException, DeserializationException {
        init();

        scanData();

//        admin.close();
        table.close();
        connect.close();
    }

    private static void init() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.8.0.10:2181,192.8.0.12:2181,192.8.0.13:2181");
        connect = ConnectionFactory.createConnection(conf);
        admin = connect.getAdmin();
    }

    private static void create() throws IOException {

        HTableDescriptor t_house = new HTableDescriptor(TableName.valueOf("t_house"));
        HColumnDescriptor f1 = new HColumnDescriptor("f1");
        t_house.addFamily(f1);
        admin.createTable(t_house);
    }

    private static void delete() throws IOException {
        admin.disableTable(TableName.valueOf("t_house"));
        admin.deleteTable(TableName.valueOf("t_house"));
    }

    private static void modify() throws IOException {
        HTableDescriptor t_uer = admin.getTableDescriptor(TableName.valueOf("t_uer"));
        HColumnDescriptor base_user = new HColumnDescriptor("base_user");
        base_user.setBlocksize(131072);
        t_uer.addFamily(base_user);
        admin.modifyTable(TableName.valueOf("t_uer"), t_uer);
    }

    //数据插入相同key时,即可修改数据
    private static void putData() throws IOException {
        table = connect.getTable(TableName.valueOf("t_uer"));
        Put put = new Put("rk001".getBytes());
        put.addColumn("f222".getBytes(), ("n" +
                "ame").getBytes(), "lwang".getBytes("UTF-8"));
        put.addColumn(Bytes.toBytes("f222"), Bytes.toBytes("age"), "24".getBytes());
        table.put(put);
    }

    private static void deleteData() throws IOException {
        table = connect.getTable(TableName.valueOf("t_uer"));
        Delete del = new Delete("rk001".getBytes());
        del.addColumn("base_user".getBytes(), ("n" +
                "ame").getBytes());
        del.addColumn(Bytes.toBytes("base_user"), Bytes.toBytes("age"));
        table.delete(del);
    }

    private static void getData() throws IOException {
        table = connect.getTable(TableName.valueOf("t_uer"));
        Get get = new Get("rk001".getBytes());
        Result result = table.get(get);

//        取单个数据
//        byte[] value = result.getValue("f222".getBytes(), "name".getBytes());
//        System.out.println(Bytes.toString(value));

//        取一行数据
        printResult(result);
    }

    private static void printResult(Result result) throws IOException {
        CellScanner cellScanner = result.cellScanner();
        while (cellScanner.advance()) {
            Cell current = cellScanner.current();
            String row = Bytes.toString(current.getRowArray(), current.getRowOffset(), current.getRowLength());
            String family = Bytes.toString(current.getFamilyArray(), current.getFamilyOffset(), current.getFamilyLength());
            String qualifier = Bytes.toString(current.getQualifierArray(), current.getQualifierOffset(), current.getQualifierLength());
            String value = Bytes.toString(current.getValueArray(), current.getValueOffset(), current.getValueLength());

            System.out.println("row= " + row + ", column= " + family + ", qualifier= " + qualifier +
                    ", value= " + value);
        }
    }

    private static void scanData() throws IOException, DeserializationException {
        table = connect.getTable(TableName.valueOf("t_uer"));

//        [rk001, rk005)
//        Scan scan = new Scan("rk001".getBytes(), "rk005".getBytes());

//        \000为ASSIC十进制0
//        Scan scan = new Scan("rk001".getBytes(), ("rk005" + "\000").getBytes());
//        CompareFilter.CompareOp.GREATER_OR_EQUAL, BinaryComparator.parseFrom("rk002".getBytes())
        Scan scan = new Scan("rk001".getBytes(), new RowFilter(CompareFilter.CompareOp.GREATER, new BinaryComparator("rk002".getBytes())));
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> results = scanner.iterator();
        while (results.hasNext()){
            Result result = results.next();
            printResult(result);
        }

        scanner.close();
    }


}

package galaxy.zk;

import com.google.gson.Gson;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZkConnect {
    static ZooKeeper zk;


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        init();

        codeBlock();

        zk.close();
    }

    private static void codeBlock() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        watch();
        TimeUnit.SECONDS.sleep(1000);
    }

    private static void init() throws IOException {
        zk = new ZooKeeper("192.8.0.10:2181,192.8.0.12:2181,192.8.0.13:2181", 2000, null);
    }

    private static void getData() throws KeeperException, InterruptedException {
        byte[] data = zk.getData("/aa", false, null);
        System.out.println(new String(data));
    }

    private static void createNode() throws UnsupportedEncodingException, KeeperException, InterruptedException {
        //开放权限的临时节点
        String eph = zk.create("/aa/lu", "wenti".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("eph: " + eph);
        TimeUnit.SECONDS.sleep(5);
        //永久节点
        String per = zk.create("/aa/lu", "wenti".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        System.out.println("per: " + per);

    }

    private static void delete() throws KeeperException, InterruptedException {
        //-1表示该数据所有版本
        zk.delete("/aa/lu0000000002", -1);
    }

    private static void setData() throws UnsupportedEncodingException, KeeperException, InterruptedException {
        Stat stat = zk.setData("/aa/lu0000000001", "吃饭了吗?".getBytes(StandardCharsets.UTF_8), -1);
        System.out.println(stat.toString());
    }

    private static void exist() throws KeeperException, InterruptedException {
        Stat stat = zk.exists("/aa/lu0000000006", false);
        System.out.println(stat != null ? "存在" : "不存在");
    }

    private static void getChildren() throws KeeperException, InterruptedException {
        List<String> childrens = zk.getChildren("/aa", false);
        for (String child : childrens) {
            System.out.println(child);
        }
    }

    private static void putObject() throws KeeperException, InterruptedException {
        Person p = new Person(45, "zhangli");
        Gson gson = new Gson();
        String json = gson.toJson(p);
        String create = zk.create("/zhangli", json.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        byte[] data = zk.getData("/zhangli", false, null);
        Person person = gson.fromJson(new String(data), Person.class);
        System.out.println(person.toString());
    }

    private static void watch() throws KeeperException, InterruptedException {
        zk.getData("/aa", event -> {
            System.out.println(event.toString());
            try {
                watch();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, null);

    }

}

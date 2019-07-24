package galaxy.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PayServiceProvider {

    private ZooKeeper zk;

    public void connectZk() throws IOException {
        zk = new ZooKeeper("192.8.0.10:2181,192.8.0.12:2181,192.8.0.13:2181", 2000, null);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        PayServiceProvider pservice = new PayServiceProvider();
        pservice.connectZk();
        if (pservice.zk.exists("/server", false) == null) {
            pservice.zk.create("/server", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        pservice.zk.create("/server/pay", "192.8.0.100:1050".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        pservice.handleService();
        pservice.zk.close();
    }


    public void handleService() {
        System.out.println("start Service ...");
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

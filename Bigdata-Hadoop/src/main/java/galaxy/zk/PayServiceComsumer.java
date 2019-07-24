package galaxy.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PayServiceComsumer {
    private static ZooKeeper zk;
    private volatile static List<String> services = new ArrayList<>();

    private static void conncte() throws IOException {
        zk = new ZooKeeper("192.8.0.10:2181,192.8.0.12:2181,192.8.0.13:2181", 2000, (Watcher) event -> {
            if (event.getState() == KeeperState.SyncConnected && event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                System.out.println("可用服务列表发生变动");
                try {
                    getOnlineService();
                    services.forEach(server -> System.out.println(server));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void getOnlineService() throws KeeperException, InterruptedException {
        //获取子节点信息并用true注册监听
        List<String> children = zk.getChildren("/server", true);
        if (services != null)
            services.clear();
        for (String child : children) {
            services.add(new String(zk.getData("/server/" + child, false, null)));
        }
    }

    private static void handleWork() {
        int n = ThreadLocalRandom.current().nextInt(services.size());
        System.out.println("业务处理时选择了服务器: " + services.get(n));
    }


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        conncte();
        getOnlineService();
        TimeUnit.SECONDS.sleep(3000);
        zk.close();
    }
}

package galaxy.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.client.api.YarnClient;
import org.apache.hadoop.yarn.exceptions.YarnException;

import java.io.IOException;

public class YarnClienSd {

    public static YarnClient client;

    public static void main(String[] args) throws IOException, YarnException {

        Configuration conf = new Configuration();
        conf.addResource("yarn/core-site.xml");
        conf.addResource("yarn/hdfs-site.xml");
        conf.addResource("yarn/yarn-site.xml");

        client = YarnClient.createYarnClient();
        client.init(conf);
        client.start();
        System.out.println("*****");
        client.getNodeReports().stream().forEach(System.out::println);
        client.getAllQueues().stream().forEach(System.out::println);
        System.out.println(client.getYarnClusterMetrics());
    }
}

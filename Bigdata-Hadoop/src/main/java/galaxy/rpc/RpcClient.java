package galaxy.rpc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class RpcClient {
    public static void main(String[] args) throws IOException {
        LoginServiceProtocal proxy = RPC.getProxy(LoginServiceProtocal.class, 12L, new InetSocketAddress("localhost", 10000), new Configuration());
        System.out.println(proxy.login("kwx", "556"));
    }
}

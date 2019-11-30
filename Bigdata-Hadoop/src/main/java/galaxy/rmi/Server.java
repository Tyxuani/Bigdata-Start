package galaxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    private static String host = "127.0.0.1";
    private static int port = 6600;

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        if(args.length == 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        LoginServer loginServer = new LoginServerImpl(port);
        LocateRegistry.createRegistry(port);
        Naming.rebind("rmi://" + host + ":" + port + "/LoginServer", loginServer);
        System.out.println("Service Start!");
    }
}

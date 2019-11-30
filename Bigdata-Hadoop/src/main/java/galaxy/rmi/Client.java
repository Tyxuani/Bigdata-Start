package galaxy.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        LoginServer loginServer = (LoginServer) Naming.lookup("rmi://192.8.0.14:6600/LoginServer");
        String login = loginServer.login(new
                UserInfo(25, "蔡徐坤", "beijing"));
        System.out.println(login);
    }
}

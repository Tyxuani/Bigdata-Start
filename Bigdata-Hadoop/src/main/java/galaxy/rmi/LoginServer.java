package galaxy.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginServer extends Remote {
    String login(UserInfo info) throws RemoteException;
}

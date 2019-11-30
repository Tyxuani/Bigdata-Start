package galaxy.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginServerImpl extends UnicastRemoteObject implements LoginServer {

    protected LoginServerImpl(int port) throws RemoteException {
        super(port);
    }

    @Override
    public String login(UserInfo info) throws RemoteException {
        return info.getName() + " login to RMI server success";
    }
}

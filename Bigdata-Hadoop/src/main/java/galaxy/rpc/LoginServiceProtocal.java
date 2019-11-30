package galaxy.rpc;

public interface LoginServiceProtocal {
    long versionID = 12;
    String login(String username, String password);
}

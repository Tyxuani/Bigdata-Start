package galaxy.rpc;

public interface LoginServiceProtocal {
    final long versionID = 12;
    String login(String username, String password);
}

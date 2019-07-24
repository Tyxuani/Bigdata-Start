package galaxy.rpc;

public class LoginServiceImpl implements LoginServiceProtocal{
    @Override
    public String login(String username, String password){
        return "success";
    }
}

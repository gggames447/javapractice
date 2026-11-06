package practicalpractice2;

// 管理员账号，目前账号直接写在 LoginService 里
public class AdminAccount {
    private final String name;
    private final String password;

    public AdminAccount(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

package practicalpractice2;

public class AdminAccount {
    private String name;
    private String password;

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


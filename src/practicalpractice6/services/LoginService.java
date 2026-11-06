package practicalpractice6.services;

import practicalpractice6.account.AdminAccount;

import java.util.Scanner;

/**
 * 登录验证
 */
public class LoginService {
    private static final int MAX_ATTEMPTS = 3;
    private static final AdminAccount[] ACCOUNTS = {
            new AdminAccount("admin", "123456"),
            new AdminAccount("hys", "123456")
    };

    // 工具类，只提供静态方法，不需要创建对象
    private LoginService() {}

    /**
     * 登录验证，最多可以输错 3 次
     *
     * @param scanner 键盘输入
     * @return 登录成功返回用户名，输错 3 次返回 null
     */
    public static String login(Scanner scanner) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            System.out.print("请输入管理员账号：");
            String username = scanner.nextLine().trim();
            System.out.print("请输入密码：");
            String password = scanner.nextLine().trim();

            if (isValid(username, password)) {
                System.out.println("登录成功，正在进入系统...");
                return username;
            }
            System.out.println("账号或密码错误！您还有 " + (MAX_ATTEMPTS - attempt) + " 次机会。");
        }
        System.out.println("错误次数过多，系统已退出");
        return null;
    }

    /**
     * @param username 账号
     * @param password 密码
     * @return 和任意一个管理员账号匹配时返回 true
     */
    private static boolean isValid(String username, String password) {
        for (AdminAccount account : ACCOUNTS) {
            if (account.getName().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}

package practicalpractice3.services;

import practicalpractice3.AdminAccount;

import java.util.Scanner;

// 登录验证
public class LoginService {

    // 登录成功返回用户名，输错 3 次返回 null
    public static String login(Scanner scanner) {
        AdminAccount[] accounts = {
                new AdminAccount("admin", "123456"),
                new AdminAccount("hys", "123456")
        };

        int loginFailedTimes = 0;

        while (loginFailedTimes < 3) {
            System.out.print("请输入管理员账号：");
            String username = scanner.nextLine().trim();
            System.out.print("请输入密码：");
            String password = scanner.nextLine().trim();

            for (AdminAccount account : accounts) {
                if (account.getName().equals(username) && account.getPassword().equals(password)) {
                    System.out.println("登录成功，正在进入系统...");
                    return username;
                }
            }
            loginFailedTimes++;
            System.out.println("账号或密码错误！您还有 " + (3 - loginFailedTimes) + " 次机会。");

            System.out.println("");
        }    return null;
    }
}
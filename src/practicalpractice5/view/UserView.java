package practicalpractice5.view;

import practicalpractice5.account.UserAccount;
import practicalpractice5.utils.menu.MenuUtil;

/**
 * 显示用户表格，不显示密码。UserHandler 只负责查数据，打印都在这里。
 */
public class UserView {

    // 工具类，只提供静态方法，不需要创建对象
    private UserView() {}

    /**
     * 打印用户表格
     *
     * @param users 要显示的用户
     * @param emptyMessage 没有用户时显示的提示
     */
    public static void printTable(UserAccount[] users, String emptyMessage) {
        if (users.length == 0) {
            System.out.println(emptyMessage);
            return;
        }
        MenuUtil.printLine();
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
        MenuUtil.printLine();
        for (UserAccount user : users) {
            System.out.printf("%-12s %-14s %s%n", user.getUserName(), user.getPhone(), user.getUserId());
        }
    }
}

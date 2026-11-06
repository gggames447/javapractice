package practicalpractice7.view;

import practicalpractice7.entity.UserAccount;

/**
 * 显示用户表格，不显示密码
 */
public class UserView extends TableView<UserAccount> {

    @Override
    protected void printColumnNames() {
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
    }

    @Override
    protected void printRow(UserAccount user) {
        System.out.printf("%-12s %-14s %s%n", user.getUserName(), user.getPhone(), user.getUserId());
    }
}

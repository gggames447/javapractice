package practicalpractice5.handler;

import practicalpractice5.account.UserAccount;
import practicalpractice5.utils.menu.MenuUtil;

/**
 * 管理普通用户，用数组存储，最多 20 个
 */
public class UserHandler {
    private static final int MAX_SIZE = 20;

    // 有效用户的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final UserAccount[] userAccounts = new UserAccount[MAX_SIZE];

    /**
     * 新增用户，容量已满或用户名重复时拒绝保存。
     * 手机号、密码等格式已经在 UserAccount 的 setter 里校验过。
     *
     * @param newUser 要新增的用户
     */
    public void addUser(UserAccount newUser) {
        if (count == userAccounts.length) {
            System.out.println("用户存储已满，无法新增！");
            return;
        }
        if (findByName(newUser.getUserName()) != null) {
            System.out.println("用户名已存在，新增失败！");
            return;
        }
        userAccounts[count] = newUser;
        count++;
        System.out.println("用户添加成功！");
    }

    /**
     * 查看全部用户，没有用户时提示暂无用户
     */
    public void queryAllUsers() {
        if (count == 0) {
            System.out.println("暂无用户。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            printUser(userAccounts[i]);
        }
    }

    /**
     * 按用户名查找用户
     *
     * @param userName 用户名
     * @return 找到返回该用户，找不到返回 null
     */
    public UserAccount findByName(String userName) {
        for (int i = 0; i < count; i++) {
            if (userAccounts[i].getUserName().equals(userName)) {
                return userAccounts[i];
            }
        }
        return null;
    }

    /**
     * 按用户 ID 查找用户，显示地址时用来查出用户名
     *
     * @param userId 用户 ID
     * @return 找到返回该用户，找不到返回 null
     */
    public UserAccount findById(String userId) {
        for (int i = 0; i < count; i++) {
            if (userAccounts[i].getUserId().equals(userId)) {
                return userAccounts[i];
            }
        }
        return null;
    }

    private void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
        MenuUtil.printLine();
    }

    // 不显示密码
    private void printUser(UserAccount user) {
        System.out.printf("%-12s %-14s %s%n", user.getUserName(), user.getPhone(), user.getUserId());
    }
}

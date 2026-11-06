package practicalpractice5.handler;

import practicalpractice5.account.UserAccount;

/**
 * 管理普通用户，用数组存储，最多 20 个
 */
public class UserHandler {
    private int count = 0;
    private static final  int MAX_SIZE = 20;
    private UserAccount[] userAccounts = new UserAccount[MAX_SIZE];

    /**
     * 打印用户表格的表头
     */
    private void printHeader() {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * 按表格格式打印一个用户，不显示密码
     *
     * @param user 要打印的用户
     */
    private void printUser(UserAccount user) {
        System.out.printf("%-12s %-14s %s%n", user.getUserName(), user.getPhone(), user.getUserId());
    }

    /**
     * 新增用户，容量已满或用户名重复时拒绝保存。
     * 手机号、密码等格式已经在 UserAccount 的 setter 里校验过。
     *
     * @param newUser 要新增的用户
     */
    public void addUser(UserAccount newUser){
        if (count == userAccounts.length) {
            System.out.println("用户已满，无法新增！");
            return;
        }
        for (int i = 0; i < count; i++) {
            if(newUser.getUserName().equals(userAccounts[i].getUserName())){
                System.out.println("用户名不能重复");
                return;
            }
        }
        userAccounts[count] = newUser;
        count++;
        System.out.println("用户添加成功");
    }

    /**
     * 查看全部用户，没有用户时提示暂无用户
     */
    public void queryAllUsers() {
        if (count == 0) {
            System.out.println("暂无用户");
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
}

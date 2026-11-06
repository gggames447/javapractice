package practicalpractice7.handler;

import practicalpractice7.entity.UserAccount;

/**
 * 管理普通用户，用数组存储，最多 20 个
 */
public class UserHandler extends BaseHandler<UserAccount> {


    public UserHandler() {
        super(new UserAccount[MAX_SIZE]);
    }

    /**
     * 打印用户表格的表头
     */
    @Override
    protected void printHeader() {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * 打印一行数据，由子类决定格式
     *
     * @param userAccount 要打印的数据
     */
    @Override
    protected void printItem(UserAccount userAccount) {
        System.out.printf("%-12s %-14s %s%n", userAccount.getUserName(), userAccount.getPhone(), userAccount.getUserId());
    }
    @Override
    protected boolean checkBeforeAdd(UserAccount item) {
        if (findByName(item.getUserName()) != null) {
            System.out.println("用户名不能重复");
            return false;
        }
        return true;
    }

    /**
     * 按用户名查找用户
     *
     * @param userName 用户名
     * @return 找到返回该用户，找不到返回 null
     */
    public UserAccount findByName(String userName) {
        for (int i = 0; i < count; i++) {
            if (array[i].getUserName().equals(userName)) {
                return array[i];
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
        int index = findIndexById(userId);
        return (index == -1) ? null : array[index];
    }
}

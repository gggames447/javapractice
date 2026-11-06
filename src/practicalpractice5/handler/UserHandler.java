package practicalpractice5.handler;

import practicalpractice5.account.UserAccount;
import practicalpractice5.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 管理普通用户，用数组存储，最多 20 个。只管数据，不打印，显示交给 UserView。
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
     * @throws BusinessException 存储已满或用户名已存在时
     */
    public void addUser(UserAccount newUser) {
        if (count == userAccounts.length) {
            throw new BusinessException("用户存储已满，无法新增");
        }
        if (findByName(newUser.getUserName()) != null) {
            throw new BusinessException("用户名「" + newUser.getUserName() + "」已存在");
        }
        userAccounts[count] = newUser;
        count++;
    }

    /**
     * @return 全部用户的副本，没有用户时长度为 0
     */
    public UserAccount[] findAll() {
        return Arrays.copyOf(userAccounts, count);
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

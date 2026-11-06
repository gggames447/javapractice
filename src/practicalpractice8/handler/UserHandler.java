package practicalpractice8.handler;

import practicalpractice8.entity.UserAccount;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.menu.MenuUtil;

/**
 * 管理普通用户
 */
public class UserHandler extends BaseHandler<UserAccount> {

    public UserHandler() {
        super(new UserAccount[MAX_SIZE]);
    }

    /**
     * 新增用户时，用户名不能重复，因为下单、加地址都靠用户名找用户
     *
     * @param item 要新增的用户
     * @throws BusinessException 用户名已存在时
     */
    @Override
    protected void validateBeforeAdd(UserAccount item) {
        if (findByName(item.getUserName()) != null) {
            throw new BusinessException("用户名「" + item.getUserName() + "」已存在");
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
            if (array[i].getUserName().equals(userName)) {
                return array[i];
            }
        }
        return null;
    }

    /**
     * 按用户名查找用户，找不到时直接报错
     *
     * @param userName 用户名
     * @return 找到的用户
     * @throws BusinessException 用户不存在时
     */
    public UserAccount getByName(String userName) {
        UserAccount user = findByName(userName);
        if (user == null) {
            throw new BusinessException("用户「" + userName + "」不存在");
        }
        return user;
    }

    /**
     * 按用户 ID 查出用户名，用于打印地址、订单
     *
     * @param userId 用户 ID
     * @return 用户名；用户不存在时返回「未知用户」
     */
    public String getUserNameById(String userId) {
        UserAccount user = findById(userId);
        return (user == null) ? "未知用户" : user.getUserName();
    }

    @Override
    protected void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-12s %-14s %s%n", "用户名", "手机号", "用户ID");
        MenuUtil.printLine();
    }

    @Override
    protected void printItem(UserAccount user) {
        System.out.printf("%-12s %-14s %s%n", user.getUserName(), user.getPhone(), user.getUserId());
    }
}

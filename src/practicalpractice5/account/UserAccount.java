package practicalpractice5.account;

import practicalpractice5.utils.exception.InputException;

import java.util.UUID;

/**
 * 普通用户
 */
public class UserAccount {
    private final String userId;
    private String userName;
    private String phone;
    private String password;

    /**
     * 创建一个用户，自动生成 UUID 作为用户 ID
     *
     * @param userName 用户名，不能为空
     * @param phone 手机号，必须是 11 位数字
     * @param password 密码，不能为空
     * @throws InputException 任意一项不合法时
     */
    public UserAccount(String userName, String phone, String password) {
        this.userId = UUID.randomUUID().toString();
        setUserName(userName);
        setPhone(phone);
        setPassword(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName 用户名
     * @throws InputException 用户名为空时
     */
    public void setUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new InputException("用户名不能为空");
        }
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 手机号
     * @throws InputException 不是 11 位数字时
     */
    public void setPhone(String phone) {
        if (phone == null || !phone.matches("\\d{11}")) {
            throw new InputException("手机号必须是 11 位数字");
        }
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @param password 密码
     * @throws InputException 密码为空时
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new InputException("密码不能为空");
        }
        this.password = password;
    }
}

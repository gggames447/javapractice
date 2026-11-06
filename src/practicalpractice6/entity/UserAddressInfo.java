package practicalpractice6.entity;

import practicalpractice6.utils.exception.InputException;

import java.util.UUID;

/**
 * 收货地址，通过 userId 关联到所属用户
 */
public class UserAddressInfo extends BaseEntity{
    private final String userId;
    private String receiver;
    private String telephone;
    private String addressInfo;

    /**
     * 创建一个收货地址，自动生成 UUID 作为地址 ID
     *
     * @param userId 所属用户的 ID，由调用方先确认这个用户存在
     * @param receiver 收件人，不能为空
     * @param telephone 收件人电话，必须是 11 位数字
     * @param addressInfo 详细地址，不能为空
     * @throws InputException 任意一项不合法时
     */
    public UserAddressInfo(String userId, String receiver, String telephone, String addressInfo) {
        super();
        this.userId = userId;
        setReceiver(receiver);
        setTelephone(telephone);
        setAddressInfo(addressInfo);
    }

    public String getAddressId() {
        return getId();
    }

    public String getUserId() {
        return userId;
    }

    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver 收件人
     * @throws InputException 收件人为空时
     */
    public void setReceiver(String receiver) {
        if (receiver == null || receiver.trim().isEmpty()) {
            throw new InputException("收件人不能为空");
        }
        this.receiver = receiver;
    }

    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone 收件人电话
     * @throws InputException 不是 11 位数字时
     */
    public void setTelephone(String telephone) {
        if (telephone == null || !telephone.matches("\\d{11}")) {
            throw new InputException("电话必须是 11 位数字");
        }
        this.telephone = telephone;
    }

    public String getAddressInfo() {
        return addressInfo;
    }

    /**
     * @param addressInfo 详细地址
     * @throws InputException 地址为空时
     */
    public void setAddressInfo(String addressInfo) {
        if (addressInfo == null || addressInfo.trim().isEmpty()) {
            throw new InputException("详细地址不能为空");
        }
        this.addressInfo = addressInfo;
    }

    @Override
    public String toString() {
        return "UserAddressInfo{" +
                ", userId='" + userId + '\'' +
                ", receiver='" + receiver + '\'' +
                ", telephone='" + telephone + '\'' +
                ", addressInfo='" + addressInfo + '\'' +
                '}';
    }
}

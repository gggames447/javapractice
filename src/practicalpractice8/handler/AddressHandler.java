package practicalpractice8.handler;

import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.menu.MenuUtil;

import java.util.Arrays;

/**
 * 管理收货地址
 */
public class AddressHandler extends BaseHandler<UserAddressInfo> {
    private final UserHandler userHandler;

    /**
     * @param userHandler 用户数据，打印地址时用来根据 userId 查出用户名
     */
    public AddressHandler(UserHandler userHandler) {
        super(new UserAddressInfo[MAX_SIZE]);
        this.userHandler = userHandler;
    }

    /**
     * 新增地址时，同一个用户的详细地址不能重复（不同用户可以是同一个地址）
     *
     * @param info 要新增的地址
     * @throws BusinessException 该用户已有这个地址时
     */
    @Override
    protected void validateBeforeAdd(UserAddressInfo info) {
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(info.getUserId())
                    && array[i].getAddressInfo().equals(info.getAddressInfo())) {
                throw new BusinessException("该用户已有这个地址");
            }
        }
    }

    /**
     * 查出某个用户的全部地址，下单时让用户从中选择
     *
     * @param userId 用户 ID
     * @return 该用户的地址数组，一个都没有时长度为 0
     */
    public UserAddressInfo[] findByUser(String userId) {
        UserAddressInfo[] result = new UserAddressInfo[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(userId)) {
                result[found] = array[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    @Override
    protected void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-8s %-14s %s%n", "所属用户", "收件人", "电话", "详细地址");
        MenuUtil.printLine();
    }

    @Override
    protected void printItem(UserAddressInfo address) {
        System.out.printf("%-10s %-8s %-14s %s%n", userHandler.getUserNameById(address.getUserId()),
                address.getReceiver(), address.getTelephone(), address.getAddressInfo());
    }
}

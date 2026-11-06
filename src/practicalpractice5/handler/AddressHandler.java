package practicalpractice5.handler;

import practicalpractice5.account.UserAddressInfo;
import practicalpractice5.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 管理收货地址，用数组存储，最多 20 个。只管数据，不打印，显示交给 AddressView。
 */
public class AddressHandler {
    private static final int MAX_SIZE = 20;

    // 有效地址的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final UserAddressInfo[] addresses = new UserAddressInfo[MAX_SIZE];

    /**
     * 新增收货地址，容量已满时拒绝保存。
     * 所属用户是否存在由 AddressUI 在创建地址前检查。
     *
     * @param newAddress 要新增的地址
     * @throws BusinessException 存储已满时
     */
    public void addAddress(UserAddressInfo newAddress) {
        if (count == addresses.length) {
            throw new BusinessException("地址存储已满，无法新增");
        }
        addresses[count] = newAddress;
        count++;
    }

    /**
     * @return 全部地址的副本，没有地址时长度为 0
     */
    public UserAddressInfo[] findAll() {
        return Arrays.copyOf(addresses, count);
    }
}

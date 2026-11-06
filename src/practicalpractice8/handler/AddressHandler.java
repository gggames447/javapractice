package practicalpractice8.handler;

import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 管理收货地址
 */
public class AddressHandler extends BaseHandler<UserAddressInfo> {

    public AddressHandler() {
        super(new UserAddressInfo[MAX_SIZE]);
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
}

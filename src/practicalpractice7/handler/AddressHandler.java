package practicalpractice7.handler;

import practicalpractice7.entity.UserAddressInfo;
import practicalpractice7.utils.exception.BusinessException;

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

}

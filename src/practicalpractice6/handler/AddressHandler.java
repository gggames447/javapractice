package practicalpractice6.handler;

import practicalpractice6.entity.UserAccount;
import practicalpractice6.entity.UserAddressInfo;
import practicalpractice6.interfaces.DataOperate;

/**
 * 管理收货地址，用数组存储，最多 20 个
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
     * 打印地址表格的表头
     */
    @Override
    protected void printHeader() {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-10s %-8s %-14s %s%n", "所属用户", "收件人", "电话", "详细地址");
        System.out.println("------------------------------------------------------------------------");
    }

    @Override
    protected void printItem(UserAddressInfo address) {
        UserAccount user = userHandler.findById(address.getUserId());
        String userName = (user == null) ? "未知用户" : user.getUserName();
        System.out.printf("%-10s %-8s %-14s %s%n",
                userName, address.getReceiver(), address.getTelephone(), address.getAddressInfo());
    }

    /**
     * 新增地址时，除了父类检查的容量和编号，详细地址也不能重复
     *
     * @param info 要新增的地址
     * @return 不重复返回 true，否则返回 false
     */
    @Override
    protected boolean checkBeforeAdd(UserAddressInfo info) {
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(info.getUserId())
                    && array[i].getAddressInfo().equals(info.getAddressInfo())) {
                System.out.println("该用户地址INFO已存在,添加失败");
                return false;
            }
        }
        return true;
    }
}


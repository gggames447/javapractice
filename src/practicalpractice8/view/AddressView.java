package practicalpractice8.view;

import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.handler.UserHandler;

/**
 * 显示收货地址表格，所属用户显示为用户名
 */
public class AddressView extends TableView<UserAddressInfo> {
    private final UserHandler userHandler;

    /**
     * @param userHandler 用户数据，用来根据 userId 查出用户名
     */
    public AddressView(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    /**
     * 把一条地址写成一行文字，下单选地址、订单详情里都用这个格式
     *
     * @param address 地址
     * @return 例如「张三，13900000000，北京市海淀区」
     */
    public static String describe(UserAddressInfo address) {
        return address.getReceiver() + "，" + address.getTelephone() + "，" + address.getAddressInfo();
    }

    @Override
    protected void printColumnNames() {
        System.out.printf("%-10s %-8s %-14s %s%n", "所属用户", "收件人", "电话", "详细地址");
    }

    @Override
    protected void printRow(UserAddressInfo address) {
        System.out.printf("%-10s %-8s %-14s %s%n", userHandler.getUserNameById(address.getUserId()),
                address.getReceiver(), address.getTelephone(), address.getAddressInfo());
    }
}

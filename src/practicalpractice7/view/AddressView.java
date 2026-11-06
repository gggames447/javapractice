package practicalpractice7.view;

import practicalpractice7.entity.UserAddressInfo;
import practicalpractice7.handler.UserHandler;

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

package practicalpractice5.view;

import practicalpractice5.account.UserAccount;
import practicalpractice5.account.UserAddressInfo;
import practicalpractice5.handler.UserHandler;
import practicalpractice5.utils.menu.MenuUtil;

/**
 * 显示收货地址表格，所属用户显示为用户名。AddressHandler 只负责查数据，打印都在这里。
 */
public class AddressView {

    // 工具类，只提供静态方法，不需要创建对象
    private AddressView() {}

    /**
     * 打印地址表格
     *
     * @param addresses 要显示的地址
     * @param userHandler 用户数据，用来根据 userId 查出用户名
     * @param emptyMessage 没有地址时显示的提示
     */
    public static void printTable(UserAddressInfo[] addresses, UserHandler userHandler, String emptyMessage) {
        if (addresses.length == 0) {
            System.out.println(emptyMessage);
            return;
        }
        MenuUtil.printLine();
        System.out.printf("%-10s %-8s %-14s %s%n", "所属用户", "收件人", "电话", "详细地址");
        MenuUtil.printLine();
        for (UserAddressInfo address : addresses) {
            UserAccount user = userHandler.findById(address.getUserId());
            String userName = (user == null) ? "未知用户" : user.getUserName();
            System.out.printf("%-10s %-8s %-14s %s%n",
                    userName, address.getReceiver(), address.getTelephone(), address.getAddressInfo());
        }
    }
}

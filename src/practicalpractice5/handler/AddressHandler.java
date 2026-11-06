package practicalpractice5.handler;

import practicalpractice5.account.UserAccount;
import practicalpractice5.account.UserAddressInfo;
import practicalpractice5.utils.menu.MenuUtil;

/**
 * 管理收货地址，用数组存储，最多 20 个
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
     */
    public void addAddress(UserAddressInfo newAddress) {
        if (count == addresses.length) {
            System.out.println("地址存储已满，无法新增！");
            return;
        }
        addresses[count] = newAddress;
        count++;
        System.out.println("收货地址添加成功！");
    }

    /**
     * 查看全部收货地址
     *
     * @param userHandler 用户数据，用来显示每条地址属于哪个用户
     */
    public void queryAllUsersAddress(UserHandler userHandler) {
        if (count == 0) {
            System.out.println("暂无地址。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            printAddress(addresses[i], userHandler);
        }
    }

    private void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-8s %-14s %s%n", "所属用户", "收件人", "电话", "详细地址");
        MenuUtil.printLine();
    }

    /**
     * 按表格格式打印一条地址，所属用户显示为用户名
     *
     * @param address 要打印的地址
     * @param userHandler 用户数据，用来根据 userId 查出用户名
     */
    private void printAddress(UserAddressInfo address, UserHandler userHandler) {
        UserAccount user = userHandler.findById(address.getUserId());
        String userName = (user == null) ? "未知用户" : user.getUserName();
        System.out.printf("%-10s %-8s %-14s %s%n",
                userName, address.getReceiver(), address.getTelephone(), address.getAddressInfo());
    }
}

package practicalpractice5.controller;

import practicalpractice5.account.UserAccount;
import practicalpractice5.account.UserAddressInfo;
import practicalpractice5.handler.AddressHandler;
import practicalpractice5.handler.UserHandler;
import practicalpractice5.utils.input.InputUtil;
import practicalpractice5.utils.menu.BaseMenu;
import practicalpractice5.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 收货地址管理子菜单
 */
public class AddressUI extends BaseMenu {
    private final AddressHandler addressHandler;
    private final UserHandler userHandler;

    /**
     * @param scanner 键盘输入
     * @param addressHandler 地址数据
     * @param userHandler 用户数据，新增地址时用来查找所属用户
     */
    public AddressUI(Scanner scanner, AddressHandler addressHandler, UserHandler userHandler) {
        super(scanner);
        this.addressHandler = addressHandler;
        this.userHandler = userHandler;
    }

    @Override
    protected void printMenu() {
        MenuUtil.printAddressMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addAddress();
            case 2 -> addressHandler.queryAllUsersAddress(userHandler);
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 读取输入并新增收货地址，所属用户必须已经存在
     */
    private void addAddress() {
        System.out.println("--- 新增收货地址 ---");
        String userName = InputUtil.getString(scanner, "请输入所属用户名：");
        UserAccount user = userHandler.findByName(userName);
        if (user == null) {
            System.out.println("用户不存在，新增失败！");
            return;
        }

        String receiver = InputUtil.getString(scanner, "请输入收件人：");
        String telephone = InputUtil.getString(scanner, "请输入收件人电话：");
        String addressInfo = InputUtil.getString(scanner, "请输入详细地址：");

        addressHandler.addAddress(new UserAddressInfo(user.getUserId(), receiver, telephone, addressInfo));
    }
}
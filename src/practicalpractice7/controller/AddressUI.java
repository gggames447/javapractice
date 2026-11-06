package practicalpractice7.controller;

import practicalpractice7.entity.UserAccount;
import practicalpractice7.entity.UserAddressInfo;
import practicalpractice7.handler.AddressHandler;
import practicalpractice7.handler.UserHandler;
import practicalpractice7.utils.input.InputUtil;
import practicalpractice7.utils.menu.BaseMenu;
import practicalpractice7.utils.menu.MenuUtil;
import practicalpractice7.view.AddressView;

import java.util.Scanner;

/**
 * 收货地址管理子菜单
 */
public class AddressUI extends BaseMenu {
    private final AddressHandler addressHandler;
    private final UserHandler userHandler;
    private final AddressView addressView;

    /**
     * @param scanner 键盘输入
     * @param addressHandler 地址数据
     * @param userHandler 用户数据，新增地址时用来查找所属用户
     */
    public AddressUI(Scanner scanner, AddressHandler addressHandler, UserHandler userHandler) {
        super(scanner);
        this.addressHandler = addressHandler;
        this.userHandler = userHandler;
        this.addressView = new AddressView(userHandler);
    }

    @Override
    protected void printMenu() {
        MenuUtil.printAddressMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addAddress();
            case 2 -> addressView.printTable(addressHandler.findAll(), "暂无数据。");
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 读取输入并新增收货地址，所属用户必须已经存在
     */
    private void addAddress() {
        System.out.println("--- 新增收货地址 ---");
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入所属用户名："));
        String receiver = InputUtil.getString(scanner, "请输入收件人：");
        String telephone = InputUtil.getString(scanner, "请输入收件人电话：");
        String addressInfo = InputUtil.getString(scanner, "请输入详细地址：");
        addressHandler.add(new UserAddressInfo(user.getUserId(), receiver, telephone, addressInfo));
        System.out.println("收货地址添加成功！");
    }
}

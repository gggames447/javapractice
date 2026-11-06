package practicalpractice8.controller;

import practicalpractice8.entity.UserAccount;
import practicalpractice8.handler.UserHandler;
import practicalpractice8.utils.input.InputUtil;
import practicalpractice8.utils.menu.BaseMenu;
import practicalpractice8.utils.menu.MenuUtil;
import practicalpractice8.view.UserView;

import java.util.Scanner;

/**
 * 用户管理子菜单
 */
public class UserUI extends BaseMenu {
    private final UserHandler userHandler;
    private final UserView userView = new UserView();

    /**
     * @param scanner 键盘输入
     * @param userHandler 用户数据
     */
    public UserUI(Scanner scanner, UserHandler userHandler) {
        super(scanner);
        this.userHandler = userHandler;
    }

    @Override
    protected void printMenu() {
        MenuUtil.printUserMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addUser();
            case 2 -> userView.printTable(userHandler.findAll(), "暂无数据。");
            case 3 -> queryByName();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 读取输入并新增用户，用户 ID 自动生成
     */
    private void addUser() {
        System.out.println("--- 新增用户 ---");
        String userName = InputUtil.getString(scanner, "请输入用户名：");
        String phone = InputUtil.getString(scanner, "请输入手机号码：");
        String password = InputUtil.getString(scanner, "请输入密码：");
        userHandler.add(new UserAccount(userName, phone, password));
        System.out.println("用户添加成功！");
    }

    /**
     * 按用户名查找并打印用户信息，不显示密码
     */
    private void queryByName() {
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入要查找的用户名："));
        System.out.println("用户ID：" + user.getUserId());
        System.out.println("用户名：" + user.getUserName());
        System.out.println("手机号：" + user.getPhone());
    }
}

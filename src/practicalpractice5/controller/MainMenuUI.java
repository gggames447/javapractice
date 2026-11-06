package practicalpractice5.controller;

import practicalpractice5.handler.AddressHandler;
import practicalpractice5.handler.CategoryHandler;
import practicalpractice5.handler.GoodsHandler;
import practicalpractice5.handler.UserHandler;
import practicalpractice5.utils.menu.BaseMenu;
import practicalpractice5.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 主菜单，输入 0 退出系统
 */
public class MainMenuUI extends BaseMenu {
    private final String currentUser;
    private final CategoryHandler categoryHandler;
    private final GoodsHandler goodsHandler;
    private final UserHandler userHandler;
    private final AddressHandler addressHandler;

    /**
     * 创建主菜单，所有 Handler 都由 main 创建后传进来
     *
     * @param scanner 键盘输入
     * @param currentUser 当前登录的账号
     * @param categoryHandler 分类数据
     * @param goodsHandler 商品数据
     * @param userHandler 用户数据
     * @param addressHandler 收货地址数据
     */
    public MainMenuUI(Scanner scanner, String currentUser, CategoryHandler categoryHandler,
                      GoodsHandler goodsHandler, UserHandler userHandler, AddressHandler addressHandler) {
        super(scanner);
        this.currentUser = currentUser;
        this.categoryHandler = categoryHandler;
        this.goodsHandler = goodsHandler;
        this.userHandler = userHandler;
        this.addressHandler = addressHandler;
    }

    @Override
    protected void printMenu() {
        System.out.println("\n当前账号: " + currentUser);
        MenuUtil.printMainMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> new UserUI(scanner, userHandler).show();
            case 2 -> new CategoryUI(scanner, categoryHandler).show();
            case 3 -> new GoodsUI(scanner, goodsHandler, categoryHandler).show();
            case 4 -> new AddressUI(scanner, addressHandler, userHandler).show();
            case 5, 6 -> System.out.println("开发中");
            default -> System.out.println("无效编号！");
        }
    }

    @Override
    protected String exitMessage() {
        return "成功退出系统";
    }
}
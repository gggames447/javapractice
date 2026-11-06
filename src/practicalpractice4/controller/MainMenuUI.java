package practicalpractice4.controller;

import practicalpractice4.category.CategoryHandler;
import practicalpractice4.goods.GoodsHandler;
import practicalpractice4.utils.exception.InputException;
import practicalpractice4.utils.input.InputUtil;
import practicalpractice4.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 主菜单，1、4、5、6 还没做，先显示开发中
 */
public class MainMenuUI {

    /**
     * 显示主菜单，循环处理用户的选择，输入 0 退出系统
     *
     * @param scanner 键盘输入
     * @param currentUser 当前登录的账号
     * @param categoryHandler 分类数据
     * @param goodsHandler 商品数据
     */
    public static void showMenu(Scanner scanner, String currentUser,
                                CategoryHandler categoryHandler, GoodsHandler goodsHandler) {
        while (true) {
            System.out.println();
            System.out.println("当前账号：" + currentUser);
            MenuUtil.printMainMenu();
            try {
                int choice = InputUtil.getInt(scanner);
                switch (choice) {
                    case 2 -> CategoryUI.showMenu(scanner, categoryHandler);
                    case 3 -> GoodsUI.showMenu(scanner, goodsHandler, categoryHandler);
                    case 1, 4, 5, 6 -> System.out.println("开发中");
                    case 0 -> {
                        System.out.println("成功退出系统");
                        return;
                    }
                    default -> System.out.println("无效编号！");
                }
            } catch (InputException e) {
                System.out.println("输入错误：" + e.getMessage());
            }
        }
    }
}

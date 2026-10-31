package practicalpractice2.controller;

import practicalpractice2.category.CategoryHandler;
import practicalpractice2.goods.GoodsHandler;
import practicalpractice2.utils.exception.InputException;
import practicalpractice2.utils.input.InputUtil;
import practicalpractice2.utils.menu.MenuUtil;

import java.util.Scanner;

public class MainMenuUI {

    public static void showMenu(Scanner scanner, String currentUser, CategoryHandler categoryHandler, GoodsHandler goodsHandler) {
        boolean isValid = true;

        while (isValid) {
            System.out.println("\n当前账号: " + currentUser);
            MenuUtil.printMainMenu();

            try {
                int enterNumber = InputUtil.getInt(scanner);
                switch (enterNumber) {
                    case 1 -> System.out.println("开发中");
                    case 2 -> CategoryUI.showMenu(scanner, categoryHandler);
                    case 3 -> GoodsUI.showMenu(scanner, goodsHandler, categoryHandler);
                    case 4, 5, 6 -> System.out.println("开发中");
                    case 0 -> {
                        System.out.println("成功退出系统");
                        isValid = false;
                    }
                    default -> System.out.println("无效编号！");
                }
            } catch (InputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
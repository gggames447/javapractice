package practicalpractice3.controller;

import practicalpractice3.category.Category;
import practicalpractice3.category.CategoryHandler;
import practicalpractice3.utils.back.BackUtils;
import practicalpractice3.utils.exception.BackException;
import practicalpractice3.utils.exception.InputException;
import practicalpractice3.utils.input.InputUtil;
import practicalpractice3.utils.menu.MenuUtil;

import java.util.Scanner;

// 商品分类管理子菜单
public class CategoryUI {

    public static void showMenu(Scanner scanner, CategoryHandler categoryHandler) {
        while (true) {
            MenuUtil.printCategoryMenu();
            try {
                int choice = InputUtil.getInt(scanner);
                BackUtils.checkBack(choice);

                switch (choice) {
                    case 1 -> addCategory(scanner, categoryHandler);
                    case 2 -> categoryHandler.queryAll();
                    default -> System.out.println("无效编号！");
                }
            } catch (InputException e) {
                System.out.println("输入错误：" + e.getMessage());
            } catch (BackException e) {
                System.out.println("返回主菜单");
                return;
            }
        }
    }

    // 读取输入并新增分类
    private static void addCategory(Scanner scanner, CategoryHandler categoryHandler) {
        System.out.println("--- 新增分类 ---");
        String id = InputUtil.getString(scanner, "请输入分类编号：");
        String name = InputUtil.getString(scanner, "请输入分类名称：");
        String desc = InputUtil.getString(scanner, "请输入分类描述：");
        categoryHandler.addCategory(new Category(id, name, desc));
    }
}
package practicalpractice4.controller;

import practicalpractice4.category.Category;
import practicalpractice4.category.CategoryHandler;
import practicalpractice4.utils.back.BackUtils;
import practicalpractice4.utils.exception.BackException;
import practicalpractice4.utils.exception.InputException;
import practicalpractice4.utils.input.InputUtil;
import practicalpractice4.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 商品分类管理子菜单
 */
public class CategoryUI {

    /**
     * 显示分类管理菜单，循环处理用户的选择，输入 0 返回上级
     *
     * @param scanner 键盘输入
     * @param categoryHandler 分类数据
     */
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

    /**
     * 读取输入并新增分类
     *
     * @param scanner 键盘输入
     * @param categoryHandler 分类数据
     */
    private static void addCategory(Scanner scanner, CategoryHandler categoryHandler) {
        System.out.println("--- 新增分类 ---");
        String id = InputUtil.getString(scanner, "请输入分类编号：");
        String name = InputUtil.getString(scanner, "请输入分类名称：");
        String desc = InputUtil.getString(scanner, "请输入分类描述：");
        categoryHandler.addCategory(new Category(id, name, desc));
    }
}

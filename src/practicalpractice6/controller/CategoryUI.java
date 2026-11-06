package practicalpractice6.controller;

import practicalpractice6.entity.Category;
import practicalpractice6.handler.CategoryHandler;
import practicalpractice6.utils.input.InputUtil;
import practicalpractice6.utils.menu.BaseMenu;
import practicalpractice6.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 商品分类管理子菜单
 */
public class CategoryUI extends BaseMenu {
    private final CategoryHandler categoryHandler;

    /**
     * @param scanner 键盘输入
     * @param categoryHandler 分类数据
     */
    public CategoryUI(Scanner scanner, CategoryHandler categoryHandler) {
        super(scanner);
        this.categoryHandler = categoryHandler;
    }

    @Override
    protected void printMenu() {
        MenuUtil.printCategoryMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addCategory();
            case 2 -> categoryHandler.queryAll();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 读取输入并新增分类
     */
    private void addCategory() {
        System.out.println("--- 新增分类 ---");
        String id = InputUtil.getString(scanner, "请输入分类编号：");
        String name = InputUtil.getString(scanner, "请输入分类名称：");
        String desc = InputUtil.getString(scanner, "请输入分类描述：");
        categoryHandler.add(new Category(id, name, desc));
    }
}
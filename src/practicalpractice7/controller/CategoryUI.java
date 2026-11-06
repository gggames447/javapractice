package practicalpractice7.controller;

import practicalpractice7.entity.Category;
import practicalpractice7.handler.CategoryHandler;
import practicalpractice7.utils.input.InputUtil;
import practicalpractice7.utils.menu.BaseMenu;
import practicalpractice7.utils.menu.MenuUtil;
import practicalpractice7.view.CategoryView;

import java.util.Scanner;

/**
 * 商品分类管理子菜单
 */
public class CategoryUI extends BaseMenu {
    private final CategoryHandler categoryHandler;
    private final CategoryView categoryView = new CategoryView();

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
            case 2 -> categoryView.printTable(categoryHandler.findAll(), "暂无数据。");
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
        System.out.println("分类添加成功！");
    }
}

package practicalpractice2.controller;

import practicalpractice2.category.CategoryHandler;
import practicalpractice2.goods.Goods;
import practicalpractice2.goods.GoodsHandler;
import practicalpractice2.utils.back.BackUtils;
import practicalpractice2.utils.exception.BackException;
import practicalpractice2.utils.exception.InputException;
import practicalpractice2.utils.input.InputUtil;
import practicalpractice2.utils.menu.MenuUtil;

import java.util.Scanner;

// 商品管理子菜单，只负责读输入，具体操作交给 GoodsHandler
public class GoodsUI {

    // 显示商品管理菜单，循环处理用户的选择，输入 0 返回上级
    public static void showMenu(Scanner scanner, GoodsHandler goodsHandler, CategoryHandler categoryHandler) {
        while (true) {
            MenuUtil.printGoodsMenu();
            try {
                int choice = InputUtil.getInt(scanner);
                BackUtils.checkBack(choice);

                switch (choice) {
                    case 1 -> addGoods(scanner, goodsHandler, categoryHandler);
                    case 2 -> goodsHandler.queryAll();
                    case 3 -> queryByPriceRange(scanner, goodsHandler);
                    case 4 -> goodsHandler.stockAlarm();
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

    // 读取输入并新增商品，分类不存在时取消新增
    private static void addGoods(Scanner scanner, GoodsHandler goodsHandler, CategoryHandler categoryHandler) {
        System.out.println("--- 新增商品 ---");
        String id = InputUtil.getString(scanner, "请输入商品编号：");
        String name = InputUtil.getString(scanner, "请输入商品名称：");
        double price = InputUtil.getDouble(scanner, "请输入商品单价：");
        int stock = InputUtil.getInt(scanner, "请输入商品库存：");
        String cateId = InputUtil.getString(scanner, "请输入所属分类ID：");

        // 分类必须先存在，商品才能归到它下面
        if (!categoryHandler.existsById(cateId)) {
            System.out.println("分类不存在，新增失败！");
            return;
        }
        goodsHandler.addGoods(new Goods(id, name, price, stock, cateId));
    }

    // 读取价格区间并查询，下限不能大于上限
    private static void queryByPriceRange(Scanner scanner, GoodsHandler goodsHandler) {
        System.out.println("--- 按价格区间查询 ---");
        double min = InputUtil.getDouble(scanner, "请输入最低价格：");
        double max = InputUtil.getDouble(scanner, "请输入最高价格：");

        if (min > max) {
            System.out.println("最低价格不能大于最高价格！");
            return;
        }
        goodsHandler.queryByPriceRange(min, max);
    }
}

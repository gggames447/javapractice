package practicalpractice2.controller;

import practicalpractice2.category.CategoryHandler;
import practicalpractice2.goods.Goods;
import practicalpractice2.goods.GoodsHandler;
import practicalpractice2.utils.back.BackUtils;
import practicalpractice2.utils.exception.BackException;
import practicalpractice2.utils.exception.BusinessException;
import practicalpractice2.utils.exception.InputException;
import practicalpractice2.utils.input.InputUtil;
import practicalpractice2.utils.menu.MenuUtil;
import practicalpractice2.view.GoodsView;

import java.util.Scanner;

// 商品管理子菜单：读输入，交给 GoodsHandler 处理，再用 GoodsView 显示结果
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
                    case 2 -> printAllGoods(goodsHandler);
                    case 3 -> findByPriceRange(scanner, goodsHandler);
                    case 4 -> stockAlarm(goodsHandler);
                    default -> System.out.println("无效编号！");
                }
            } catch (InputException e) {
                System.out.println("输入错误：" + e.getMessage());
            } catch (BusinessException e) {
                // InputException 也是 BusinessException，所以必须写在它后面
                System.out.println("操作失败：" + e.getMessage());
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
            throw new BusinessException("分类「" + cateId + "」不存在");
        }
        goodsHandler.addGoods(new Goods(id, name, price, stock, cateId));
        System.out.println("商品添加成功！");
    }

    // 读取价格区间并查询，下限不能大于上限
    private static void findByPriceRange(Scanner scanner, GoodsHandler goodsHandler) {
        System.out.println("--- 按价格区间查询 ---");
        double min = InputUtil.getDouble(scanner, "请输入最低价格：");
        double max = InputUtil.getDouble(scanner, "请输入最高价格：");

        if (min > max) {
            System.out.println("最低价格不能大于最高价格！");
            return;
        }
        GoodsView.printTable(goodsHandler.findByPriceRange(min, max), "在该价格区间内未找到商品。");
    }

    // 显示全部商品
    private static void printAllGoods(GoodsHandler goodsHandler) {
        GoodsView.printTable(goodsHandler.findAll(), "暂无数据。");
    }

    // 库存预警：列出库存低于 20 的商品
    private static void stockAlarm(GoodsHandler goodsHandler) {
        if (goodsHandler.isEmpty()) {
            System.out.println("暂无商品，无法进行库存预警。");
            return;
        }
        GoodsView.printLowStock(goodsHandler.findLowStock());
    }
}

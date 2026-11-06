package practicalpractice8.controller;

import practicalpractice8.entity.Goods;
import practicalpractice8.handler.CategoryHandler;
import practicalpractice8.handler.GoodsHandler;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.input.InputUtil;
import practicalpractice8.utils.menu.BaseMenu;
import practicalpractice8.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 商品管理子菜单，只负责读输入和显示结果，具体操作交给 GoodsHandler
 */
public class GoodsUI extends BaseMenu {
    private final GoodsHandler goodsHandler;
    private final CategoryHandler categoryHandler;

    /**
     * @param scanner 键盘输入
     * @param goodsHandler 商品数据
     * @param categoryHandler 分类数据，新增商品时用来校验分类是否存在
     */
    public GoodsUI(Scanner scanner, GoodsHandler goodsHandler, CategoryHandler categoryHandler) {
        super(scanner);
        this.goodsHandler = goodsHandler;
        this.categoryHandler = categoryHandler;
    }

    @Override
    protected void printMenu() {
        MenuUtil.printGoodsMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addGoods();
            case 2 -> goodsHandler.queryAll();
            case 3 -> queryByPriceRange();
            case 4 -> goodsHandler.stockAlarm();
            case 5 -> goodsHandler.findGoods(InputUtil.getString(scanner, "请输入商品名称："));
            case 6 -> deleteGoods();
            case 7 -> sortByPrice();
            case 8 -> goodsHandler.queryByKeyword(InputUtil.getString(scanner, "请输入关键字："));
            case 9 -> goodsHandler.showStatistics();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 读取输入并新增商品，所属分类必须已经存在
     *
     * @throws BusinessException 分类不存在，或商品编号、名称重复时
     */
    private void addGoods() {
        System.out.println("--- 新增商品 ---");
        String id = InputUtil.getString(scanner, "请输入商品编号：");
        String name = InputUtil.getString(scanner, "请输入商品名称：");
        double price = InputUtil.getDouble(scanner, "请输入商品单价：");
        int stock = InputUtil.getInt(scanner, "请输入商品库存：");
        String cateId = InputUtil.getString(scanner, "请输入所属分类编号：");

        if (!categoryHandler.existsById(cateId)) {
            throw new BusinessException("分类「" + cateId + "」不存在，请先添加分类");
        }
        goodsHandler.add(new Goods(id, name, price, stock, cateId));
        System.out.println("商品添加成功！");
    }

    /**
     * 输入商品名称并删除
     */
    private void deleteGoods() {
        String name = InputUtil.getString(scanner, "请输入要删除的商品名称：");
        goodsHandler.deleteByName(name);
        System.out.println("商品删除成功！");
    }

    /**
     * 读取价格区间并查询，下限不能大于上限
     */
    private void queryByPriceRange() {
        double min = InputUtil.getDouble(scanner, "请输入最低价格：");
        double max = InputUtil.getDouble(scanner, "请输入最高价格：");
        if (min > max) {
            System.out.println("最低价格不能大于最高价格！");
            return;
        }
        goodsHandler.findGoods(min, max);
    }

    /**
     * 选择排序方式：1 升序，2 降序，0 取消
     */
    private void sortByPrice() {
        MenuUtil.printSortMenu();
        int type = InputUtil.getInt(scanner);
        switch (type) {
            case 1, 2 -> goodsHandler.sortByPrice(type);
            case 0 -> System.out.println("已取消排序");
            default -> System.out.println("无效编号！");
        }
    }
}

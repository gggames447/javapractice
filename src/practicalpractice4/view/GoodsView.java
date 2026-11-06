package practicalpractice4.view;

import practicalpractice4.goods.Goods;
import practicalpractice4.utils.menu.MenuUtil;

/**
 * 显示商品表格和库存预警。GoodsHandler 只负责查数据，打印都在这里。
 */
public class GoodsView {

    // 工具类，只提供静态方法，不需要创建对象
    private GoodsView() {}

    /**
     * 打印商品表格
     *
     * @param goodsList 要显示的商品
     * @param emptyMessage 没有商品时显示的提示
     */
    public static void printTable(Goods[] goodsList, String emptyMessage) {
        if (goodsList.length == 0) {
            System.out.println(emptyMessage);
            return;
        }
        printHeader();
        for (Goods goods : goodsList) {
            printRow(goods);
        }
    }

    /**
     * 只打印一个商品，也带表头
     *
     * @param goods 要显示的商品
     */
    public static void printOne(Goods goods) {
        printHeader();
        printRow(goods);
    }

    /**
     * 打印库存预警的结果
     *
     * @param lowStock 库存紧张的商品
     */
    public static void printLowStock(Goods[] lowStock) {
        if (lowStock.length == 0) {
            System.out.println("所有商品库存充足。");
            return;
        }
        for (Goods goods : lowStock) {
            System.out.println("【库存紧张】" + goods.getGoodName() + "，剩余库存：" + goods.getGoodStock());
        }
        System.out.println("共有 " + lowStock.length + " 个商品库存紧张。");
    }

    private static void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-15s %-10s %-8s %s%n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        MenuUtil.printLine();
    }

    private static void printRow(Goods goods) {
        System.out.printf("%-10s %-15s %-10.2f %-8d %s%n",
                goods.getGoodId(), goods.getGoodName(), goods.getGoodPrice(), goods.getGoodStock(), goods.getCateId());
    }
}

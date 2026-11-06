package practicalpractice2.view;

import practicalpractice2.goods.Goods;
import practicalpractice2.utils.menu.MenuUtil;

// 显示商品表格和库存预警。GoodsHandler 只负责查数据，打印都在这里。
public class GoodsView {

    // 工具类，只提供静态方法，不需要创建对象
    private GoodsView() {}

    // 打印商品表格
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

    // 打印库存预警的结果
    public static void printLowStock(Goods[] lowStock) {
        if (lowStock.length == 0) {
            System.out.println("所有商品库存充足。");
            return;
        }
        for (Goods goods : lowStock) {
            System.out.println("【库存紧张】" + goods.getGoodName() + "，剩余库存：" + goods.getGoodStock());
        }
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

package practicalpractice6.view;

import practicalpractice6.entity.Goods;

/**
 * 显示商品表格和库存预警
 */
public class GoodsView extends TableView<Goods> {

    /**
     * 打印库存预警的结果
     *
     * @param lowStock 库存紧张的商品
     */
    public void printLowStock(Goods[] lowStock) {
        if (lowStock.length == 0) {
            System.out.println("所有商品库存充足。");
            return;
        }
        for (Goods goods : lowStock) {
            System.out.println("【库存紧张】" + goods.getGoodsName() + "，剩余库存：" + goods.getStock());
        }
        System.out.println("共有 " + lowStock.length + " 个商品库存紧张。");
    }

    @Override
    protected void printColumnNames() {
        System.out.printf("%-10s %-15s %-10s %-8s %s%n", "商品编号", "商品名称", "单价", "库存", "分类ID");
    }

    @Override
    protected void printRow(Goods item) {
        System.out.printf("%-10s %-15s %-10.2f %-8d %s%n",
                item.getGoodsId(), item.getGoodsName(), item.getPrice(), item.getStock(), item.getCateId());
    }
}

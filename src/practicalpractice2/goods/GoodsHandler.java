package practicalpractice2.goods;

import practicalpractice2.utils.menu.MenuUtil;

// 管理商品，用数组存储，最多 20 个
public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;

    // 有效商品的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final Goods[] goods = new Goods[MAX_SIZE];

    // 新增商品：容量、单价、库存、编号和名称都检查通过才保存
    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            System.out.println("商品存储已满，无法新增！");
            return;
        }
        if (newGoods.getGoodPrice() <= 0) {
            System.out.println("单价必须大于 0，新增失败！");
            return;
        }
        if (newGoods.getGoodStock() < 0) {
            System.out.println("库存不能为负数，新增失败！");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (newGoods.getGoodId().equals(goods[i].getGoodId())) {
                System.out.println("商品编号已存在，新增失败！");
                return;
            }
            if (newGoods.getGoodName().equals(goods[i].getGoodName())) {
                System.out.println("商品名称已存在，新增失败！");
                return;
            }
        }
        goods[count] = newGoods;
        count++;
        System.out.println("商品添加成功！");
    }

    // 查看全部商品
    public void queryAll() {
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            printGoods(goods[i]);
        }
    }

    // 查询价格在 minPrice ~ maxPrice 之间的商品（包含两端）
    public void queryByPriceRange(double minPrice, double maxPrice) {
        int found = 0;
        for (int i = 0; i < count; i++) {
            double price = goods[i].getGoodPrice();
            if (price >= minPrice && price <= maxPrice) {
                printFound(goods[i], found);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("在该价格区间内未找到商品。");
        }
    }

    // 列出库存低于 20 的商品
    public void stockAlarm() {
        if (count == 0) {
            System.out.println("暂无商品，无法进行库存预警。");
            return;
        }
        int lowStockCount = 0;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
                System.out.println("【库存紧张】" + goods[i].getGoodName() + "，剩余库存：" + goods[i].getGoodStock());
                lowStockCount++;
            }
        }
        if (lowStockCount == 0) {
            System.out.println("所有商品库存充足。");
        }
    }

    // 查询时打印一个找到的商品；找到的是第一个时先打印表头，一个都没找到就不会出现空表头
    private void printFound(Goods g, int foundBefore) {
        if (foundBefore == 0) {
            printHeader();
        }
        printGoods(g);
    }

    private void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-15s %-10s %-8s %s%n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        MenuUtil.printLine();
    }

    private void printGoods(Goods g) {
        System.out.printf("%-10s %-15s %-10.2f %-8d %s%n",
                g.getGoodId(), g.getGoodName(), g.getGoodPrice(), g.getGoodStock(), g.getCateId());
    }
}

package practicalpractice2.goods;

public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;
    private int count = 0;
    private Goods[] goods = new Goods[MAX_SIZE];

    private void printHeader() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s\n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        System.out.println("------------------------------------------------------------");
    }

    private void printGoods(Goods g) {
        System.out.printf("%-10s %-15s %-10.2f %-10d %-10s\n",
                g.getGoodId(), g.getGoodName(), g.getGoodPrice(), g.getGoodStock(), g.getCateId());
    }

    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            System.out.println("仓库已满，无法新增！");
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
            if (newGoods.getGoodName().equals(goods[i].getGoodName())) {
                System.out.println("名称已存在，新增失败！");
                return;
            }
        }
        goods[count] = newGoods;
        count++;
        System.out.println("商品添加成功！");
    }

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

    public void queryByPriceRange(double minPrice, double maxPrice) {
        boolean isFound = false;
        printHeader();
        for (int i = 0; i < count; i++) {
            double currentPrice = goods[i].getGoodPrice();
            if (currentPrice >= minPrice && currentPrice <= maxPrice) {
                printGoods(goods[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("在该价格区间内未找到商品。");
        }
    }

    public void stockAlarm() {
        if (count == 0) {
            System.out.println("暂无商品，无法进行库存预警。");
            return;
        }

        System.out.println("--- 库存预警 ---");
        boolean hasAlarm = false;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
                System.out.println("【库存紧张】 " + goods[i].getGoodName() + " 剩余库存: " + goods[i].getGoodStock());
                hasAlarm = true;
            }
        }
        if (!hasAlarm) {
            System.out.println("所有商品库存充足。");
        }
    }
}
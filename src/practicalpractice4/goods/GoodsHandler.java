package practicalpractice4.goods;

import practicalpractice4.utils.menu.MenuUtil;

/**
 * 管理商品，用数组存储，最多 20 个
 */
public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;

    // 有效商品的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final Goods[] goods = new Goods[MAX_SIZE];

    /**
     * 新增商品，容量、单价、库存、编号和名称都检查通过才保存
     *
     * @param newGoods 要新增的商品
     */
    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            System.out.println("【商品存储上限，无法新增】");
            return;
        }
        if (!checkPrice(newGoods.getGoodPrice())) {
            System.out.println("单价必须大于 0，新增失败！");
            return;
        }
        if (!checkStock(newGoods.getGoodStock())) {
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

    /**
     * 按名称删除商品，后面的商品依次前移，中间不留空位
     *
     * @param name 要删除的商品名称
     */
    public void deleteGoods(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品，删除失败！");
            return;
        }
        for (int i = index; i < count - 1; i++) {
            goods[i] = goods[i + 1];
        }
        count--;
        goods[count] = null;
        System.out.println("商品删除成功！");
    }

    /**
     * 查看全部商品，没有商品时提示暂无数据
     */
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

    /**
     * 按名称精确查找并打印商品
     *
     * @param name 商品名称
     */
    public void findGoods(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品。");
            return;
        }
        printHeader();
        printGoods(goods[index]);
    }

    /**
     * 查找并打印价格在区间内的商品（包含两端）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     */
    public void findGoods(double minPrice, double maxPrice) {
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

    /**
     * 模糊查询：名称包含关键字的商品都会打印出来
     *
     * @param keyword 关键字
     */
    public void queryByKeyword(String keyword) {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodName().contains(keyword)) {
                printFound(goods[i], found);
                found++;
            }
        }
        if (found == 0) {
            System.out.println("未找到商品。");
        }
    }

    /**
     * 统计库存紧张的商品数量
     *
     * @return 库存低于 20 的商品个数
     */
    public int countLowStock() {
        int lowStockCount = 0;
        for (int i = 0; i < count; i++) {
            if (isLowStock(goods[i])) {
                lowStockCount++;
            }
        }
        return lowStockCount;
    }

    /**
     * 库存预警：列出库存低于 20 的商品，并显示数量
     */
    public void stockAlarm() {
        if (count == 0) {
            System.out.println("暂无商品，无法进行库存预警。");
            return;
        }
        int lowStockCount = countLowStock();
        if (lowStockCount == 0) {
            System.out.println("所有商品库存充足。");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (isLowStock(goods[i])) {
                System.out.println("【库存紧张】" + goods[i].getGoodName() + "，剩余库存：" + goods[i].getGoodStock());
            }
        }
        System.out.println("共有 " + lowStockCount + " 个商品库存紧张。");
    }

    /**
     * 用冒泡排序按价格排序并打印结果，排序会改变数组里原来的顺序
     *
     * @param type 1 为升序，2 为降序
     */
    public void sortByPrice(int type) {
        for (int i = 0; i < count - 1; i++) {
            // 每轮结束后最后 i 个已经排好，所以每轮少比一次
            for (int j = 0; j < count - 1 - i; j++) {
                double current = goods[j].getGoodPrice();
                double next = goods[j + 1].getGoodPrice();
                boolean needSwap = (type == 1) ? current > next : current < next;
                if (needSwap) {
                    Goods temp = goods[j];
                    goods[j] = goods[j + 1];
                    goods[j + 1] = temp;
                }
            }
        }
        queryAll();
    }

    /**
     * 统计并打印总库存和平均单价，没有商品时提示暂无数据
     */
    public void showStatistics() {
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
        int totalStock = 0;
        double totalPrice = 0;
        for (int i = 0; i < count; i++) {
            totalStock += goods[i].getGoodStock();
            totalPrice += goods[i].getGoodPrice();
        }
        double averagePrice = totalPrice / count;
        System.out.printf("总库存：%d，平均单价：%.2f%n", totalStock, averagePrice);
    }

    /**
     * 按名称查找商品的下标，查找和删除都会用到
     *
     * @param name 商品名称
     * @return 找到返回下标，找不到返回 -1
     */
    private int findIndexByName(String name) {
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param price 商品单价
     * @return 大于 0 返回 true
     */
    private boolean checkPrice(double price) {
        return price > 0;
    }

    /**
     * @param stock 商品库存
     * @return 大于等于 0 返回 true
     */
    private boolean checkStock(int stock) {
        return stock >= 0;
    }

    private boolean isLowStock(Goods g) {
        return g.getGoodStock() < LOW_STOCK_THRESHOLD;
    }

    /**
     * 查询时打印一个找到的商品。找到的是第一个时先打印表头，
     * 这样一个都没找到时就不会出现一个空表头。
     *
     * @param g 找到的商品
     * @param foundBefore 在它之前已经找到了几个
     */
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

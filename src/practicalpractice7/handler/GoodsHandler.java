package practicalpractice7.handler;

import practicalpractice7.entity.Goods;

/**
 * 管理商品：查找、排序、库存预警和统计。
 * 存储、新增、查看全部、按 ID 删除由父类 BaseHandler 提供。
 */
public class GoodsHandler extends BaseHandler<Goods> {
    private static final int LOW_STOCK_THRESHOLD = 20;

    public GoodsHandler() {
        super(new Goods[MAX_SIZE]);
    }

    @Override
    protected void printHeader() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s%n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        System.out.println("------------------------------------------------------------");
    }

    @Override
    protected void printItem(Goods item) {
        System.out.printf("%-10s %-15s %-10.2f %-10d %-10s%n",
                item.getGoodId(), item.getGoodName(), item.getGoodPrice(), item.getGoodStock(), item.getCateId());
    }

    /**
     * 新增商品时，除了父类检查的容量和编号，名称也不能重复
     *
     * @param item 要新增的商品
     * @return 名称不重复返回 true，否则返回 false
     */
    @Override
    protected boolean checkBeforeAdd(Goods item) {
        if (findIndexByName(item.getGoodName()) != -1) {
            System.out.println("名称已存在，新增失败！");
            return false;
        }
        return true;
    }

    /**
     * 按名称查找商品的下标，查找和删除都会用到
     *
     * @param name 商品名称
     * @return 找到返回下标，找不到返回 -1
     */
    private int findIndexByName(String name) {
        for (int i = 0; i < count; i++) {
            if (array[i].getGoodName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 按名称查找商品，下单时用来取得商品对象
     *
     * @param name 商品名称
     * @return 找到返回该商品，找不到返回 null
     */
    public Goods findByName(String name) {
        int index = findIndexByName(name);
        return (index == -1) ? null : array[index];
    }

    /**
     * 按名称删除商品，找到后交给父类按 ID 删除
     *
     * @param name 要删除的商品名称
     */
    public void deleteGoods(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品，删除失败");
            return;
        }
        delete(array[index].getId());
        System.out.println("商品删除成功！");
    }

    /**
     * 按名称精确查找并打印商品
     *
     * @param name 商品名称
     */
    public void findGoods(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品");
        } else {
            printHeader();
            printItem(array[index]);
        }
    }

    /**
     * 查找并打印价格在区间内的商品（包含两端）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     */
    public void findGoods(double minPrice, double maxPrice) {
        boolean isFound = false;
        printHeader();
        for (int i = 0; i < count; i++) {
            double currentPrice = array[i].getGoodPrice();
            if (currentPrice >= minPrice && currentPrice <= maxPrice) {
                printItem(array[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("在该价格区间内未找到商品。");
        }
    }

    /**
     * 模糊查询：名称包含关键字的商品都会打印出来
     *
     * @param keyword 关键字
     */
    public void queryByKeyword(String keyword) {
        boolean isFound = false;
        printHeader();
        for (int i = 0; i < count; i++) {
            if (array[i].getGoodName().contains(keyword)) {
                printItem(array[i]);
                isFound = true;
            }
        }
        if (!isFound) {
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
            if (array[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
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
        System.out.println("--- 库存预警 ---");
        int lowStockCount = countLowStock();
        if (lowStockCount == 0) {
            System.out.println("所有商品库存充足。");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (array[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
                System.out.println("【库存紧张】 " + array[i].getGoodName() + " 剩余库存: " + array[i].getGoodStock());
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
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
        for (int i = 0; i < count - 1; i++) {
            // 每轮结束后最后 i 个已经排好，所以每轮少比一次
            for (int j = 0; j < count - 1 - i; j++) {
                double current = array[j].getGoodPrice();
                double next = array[j + 1].getGoodPrice();
                boolean needSwap = (type == 1) ? current > next : current < next;
                if (needSwap) {
                    Goods temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
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
            totalStock += array[i].getGoodStock();
            totalPrice += array[i].getGoodPrice();
        }
        double averagePrice = totalPrice / count;
        System.out.printf("总库存：%d，平均单价：%.2f%n", totalStock, averagePrice);
    }
    /**
     * 按商品编号查找商品，下单时用来检查和扣减库存
     *
     * @param id 商品编号
     * @return 找到返回该商品，找不到返回 null
     */
    public Goods findById(String id) {
        int index = findIndexById(id);
        return (index == -1) ? null : array[index];
    }
}
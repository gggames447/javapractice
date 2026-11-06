package practicalpractice8.handler;

import practicalpractice8.entity.Goods;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.menu.MenuUtil;

/**
 * 管理商品：查找、排序、库存预警和统计。
 * 存储、新增、查看全部、按 ID 删除由父类 BaseHandler 提供。
 */
public class GoodsHandler extends BaseHandler<Goods> {
    private static final int LOW_STOCK_THRESHOLD = 20;

    public GoodsHandler() {
        super(new Goods[MAX_SIZE]);
    }

    /**
     * 新增商品时，除了父类检查的容量和编号，名称也不能重复
     *
     * @param item 要新增的商品
     * @throws BusinessException 名称已存在时
     */
    @Override
    protected void validateBeforeAdd(Goods item) {
        if (findByName(item.getGoodsName()) != null) {
            throw new BusinessException("商品名称「" + item.getGoodsName() + "」已存在");
        }
    }

    /**
     * 按名称查找商品
     *
     * @param name 商品名称
     * @return 找到返回该商品，找不到返回 null
     */
    public Goods findByName(String name) {
        for (int i = 0; i < count; i++) {
            if (array[i].getGoodsName().equals(name)) {
                return array[i];
            }
        }
        return null;
    }

    /**
     * 按名称删除商品，找到后交给父类按 ID 删除
     *
     * @param name 要删除的商品名称
     * @throws BusinessException 找不到该商品时
     */
    public void deleteByName(String name) {
        Goods goods = findByName(name);
        if (goods == null) {
            throw new BusinessException("找不到商品「" + name + "」");
        }
        delete(goods.getGoodsId());
    }

    /**
     * 按名称精确查找并打印商品
     *
     * @param name 商品名称
     */
    public void findGoods(String name) {
        Goods goods = findByName(name);
        if (goods == null) {
            System.out.println("找不到该商品。");
            return;
        }
        printHeader();
        printItem(goods);
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
            double price = array[i].getPrice();
            if (price >= minPrice && price <= maxPrice) {
                printFound(array[i], found);
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
            if (array[i].getGoodsName().contains(keyword)) {
                printFound(array[i], found);
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
            if (isLowStock(array[i])) {
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
            if (isLowStock(array[i])) {
                System.out.println("【库存紧张】" + array[i].getGoodsName() + "，剩余库存：" + array[i].getStock());
            }
        }
        System.out.println("共有 " + lowStockCount + " 个商品库存紧张。");
    }

    private boolean isLowStock(Goods goods) {
        return goods.getStock() < LOW_STOCK_THRESHOLD;
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
                double current = array[j].getPrice();
                double next = array[j + 1].getPrice();
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
            totalStock += array[i].getStock();
            totalPrice += array[i].getPrice();
        }
        double averagePrice = totalPrice / count;
        System.out.printf("总库存：%d，平均单价：%.2f%n", totalStock, averagePrice);
    }

    @Override
    protected void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-15s %-10s %-8s %s%n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        MenuUtil.printLine();
    }

    @Override
    protected void printItem(Goods item) {
        System.out.printf("%-10s %-15s %-10.2f %-8d %s%n",
                item.getGoodsId(), item.getGoodsName(), item.getPrice(), item.getStock(), item.getCateId());
    }
}

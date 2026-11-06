package practicalpractice4.goods;

import practicalpractice4.utils.exception.BusinessException;
import practicalpractice4.utils.exception.InputException;

import java.util.Arrays;

/**
 * 管理商品，用数组存储，最多 20 个。只管数据和规则，不打印，显示交给 GoodsView。
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
     * @throws InputException 单价或库存不合法时
     * @throws BusinessException 存储已满，或编号、名称已存在时
     */
    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            throw new BusinessException("【商品存储上限，无法新增】");
        }
        if (!checkPrice(newGoods.getGoodPrice())) {
            throw new InputException("单价必须大于 0");
        }
        if (!checkStock(newGoods.getGoodStock())) {
            throw new InputException("库存不能为负数");
        }
        for (int i = 0; i < count; i++) {
            if (newGoods.getGoodId().equals(goods[i].getGoodId())) {
                throw new BusinessException("商品编号「" + newGoods.getGoodId() + "」已存在");
            }
            if (newGoods.getGoodName().equals(goods[i].getGoodName())) {
                throw new BusinessException("商品名称「" + newGoods.getGoodName() + "」已存在");
            }
        }
        goods[count] = newGoods;
        count++;
    }

    /**
     * 按名称删除商品，后面的商品依次前移，中间不留空位
     *
     * @param name 要删除的商品名称
     * @throws BusinessException 找不到该商品时
     */
    public void deleteGoods(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            throw new BusinessException("找不到商品「" + name + "」");
        }
        for (int i = index; i < count - 1; i++) {
            goods[i] = goods[i + 1];
        }
        count--;
        goods[count] = null;
    }

    /**
     * @return 全部商品的副本，没有商品时长度为 0
     */
    public Goods[] findAll() {
        return Arrays.copyOf(goods, count);
    }

    /**
     * @return 一个商品都没有时返回 true
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * 按名称精确查找商品
     *
     * @param name 商品名称
     * @return 找到返回该商品，找不到返回 null
     */
    public Goods findGoods(String name) {
        int index = findIndexByName(name);
        return (index == -1) ? null : goods[index];
    }

    /**
     * 查找价格在区间内的商品（包含两端）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 找到的商品，一个都没有时长度为 0
     */
    public Goods[] findGoods(double minPrice, double maxPrice) {
        Goods[] result = new Goods[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            double price = goods[i].getGoodPrice();
            if (price >= minPrice && price <= maxPrice) {
                result[found] = goods[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * 模糊查询：名称包含关键字的商品
     *
     * @param keyword 关键字
     * @return 找到的商品，一个都没有时长度为 0
     */
    public Goods[] findByKeyword(String keyword) {
        Goods[] result = new Goods[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodName().contains(keyword)) {
                result[found] = goods[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * 库存预警：找出库存低于 20 的商品
     *
     * @return 库存紧张的商品，一个都没有时长度为 0
     */
    public Goods[] findLowStock() {
        Goods[] result = new Goods[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
                result[found] = goods[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * 统计库存紧张的商品数量（拓展任务 5）
     *
     * @return 库存低于 20 的商品个数
     */
    public int countLowStock() {
        return findLowStock().length;
    }

    /**
     * 用冒泡排序按价格排序，排序会改变数组里原来的顺序
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
    }

    /**
     * @return 所有商品的库存之和
     */
    public int getTotalStock() {
        int totalStock = 0;
        for (int i = 0; i < count; i++) {
            totalStock += goods[i].getGoodStock();
        }
        return totalStock;
    }

    /**
     * @return 所有商品的平均单价，没有商品时返回 0
     */
    public double getAveragePrice() {
        if (count == 0) {
            return 0;
        }
        double totalPrice = 0;
        for (int i = 0; i < count; i++) {
            totalPrice += goods[i].getGoodPrice();
        }
        return totalPrice / count;
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
}

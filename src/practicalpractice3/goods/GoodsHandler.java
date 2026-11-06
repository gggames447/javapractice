package practicalpractice3.goods;

import practicalpractice3.utils.exception.BusinessException;
import practicalpractice3.utils.exception.InputException;

import java.util.Arrays;

// 管理商品，用数组存储，最多 20 个。只管数据和规则，不打印，显示交给 GoodsView。
public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;

    // 有效商品的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final Goods[] goods = new Goods[MAX_SIZE];

    // 新增商品：容量、单价、库存、编号和名称都检查通过才保存，不通过时抛出异常，由菜单提示
    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            throw new BusinessException("【商品存储上限，无法新增】");
        }
        if (newGoods.getGoodPrice() <= 0) {
            throw new InputException("单价必须大于 0");
        }
        if (newGoods.getGoodStock() < 0) {
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

    // 按名称删除商品，后面的依次前移，中间不留空位；找不到时抛出异常
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

    // 返回全部商品的副本，没有商品时长度为 0
    public Goods[] findAll() {
        return Arrays.copyOf(goods, count);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    // 按名称精确查找，找不到返回 null
    public Goods findByName(String name) {
        int index = findIndexByName(name);
        return (index == -1) ? null : goods[index];
    }

    // 查找价格在 minPrice ~ maxPrice 之间的商品（包含两端）
    public Goods[] findByPriceRange(double minPrice, double maxPrice) {
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

    // 模糊查询：名称包含关键字的商品
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

    // 库存预警：找出库存低于 20 的商品
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

    // 冒泡排序，type 为 1 升序、2 降序；排序会改变数组里原来的顺序
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

    // 所有商品的库存之和
    public int getTotalStock() {
        int totalStock = 0;
        for (int i = 0; i < count; i++) {
            totalStock += goods[i].getGoodStock();
        }
        return totalStock;
    }

    // 所有商品的平均单价，没有商品时返回 0
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

    // 找到返回下标，找不到返回 -1，查找和删除都会用到
    private int findIndexByName(String name) {
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}

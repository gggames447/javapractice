package practicalpractice2.goods;

import practicalpractice2.utils.exception.BusinessException;
import practicalpractice2.utils.exception.InputException;

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
            throw new BusinessException("商品存储已满，无法新增");
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

    // 返回全部商品的副本，没有商品时长度为 0
    public Goods[] findAll() {
        return Arrays.copyOf(goods, count);
    }

    public boolean isEmpty() {
        return count == 0;
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
}

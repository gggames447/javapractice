package practicalpractice6.handler;

import practicalpractice6.entity.Goods;
import practicalpractice6.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 管理商品：查找、排序、库存预警和统计。
 * 存储、新增、取得全部、按 ID 删除由父类 BaseHandler 提供。
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
     * 按名称精确查找商品
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
     * 查找价格在区间内的商品（包含两端）
     *
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @return 找到的商品，一个都没有时长度为 0
     */
    public Goods[] findByPriceRange(double minPrice, double maxPrice) {
        Goods[] result = new Goods[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            double price = array[i].getPrice();
            if (price >= minPrice && price <= maxPrice) {
                result[found] = array[i];
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
            if (array[i].getGoodsName().contains(keyword)) {
                result[found] = array[i];
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
            if (array[i].getStock() < LOW_STOCK_THRESHOLD) {
                result[found] = array[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
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
    }

    /**
     * @return 所有商品的库存之和
     */
    public int getTotalStock() {
        int totalStock = 0;
        for (int i = 0; i < count; i++) {
            totalStock += array[i].getStock();
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
            totalPrice += array[i].getPrice();
        }
        return totalPrice / count;
    }
}

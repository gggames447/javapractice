package practicalpractice3.goods;

import practicalpractice3.utils.exception.BusinessException;
import practicalpractice3.utils.exception.InputException;
import practicalpractice3.utils.menu.MenuUtil;

// 管理商品，用数组存储，最多 20 个
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

    // 按名称精确查找
    public void queryByName(String name) {
        int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品。");
            return;
        }
        printHeader();
        printGoods(goods[index]);
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

    // 模糊查询：名称包含关键字就输出
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
        queryAll();
    }

    // 统计总库存和平均单价
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

    // 找到返回下标，找不到返回 -1，查找和删除都会用到
    private int findIndexByName(String name) {
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodName().equals(name)) {
                return i;
            }
        }
        return -1;
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

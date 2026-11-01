package practicalpractice3.goods;

import java.util.Scanner;

// 管理商品，用数组存储，最多 20 个
public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;
    // 有效商品的个数，只有下标 0 ~ count-1 有数据，后面都是 null
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

    // 新增商品：容量、价格、库存、重名都检查通过才保存
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

    // 按名称删除商品，后面的依次前移，中间不留空位
    public void deleteGoods(String name){
       int index = findIndexByName(name);
        if (index == -1) {
            System.out.println("找不到该商品，删除失败");
            return;
        }
        for (int i = index; i < count - 1; i++) {
            goods[i] = goods[i + 1];
        }
        count--;
        goods[count] = null;
        System.out.println("商品删除成功！");
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
    public void queryByName(String name){
        int receive = findIndexByName(name);
        if(receive == -1){
            System.out.println("找不到该商品");
        }else{
            printHeader();
            printGoods(goods[receive]);
        }
    }
    // 查询价格在 minPrice ~ maxPrice 之间的商品（包含两端）
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

    // 列出库存低于 20 的商品
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

    // 找到返回下标，找不到返回 -1，查找和删除都会用到
    private int findIndexByName(String name){
        for (int i = 0; i < count; i++) {
            if(goods[i].getGoodName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    // 冒泡排序，type 为 1 升序、2 降序；排序会改变数组里原来的顺序
    public void sortByPrice(int type) {
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
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
    // 模糊查询：名称包含关键字就输出
    public void queryByKeyword(String keyword) {
        boolean isFound = false;
        printHeader();
        for (int i = 0; i < count; i++) {
            if(goods[i].getGoodName().contains(keyword)){
                printGoods(goods[i]);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("未找到商品。");
        }
    }
    // 统计总库存和平均单价
    public void showStatistics(){
        int totalStock = 0;
        double totalPrice = 0;
        if(count == 0 ){
            System.out.println("暂无数据。");
            return;
        }
        for (int i = 0; i < count; i++) {
            totalStock += goods[i].getGoodStock();
            totalPrice += goods[i].getGoodPrice() ;
        }
        double averagePrice = totalPrice / count;
        System.out.printf("总库存：%d，平均单价：%.2f%n", totalStock, averagePrice);
    }
}
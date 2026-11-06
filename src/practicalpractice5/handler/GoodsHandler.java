package practicalpractice5.handler;

import practicalpractice5.goods.Goods;

/**
 * 管理商品，用数组存储，最多 20 个
 */
public class GoodsHandler {
    private static final int MAX_SIZE = 20;
    private static final int LOW_STOCK_THRESHOLD = 20;
    // 有效商品的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private Goods[] goods = new Goods[MAX_SIZE];

    /**
     * 打印商品表格的表头
     */
    private void printHeader() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s\n", "商品编号", "商品名称", "单价", "库存", "分类ID");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * 按表格格式打印一个商品
     *
     * @param g 要打印的商品
     */
    private void printGoods(Goods g) {
        System.out.printf("%-10s %-15s %-10.2f %-10d %-10s\n",
                g.getGoodId(), g.getGoodName(), g.getGoodPrice(), g.getGoodStock(), g.getCateId());
    }

    /**
     * 新增商品，容量、编号和名称都检查通过才保存。
     * 单价和库存已经在 Goods 的 setter 里校验过，这里只检查和其他商品有没有冲突。
     *
     * @param newGoods 要新增的商品
     */
    public void addGoods(Goods newGoods) {
        if (count == goods.length) {
            System.out.println("仓库已满，无法新增！");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (newGoods.getGoodId().equals(goods[i].getGoodId())) {
                System.out.println("商品编号已存在，新增失败！");
                return;
            }
            if (newGoods.getGoodName().equals(goods[i].getGoodName())) {
                System.out.println("名称已存在，新增失败！");
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
     * 统计库存紧张的商品数量
     *
     * @return 库存低于 20 的商品个数
     */
    public int countLowStock(){
        int lowStockCount = 0;
        for (int i = 0; i < count; i++) {
            if (goods[i].getGoodStock() < LOW_STOCK_THRESHOLD) {
               lowStockCount++;
            }
        }
        return lowStockCount;
    }

    /**
     * 按名称精确查找并打印商品
     *
     * @param name 商品名称
     */
    public void findGoods(String name){
        int receive = findIndexByName(name);
        if(receive == -1){
            System.out.println("找不到该商品");
        }else{
            printHeader();
            printGoods(goods[receive]);
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

    /**
     * 库存预警：列出库存低于 20 的商品，并显示数量
     */
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
        System.out.println("共有 " + countLowStock() + " 个商品库存紧张。");
    }

    /**
     * 按名称查找商品的下标，查找和删除都会用到
     *
     * @param name 商品名称
     * @return 找到返回下标，找不到返回 -1
     */
    private int findIndexByName(String name){
        for (int i = 0; i < count; i++) {
            if(goods[i].getGoodName().equals(name)){
                return i;
            }
        }
        return -1;
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
     * 模糊查询：名称包含关键字的商品都会打印出来
     *
     * @param keyword 关键字
     */
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
    /**
     * 统计并打印总库存和平均单价，没有商品时提示暂无数据
     */
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
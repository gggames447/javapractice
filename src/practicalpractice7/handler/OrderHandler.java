package practicalpractice7.handler;

import practicalpractice7.entity.Goods;
import practicalpractice7.entity.Order;
import practicalpractice7.entity.OrderItem;
import practicalpractice7.entity.UserAccount;
import practicalpractice7.enums.OrderStatus;
import practicalpractice7.utils.exception.InputException;
import practicalpractice7.utils.exception.StockNotEnoughException;

import java.time.format.DateTimeFormatter;

/**
 * 管理订单：生成订单号、下单（检查并扣减库存）、查询和修改订单状态。
 * 存储、新增、查看全部、按 ID 删除由父类 BaseHandler 提供。
 */
public class OrderHandler extends BaseHandler<Order> {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final UserHandler userHandler;
    private final GoodsHandler goodsHandler;
    private int nextOrderNo = 1;

    /**
     * @param userHandler 用户数据，打印订单时用来根据 userId 查出用户名
     * @param goodsHandler 商品数据，下单时用来检查和扣减库存
     */
    public OrderHandler(UserHandler userHandler, GoodsHandler goodsHandler) {
        super(new Order[MAX_SIZE]);
        this.userHandler = userHandler;
        this.goodsHandler = goodsHandler;
    }

    /**
     * 生成下一个订单号，例如 O001。
     * 编号只在下单成功时才加 1（见 placeOrder），所以取消或失败的订单不会占用编号；
     * 用只增不减的 nextOrderNo，删除订单后也不会生成重复的编号。
     *
     * @return 新的订单号
     */
    public String nextOrderId() {
        return String.format("O%03d", nextOrderNo);
    }

    /**
     * 提交订单：先检查所有商品的库存，全部足够才扣库存并保存订单。
     * 只要有一种商品库存不足，就抛出异常，后面的扣库存和保存都不会执行，不扣任何库存。
     *
     * @param order 要提交的订单
     * @throws InputException 订单里没有商品，或订单数量已达上限时
     * @throws StockNotEnoughException 有商品不存在或库存不足时
     */
    public void placeOrder(Order order) {
        if (order.getItemCount() == 0) {
            throw new InputException("订单里没有商品");
        }
        if (count == array.length) {
            throw new InputException("订单数量已达上限");
        }
        checkStock(order);
        deductStock(order);
        add(order);
        nextOrderNo++;
    }

    /**
     * 检查订单里每一种商品的库存，只检查，不修改任何数据
     *
     * @param order 要检查的订单
     * @throws StockNotEnoughException 有商品不存在或库存不足时
     */
    private void checkStock(Order order) {
        for (OrderItem item : order.getItems()) {
            checkItemStock(item);
        }
    }

    /**
     * 检查一个订单项的商品是否存在、库存是否足够
     *
     * @param item 要检查的订单项
     * @throws StockNotEnoughException 商品不存在或库存不足时
     */
    private void checkItemStock(OrderItem item) {
        Goods goods = goodsHandler.findById(item.getGoodsId());
        if (goods == null) {
            throw new StockNotEnoughException("商品「" + item.getGoodsName() + "」已不存在");
        }
        if (goods.getGoodStock() < item.getNum()) {
            throw new StockNotEnoughException("商品「" + goods.getGoodName() + "」库存不足，当前库存 "
                    + goods.getGoodStock() + "，需要 " + item.getNum());
        }
    }

    /**
     * 扣减订单里每一种商品的库存，调用前必须先通过 checkStock 检查
     *
     * @param order 要扣库存的订单
     */
    private void deductStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Goods goods = goodsHandler.findById(item.getGoodsId());
            goods.setGoodStock(goods.getGoodStock() - item.getNum());
        }
    }
    /**
     * 修改订单状态
     *
     * @param orderId 订单号
     * @param newStatus 新状态
     */
    public void changeStatus(String orderId, OrderStatus newStatus) {
        int index = findIndexById(orderId);
        if (index == -1) {
            System.out.println("找不到该订单");
            return;
        }
        array[index].setStatus(newStatus);
        System.out.println("订单状态已改为：" + newStatus.getLabel());
    }
    @Override
    protected void printHeader() {
        System.out.println("------------------------------------------------------------------------");
        System.out.printf("%-8s %-10s %-18s %-6s %-10s %s%n",
                "订单号", "用户", "下单时间", "种数", "总金额", "状态");
        System.out.println("------------------------------------------------------------------------");
    }

    /**
     * 打印订单里每一种商品的名称、单价、数量和小计
     *
     * @param order 要打印的订单
     */
    private void printOrderItems(Order order) {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-15s %-10s %-8s %-10s%n", "商品名称", "单价", "数量", "小计");
        for (OrderItem item : order.getItems()) {
            System.out.printf("%-15s %-10.2f %-8d %-10.2f%n",
                    item.getGoodsName(), item.getUnitPrice(), item.getNum(), item.getSubTotal());
        }
    }

    @Override
    protected void printItem(Order order) {
        UserAccount user = userHandler.findById(order.getUserId());
        String userName = (user == null) ? "未知用户" : user.getUserName();
        System.out.printf("%-8s %-10s %-18s %-6d %-10.2f %s%n",
                order.getOrderId(),
                userName,
                order.getOrderTime().format(TIME_FORMAT),
                order.getItemCount(),
                order.getTotalMoney(),
                order.getStatus().getLabel());
    }
    /**
     * 打印订单的合计金额
     *
     * @param order 要打印的订单
     */
    private void printTotal(Order order) {
        System.out.println("------------------------------------------------------------");
        System.out.printf("订单总金额：%.2f%n", order.getTotalMoney());
    }
    /**
     * 查看订单详情：订单概要加上每一种商品的单价、数量和小计
     *
     * @param orderId 订单号
     */
    public void showDetail(String orderId) {
        int index = findIndexById(orderId);
        if (index == -1) {
            System.out.println("找不到该订单");
            return;
        }
        Order order = array[index];

        printHeader();
        printItem(order);
        printOrderItems(order);
        printTotal(order);
    }

    /**
     * 查看某个用户的全部订单
     *
     * @param userId 用户 ID
     */
    public void queryByUser(String userId) {
        if (countByUser(userId) == 0) {
            System.out.println("该用户暂无订单。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(userId)) {
                printItem(array[i]);
            }
        }
    }

    /**
     * 统计某个用户的订单数量
     *
     * @param userId 用户 ID
     * @return 该用户的订单个数
     */
    private int countByUser(String userId) {
        int orderCount = 0;
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(userId)) {
                orderCount++;
            }
        }
        return orderCount;
    }
}






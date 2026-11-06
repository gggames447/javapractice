package practicalpractice8.handler;

import practicalpractice8.entity.Goods;
import practicalpractice8.entity.Order;
import practicalpractice8.entity.OrderItem;
import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.enums.OrderStatus;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.exception.InputException;
import practicalpractice8.utils.exception.OrderStatusException;
import practicalpractice8.utils.exception.StockNotEnoughException;
import practicalpractice8.utils.menu.MenuUtil;

import java.time.format.DateTimeFormatter;

/**
 * 管理订单：生成订单号、下单（检查并扣减库存）、查询、修改状态、取消订单和消费统计。
 * 存储、新增、查看全部、按 ID 删除由父类 BaseHandler 提供。
 */
public class OrderHandler extends BaseHandler<Order> {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final UserHandler userHandler;
    private final GoodsHandler goodsHandler;
    private final AddressHandler addressHandler;
    private int nextOrderNo = 1;

    /**
     * @param userHandler 用户数据，打印订单时用来根据 userId 查出用户名
     * @param goodsHandler 商品数据，下单和取消订单时用来扣减、回补库存
     * @param addressHandler 地址数据，查看订单详情时用来显示收货地址
     */
    public OrderHandler(UserHandler userHandler, GoodsHandler goodsHandler, AddressHandler addressHandler) {
        super(new Order[MAX_SIZE]);
        this.userHandler = userHandler;
        this.goodsHandler = goodsHandler;
        this.addressHandler = addressHandler;
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
     * @throws InputException 订单里没有商品时
     * @throws BusinessException 订单数量已达上限时
     * @throws StockNotEnoughException 有商品不存在或库存不足时
     */
    public void placeOrder(Order order) {
        if (order.getItemCount() == 0) {
            throw new InputException("订单里没有商品");
        }
        if (count == array.length) {
            throw new BusinessException("订单数量已达上限");
        }
        checkStock(order);
        deductStock(order);
        add(order);
        nextOrderNo++;
    }

    /**
     * 修改订单状态。改为「已取消」时会走取消订单的流程，把库存加回去。
     *
     * @param orderId 订单号
     * @param newStatus 新状态
     * @throws BusinessException 找不到订单时
     * @throws OrderStatusException 不允许改为新状态时
     */
    public void changeStatus(String orderId, OrderStatus newStatus) {
        if (newStatus == OrderStatus.CANCELLED) {
            cancelOrder(orderId);
            return;
        }
        getOrder(orderId).setStatus(newStatus);
    }

    /**
     * 取消订单：只有待付款的订单可以取消，取消后把每种商品的数量加回库存
     *
     * @param orderId 订单号
     * @throws BusinessException 找不到订单时
     * @throws OrderStatusException 订单不是待付款状态时
     */
    public void cancelOrder(String orderId) {
        Order order = getOrder(orderId);
        order.setStatus(OrderStatus.CANCELLED);
        restoreStock(order);
    }

    /**
     * 统计某个用户的总消费，已取消的订单不计入
     *
     * @param userId 用户 ID
     * @return 总消费金额
     */
    public double totalMoneyByUser(String userId) {
        double total = 0;
        for (int i = 0; i < count; i++) {
            Order order = array[i];
            if (order.getUserId().equals(userId) && order.getStatus() != OrderStatus.CANCELLED) {
                total += order.getTotalMoney();
            }
        }
        return total;
    }

    /**
     * 查看某个用户的全部订单
     *
     * @param userId 用户 ID
     */
    public void queryByUser(String userId) {
        printMatching(order -> order.getUserId().equals(userId), "该用户暂无订单。");
    }

    /**
     * 查看订单详情：订单概要、收货地址、每一种商品的单价、数量和小计，以及合计
     *
     * @param orderId 订单号
     * @throws BusinessException 找不到订单时
     */
    public void showDetail(String orderId) {
        Order order = getOrder(orderId);
        printHeader();
        printItem(order);
        printAddress(order);
        printOrderItems(order);
        printTotal(order);
    }

    /**
     * 按订单号取得订单，找不到时直接报错
     *
     * @param orderId 订单号
     * @return 找到的订单
     * @throws BusinessException 找不到订单时
     */
    private Order getOrder(String orderId) {
        Order order = findById(orderId);
        if (order == null) {
            throw new BusinessException("找不到订单「" + orderId + "」");
        }
        return order;
    }

    /**
     * 检查订单里每一种商品的库存，只检查，不修改任何数据
     *
     * @param order 要检查的订单
     * @throws StockNotEnoughException 有商品不存在或库存不足时
     */
    private void checkStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Goods goods = goodsHandler.findById(item.getGoodsId());
            if (goods == null) {
                throw new StockNotEnoughException("商品「" + item.getGoodsName() + "」已不存在");
            }
            if (goods.getStock() < item.getNum()) {
                throw new StockNotEnoughException("商品「" + goods.getGoodsName() + "」库存不足，当前库存 "
                        + goods.getStock() + "，需要 " + item.getNum());
            }
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
            goods.setStock(goods.getStock() - item.getNum());
        }
    }

    /**
     * 把订单里每一种商品的数量加回库存。商品已被删除时跳过。
     *
     * @param order 要回补库存的订单
     */
    private void restoreStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Goods goods = goodsHandler.findById(item.getGoodsId());
            if (goods != null) {
                goods.setStock(goods.getStock() + item.getNum());
            }
        }
    }

    @Override
    protected void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-8s %-10s %-18s %-6s %-10s %s%n", "订单号", "用户", "下单时间", "种数", "总金额", "状态");
        MenuUtil.printLine();
    }

    @Override
    protected void printItem(Order order) {
        System.out.printf("%-8s %-10s %-18s %-6d %-10.2f %s%n",
                order.getOrderId(),
                userHandler.getUserNameById(order.getUserId()),
                order.getOrderTime().format(TIME_FORMAT),
                order.getItemCount(),
                order.getTotalMoney(),
                order.getStatus().getLabel());
    }

    /**
     * 打印订单的收货地址
     *
     * @param order 要打印的订单
     */
    private void printAddress(Order order) {
        UserAddressInfo address = addressHandler.findById(order.getAddressId());
        if (address == null) {
            System.out.println("收货地址：地址已删除");
            return;
        }
        System.out.println("收货地址：" + address.getReceiver() + "，" + address.getTelephone()
                + "，" + address.getAddressInfo());
    }

    /**
     * 打印订单里每一种商品的名称、单价、数量和小计
     *
     * @param order 要打印的订单
     */
    private void printOrderItems(Order order) {
        MenuUtil.printLine();
        System.out.printf("%-15s %-10s %-8s %s%n", "商品名称", "单价", "数量", "小计");
        for (OrderItem item : order.getItems()) {
            System.out.printf("%-15s %-10.2f %-8d %.2f%n",
                    item.getGoodsName(), item.getUnitPrice(), item.getNum(), item.getSubTotal());
        }
    }

    /**
     * 打印订单的合计金额
     *
     * @param order 要打印的订单
     */
    private void printTotal(Order order) {
        MenuUtil.printLine();
        System.out.printf("订单总金额：%.2f%n", order.getTotalMoney());
    }
}

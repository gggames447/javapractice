package practicalpractice8.view;

import practicalpractice8.entity.Order;
import practicalpractice8.entity.OrderItem;
import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.handler.AddressHandler;
import practicalpractice8.handler.UserHandler;
import practicalpractice8.utils.menu.MenuUtil;

import java.time.format.DateTimeFormatter;

/**
 * 显示订单列表和订单详情
 */
public class OrderView extends TableView<Order> {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final UserHandler userHandler;
    private final AddressHandler addressHandler;

    /**
     * @param userHandler 用户数据，用来根据 userId 查出用户名
     * @param addressHandler 地址数据，订单详情里用来显示收货地址
     */
    public OrderView(UserHandler userHandler, AddressHandler addressHandler) {
        this.userHandler = userHandler;
        this.addressHandler = addressHandler;
    }

    /**
     * 打印订单详情：订单概要、收货地址、每一种商品的单价、数量和小计，以及合计
     *
     * @param order 要显示的订单
     */
    public void printDetail(Order order) {
        printOne(order);
        printAddress(order);
        printOrderItems(order);
        printTotal(order);
    }

    @Override
    protected void printColumnNames() {
        System.out.printf("%-8s %-10s %-18s %-6s %-10s %s%n", "订单号", "用户", "下单时间", "种数", "总金额", "状态");
    }

    @Override
    protected void printRow(Order order) {
        System.out.printf("%-8s %-10s %-18s %-6d %-10.2f %s%n",
                order.getOrderId(),
                userHandler.getUserNameById(order.getUserId()),
                order.getOrderTime().format(TIME_FORMAT),
                order.getItemCount(),
                order.getTotalMoney(),
                order.getStatus().getLabel());
    }

    private void printAddress(Order order) {
        UserAddressInfo address = addressHandler.findById(order.getAddressId());
        if (address == null) {
            System.out.println("收货地址：地址已删除");
            return;
        }
        System.out.println("收货地址：" + AddressView.describe(address));
    }

    private void printOrderItems(Order order) {
        MenuUtil.printLine();
        System.out.printf("%-15s %-10s %-8s %s%n", "商品名称", "单价", "数量", "小计");
        for (OrderItem item : order.getItems()) {
            System.out.printf("%-15s %-10.2f %-8d %.2f%n",
                    item.getGoodsName(), item.getUnitPrice(), item.getNum(), item.getSubTotal());
        }
    }

    private void printTotal(Order order) {
        MenuUtil.printLine();
        System.out.printf("订单总金额：%.2f%n", order.getTotalMoney());
    }
}

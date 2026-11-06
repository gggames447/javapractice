package practicalpractice6.view;

import practicalpractice6.entity.Order;
import practicalpractice6.entity.OrderItem;
import practicalpractice6.handler.UserHandler;
import practicalpractice6.utils.menu.MenuUtil;

import java.time.format.DateTimeFormatter;

/**
 * 显示订单列表和订单详情
 */
public class OrderView extends TableView<Order> {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final UserHandler userHandler;

    /**
     * @param userHandler 用户数据，用来根据 userId 查出用户名
     */
    public OrderView(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    /**
     * 打印订单详情：订单概要、每一种商品的单价、数量和小计，以及合计
     *
     * @param order 要显示的订单
     */
    public void printDetail(Order order) {
        printOne(order);
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

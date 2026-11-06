package practicalpractice7.controller;

import practicalpractice7.entity.Goods;
import practicalpractice7.entity.Order;
import practicalpractice7.entity.OrderItem;
import practicalpractice7.entity.UserAccount;
import practicalpractice7.enums.OrderStatus;
import practicalpractice7.handler.GoodsHandler;
import practicalpractice7.handler.OrderHandler;
import practicalpractice7.handler.UserHandler;
import practicalpractice7.utils.exception.ExceptionLogger;
import practicalpractice7.utils.exception.InputException;
import practicalpractice7.utils.exception.OrderStatusException;
import practicalpractice7.utils.exception.StockNotEnoughException;
import practicalpractice7.utils.input.InputUtil;
import practicalpractice7.utils.menu.BaseMenu;
import practicalpractice7.utils.menu.MenuUtil;

import java.util.Scanner;

/**
 * 订单菜单：下单、查询订单、修改订单状态
 */
public class OrderUI extends BaseMenu {
    private static final String FINISH_CODE = "0";

    private final OrderHandler orderHandler;
    private final UserHandler userHandler;
    private final GoodsHandler goodsHandler;

    /**
     * @param scanner 键盘输入
     * @param orderHandler 订单数据
     * @param userHandler 用户数据，下单和按用户查询时用来找到用户
     * @param goodsHandler 商品数据，下单时用来选择商品
     */
    public OrderUI(Scanner scanner, OrderHandler orderHandler, UserHandler userHandler, GoodsHandler goodsHandler) {
        super(scanner);
        this.orderHandler = orderHandler;
        this.userHandler = userHandler;
        this.goodsHandler = goodsHandler;
    }

    @Override
    protected void printMenu() {
        MenuUtil.printOrderMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> orderHandler.queryAll();
            case 2 -> queryByUser();
            case 3 -> orderHandler.showDetail(InputUtil.getString(scanner, "请输入订单号："));
            case 4 -> changeStatus();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 下单流程：选择用户，循环添加商品，最后提交订单
     */
    public void placeOrder() {
        System.out.println("--- 订单下单 ---");
        UserAccount user = askUser();
        if (user == null) {
            return;
        }

        Order order = new Order(orderHandler.nextOrderId(), user.getUserId());
        addItemsTo(order);
        if (order.getItemCount() == 0) {
            System.out.println("没有选择商品，已取消下单。");
            return;
        }

        try {
            orderHandler.placeOrder(order);
            System.out.println("下单成功！订单号：" + order.getOrderId());
            orderHandler.showDetail(order.getOrderId());
        } catch (StockNotEnoughException e) {
            ExceptionLogger.log(e);
            System.out.println("下单失败：" + e.getMessage());
        }
    }

    /**
     * 输入用户名并找到对应的用户
     *
     * @return 找到返回该用户，找不到时提示并返回 null
     */
    private UserAccount askUser() {
        String userName = InputUtil.getString(scanner, "请输入下单用户名：");
        UserAccount user = userHandler.findByName(userName);
        if (user == null) {
            System.out.println("用户不存在，下单失败！");
        }
        return user;
    }

    /**
     * 循环选择商品和数量加入订单，输入 0 结束。
     * 某一种商品输入有误时只跳过这一种，不影响已经加入的商品。
     *
     * @param order 正在创建的订单
     */
    private void addItemsTo(Order order) {
        goodsHandler.queryAll();
        while (true) {
            String goodsName = InputUtil.getString(scanner, "请输入商品名称（输入 0 结束）：");
            if (goodsName.equals(FINISH_CODE)) {
                return;
            }
            try {
                addOneItem(order, goodsName);
            } catch (InputException e) {
                ExceptionLogger.log(e);
                System.out.println("输入错误：" + e.getMessage());
            }
        }
    }

    /**
     * 把一种商品加入订单
     *
     * @param order 正在创建的订单
     * @param goodsName 商品名称
     * @throws InputException 数量不是数字、小于等于 0，或订单已满时
     */
    private void addOneItem(Order order, String goodsName) {
        Goods goods = goodsHandler.findByName(goodsName);
        if (goods == null) {
            System.out.println("商品不存在，请重新输入。");
            return;
        }
        if (containsGoods(order, goods)) {
            System.out.println("该商品已在订单中，请选择其他商品。");
            return;
        }
        int num = InputUtil.getInt(scanner, "请输入购买数量：");
        if (num > goods.getGoodStock()) {
            System.out.println("库存不足，当前库存 " + goods.getGoodStock() + "，请重新选择。");
            return;
        }
        order.addItem(goods, num);
        System.out.printf("已加入：%s × %d，当前订单金额 %.2f%n", goods.getGoodName(), num, order.getTotalMoney());
    }

    /**
     * 判断订单里是否已经有这种商品，防止同一种商品分两次加入、绕过库存检查
     *
     * @param order 订单
     * @param goods 商品
     * @return 已经有了返回 true
     */
    private boolean containsGoods(Order order, Goods goods) {
        for (OrderItem item : order.getItems()) {
            if (item.getGoodsId().equals(goods.getGoodId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 输入用户名，查看该用户的全部订单
     */
    private void queryByUser() {
        String userName = InputUtil.getString(scanner, "请输入用户名：");
        UserAccount user = userHandler.findByName(userName);
        if (user == null) {
            System.out.println("用户不存在");
            return;
        }
        orderHandler.queryByUser(user.getUserId());
    }

    /**
     * 输入订单号和新状态，修改订单状态
     */
    private void changeStatus() {
        String orderId = InputUtil.getString(scanner, "请输入订单号：");
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println("【" + (i + 1) + "】" + statuses[i].getLabel());
        }
        int choice = InputUtil.getInt(scanner, "请选择新状态：");
        if (choice < 1 || choice > statuses.length) {
            System.out.println("无效编号！");
            return;
        }
        try {
            orderHandler.changeStatus(orderId, statuses[choice - 1]);
        } catch (OrderStatusException e) {
            ExceptionLogger.log(e);
            System.out.println("修改失败：" + e.getMessage());
        }
    }
}

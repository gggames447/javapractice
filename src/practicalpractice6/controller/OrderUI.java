package practicalpractice6.controller;

import practicalpractice6.entity.Goods;
import practicalpractice6.entity.Order;
import practicalpractice6.entity.UserAccount;
import practicalpractice6.enums.OrderStatus;
import practicalpractice6.handler.GoodsHandler;
import practicalpractice6.handler.OrderHandler;
import practicalpractice6.handler.UserHandler;
import practicalpractice6.utils.exception.BusinessException;
import practicalpractice6.utils.exception.InputException;
import practicalpractice6.utils.input.InputUtil;
import practicalpractice6.utils.menu.BaseMenu;
import practicalpractice6.utils.menu.MenuUtil;
import practicalpractice6.view.GoodsView;
import practicalpractice6.view.OrderView;

import java.util.Scanner;

/**
 * 订单菜单：下单、查询订单、修改订单状态
 */
public class OrderUI extends BaseMenu {
    private static final String FINISH_CODE = "0";

    private final OrderHandler orderHandler;
    private final UserHandler userHandler;
    private final GoodsHandler goodsHandler;
    private final OrderView orderView;
    private final GoodsView goodsView = new GoodsView();

    /**
     * @param scanner 键盘输入
     * @param orderHandler 订单数据
     * @param userHandler 用户数据，下单和查询时用来找到用户
     * @param goodsHandler 商品数据，下单时用来选择商品
     */
    public OrderUI(Scanner scanner, OrderHandler orderHandler, UserHandler userHandler, GoodsHandler goodsHandler) {
        super(scanner);
        this.orderHandler = orderHandler;
        this.userHandler = userHandler;
        this.goodsHandler = goodsHandler;
        this.orderView = new OrderView(userHandler);
    }

    @Override
    protected void printMenu() {
        MenuUtil.printOrderMenu();
    }

    @Override
    protected void handleChoice(int choice) {
        switch (choice) {
            case 1 -> orderView.printTable(orderHandler.findAll(), "暂无数据。");
            case 2 -> queryByUser();
            case 3 -> showDetail();
            case 4 -> changeStatus();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 下单流程：选择用户，循环添加商品，最后提交订单
     */
    public void placeOrder() {
        System.out.println("--- 订单下单 ---");
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入下单用户名："));

        Order order = new Order(orderHandler.nextOrderId(), user.getUserId());
        addItemsTo(order);
        if (order.getItemCount() == 0) {
            System.out.println("没有选择商品，已取消下单。");
            return;
        }

        orderHandler.placeOrder(order);
        System.out.println("下单成功！订单号：" + order.getOrderId());
        orderView.printDetail(order);
    }

    /**
     * 循环选择商品和数量加入订单，输入 0 结束。
     * 某一种商品输入有误时只跳过这一种，不影响已经加入的商品。
     *
     * @param order 正在创建的订单
     */
    private void addItemsTo(Order order) {
        goodsView.printTable(goodsHandler.findAll(), "暂无商品。");
        while (true) {
            String goodsName = InputUtil.getString(scanner, "请输入商品名称（输入 0 结束）：");
            if (goodsName.equals(FINISH_CODE)) {
                return;
            }
            try {
                addOneItem(order, goodsName);
            } catch (BusinessException e) {
                System.out.println("无法加入：" + e.getMessage());
            }
        }
    }

    /**
     * 把一种商品加入订单
     *
     * @param order 正在创建的订单
     * @param goodsName 商品名称
     * @throws BusinessException 商品不存在或已在订单中，或数量超过当前库存时
     * @throws InputException 数量不是整数、小于等于 0，或订单已满时
     */
    private void addOneItem(Order order, String goodsName) {
        Goods goods = goodsHandler.findByName(goodsName);
        if (goods == null) {
            throw new BusinessException("商品「" + goodsName + "」不存在");
        }
        // 同一种商品分两次加入会绕过库存检查，所以不允许重复添加
        if (order.containsGoods(goods.getGoodsId())) {
            throw new BusinessException("该商品已在订单中");
        }
        int num = InputUtil.getInt(scanner, "请输入购买数量：");
        // 这里提前检查是为了马上提示用户；提交时 OrderHandler 还会再检查一次
        if (num > goods.getStock()) {
            throw new BusinessException("库存不足，当前库存 " + goods.getStock());
        }
        order.addItem(goods, num);
        System.out.printf("已加入：%s × %d，当前订单金额 %.2f%n", goods.getGoodsName(), num, order.getTotalMoney());
    }

    /**
     * 输入用户名，查看该用户的全部订单
     */
    private void queryByUser() {
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入用户名："));
        orderView.printTable(orderHandler.findByUser(user.getUserId()), "该用户暂无订单。");
    }

    private void showDetail() {
        String orderId = InputUtil.getString(scanner, "请输入订单号：");
        orderView.printDetail(orderHandler.getOrder(orderId));
    }

    /**
     * 输入订单号和新状态，修改订单状态
     */
    private void changeStatus() {
        String orderId = InputUtil.getString(scanner, "请输入订单号：");
        OrderStatus[] statuses = OrderStatus.values();
        String[] options = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            options[i] = statuses[i].getLabel();
        }
        OrderStatus newStatus = statuses[chooseIndex(options, "请选择新状态：")];
        orderHandler.changeStatus(orderId, newStatus);
        System.out.println("订单状态已改为：" + newStatus.getLabel());
    }
}

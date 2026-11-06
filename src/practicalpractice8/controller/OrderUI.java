package practicalpractice8.controller;

import practicalpractice8.entity.Goods;
import practicalpractice8.entity.Order;
import practicalpractice8.entity.UserAccount;
import practicalpractice8.entity.UserAddressInfo;
import practicalpractice8.enums.OrderStatus;
import practicalpractice8.handler.AddressHandler;
import practicalpractice8.handler.GoodsHandler;
import practicalpractice8.handler.OrderHandler;
import practicalpractice8.handler.UserHandler;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.exception.ExceptionLogger;
import practicalpractice8.utils.exception.InputException;
import practicalpractice8.utils.exception.StockNotEnoughException;
import practicalpractice8.utils.input.InputUtil;
import practicalpractice8.utils.menu.BaseMenu;
import practicalpractice8.utils.menu.MenuUtil;
import practicalpractice8.view.AddressView;
import practicalpractice8.view.GoodsView;
import practicalpractice8.view.OrderView;

import java.util.Scanner;

/**
 * 订单菜单：下单、查询订单、修改状态、取消订单、统计用户消费
 */
public class OrderUI extends BaseMenu {
    private static final String FINISH_CODE = "0";

    private final OrderHandler orderHandler;
    private final UserHandler userHandler;
    private final GoodsHandler goodsHandler;
    private final AddressHandler addressHandler;
    private final OrderView orderView;
    private final GoodsView goodsView = new GoodsView();

    /**
     * @param scanner 键盘输入
     * @param orderHandler 订单数据
     * @param userHandler 用户数据，下单和查询时用来找到用户
     * @param goodsHandler 商品数据，下单时用来选择商品
     * @param addressHandler 地址数据，下单时用来选择收货地址
     */
    public OrderUI(Scanner scanner, OrderHandler orderHandler, UserHandler userHandler,
                   GoodsHandler goodsHandler, AddressHandler addressHandler) {
        super(scanner);
        this.orderHandler = orderHandler;
        this.userHandler = userHandler;
        this.goodsHandler = goodsHandler;
        this.addressHandler = addressHandler;
        this.orderView = new OrderView(userHandler, addressHandler);
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
            case 5 -> cancelOrder();
            case 6 -> showTotalSpending();
            default -> System.out.println("无效编号！");
        }
    }

    /**
     * 下单流程：选择用户和收货地址，循环添加商品，最后提交订单
     */
    public void placeOrder() {
        System.out.println("--- 订单下单 ---");
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入下单用户名："));
        UserAddressInfo address = chooseAddress(user);

        Order order = new Order(orderHandler.nextOrderId(), user.getUserId(), address.getAddressId());
        addItemsTo(order);
        if (order.getItemCount() == 0) {
            System.out.println("没有选择商品，已取消下单。");
            return;
        }

        try {
            orderHandler.placeOrder(order);
            System.out.println("下单成功！订单号：" + order.getOrderId());
            orderView.printDetail(order);
        } catch (StockNotEnoughException e) {
            ExceptionLogger.log(e);
            System.out.println("下单失败：" + e.getMessage());
        }
    }

    /**
     * 列出用户的全部收货地址，让用户选择一个
     *
     * @param user 下单用户
     * @return 选中的地址
     * @throws BusinessException 用户还没有收货地址时
     * @throws InputException 输入的地址编号不在范围内时
     */
    private UserAddressInfo chooseAddress(UserAccount user) {
        UserAddressInfo[] addresses = addressHandler.findByUser(user.getUserId());
        if (addresses.length == 0) {
            throw new BusinessException("用户「" + user.getUserName() + "」还没有收货地址，请先在【收货地址管理】中添加");
        }
        String[] options = new String[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            options[i] = AddressView.describe(addresses[i]);
        }
        return addresses[chooseIndex(options, "请选择收货地址：")];
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
                ExceptionLogger.log(e);
                System.out.println("无法加入：" + e.getMessage());
            }
        }
    }

    /**
     * 把一种商品加入订单
     *
     * @param order 正在创建的订单
     * @param goodsName 商品名称
     * @throws BusinessException 商品不存在或已在订单中时
     * @throws InputException 数量不是整数、小于等于 0，或订单已满时
     * @throws StockNotEnoughException 数量超过当前库存时
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
            throw new StockNotEnoughException("库存不足，当前库存 " + goods.getStock());
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

    /**
     * 输入订单号取消订单，库存会自动回补
     */
    private void cancelOrder() {
        String orderId = InputUtil.getString(scanner, "请输入要取消的订单号：");
        orderHandler.cancelOrder(orderId);
        System.out.println("订单已取消，商品库存已回补。");
    }

    /**
     * 输入用户名，统计该用户的总消费（不含已取消的订单）
     */
    private void showTotalSpending() {
        UserAccount user = userHandler.getByName(InputUtil.getString(scanner, "请输入用户名："));
        System.out.printf("用户「%s」的总消费：%.2f（不含已取消的订单）%n",
                user.getUserName(), orderHandler.totalMoneyByUser(user.getUserId()));
    }
}

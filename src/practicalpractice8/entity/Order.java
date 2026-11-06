package practicalpractice8.entity;

import practicalpractice8.enums.OrderStatus;
import practicalpractice8.utils.exception.InputException;
import practicalpractice8.utils.exception.OrderStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 订单：一个订单包含多个订单项（一对多），并关联下单用户和收货地址
 */
public class Order extends BaseEntity {
    private static final int MAX_ITEMS = 10;

    private final String userId;
    private final String addressId;
    private final OrderItem[] items = new OrderItem[MAX_ITEMS];
    private int itemCount = 0;
    private double totalMoney = 0;
    private OrderStatus status = OrderStatus.UNPAID;

    /**
     * 创建一个空订单，状态为待付款
     *
     * @param orderId 订单号，由 OrderHandler 生成
     * @param userId 下单用户的 ID
     * @param addressId 收货地址的 ID
     */
    public Order(String orderId, String userId, String addressId) {
        super(orderId);
        this.userId = userId;
        this.addressId = addressId;
    }

    /**
     * 往订单里加一种商品，同时累加总金额。
     * 库存是否足够由 OrderHandler 在提交订单时检查。
     *
     * @param goods 购买的商品
     * @param num 购买数量
     * @throws InputException 订单项已满或数量不合法时
     */
    public void addItem(Goods goods, int num) {
        if (itemCount == items.length) {
            throw new InputException("一个订单最多 " + MAX_ITEMS + " 种商品");
        }
        OrderItem item = new OrderItem(getId(), goods, num);
        items[itemCount] = item;
        itemCount++;
        totalMoney += item.getSubTotal();
    }

    /**
     * 判断订单里是否已经有某种商品
     *
     * @param goodsId 商品编号
     * @return 已经有了返回 true
     */
    public boolean containsGoods(String goodsId) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getGoodsId().equals(goodsId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return 订单里所有的订单项（返回副本，外面改不了订单内部的数组）
     */
    public OrderItem[] getItems() {
        return Arrays.copyOf(items, itemCount);
    }

    public int getItemCount() {
        return itemCount;
    }

    public String getOrderId() {
        return getId();
    }

    public String getUserId() {
        return userId;
    }

    public String getAddressId() {
        return addressId;
    }

    /**
     * @return 下单时间，就是父类记录的创建时间
     */
    public LocalDateTime getOrderTime() {
        return getCreateTime();
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public OrderStatus getStatus() {
        return status;
    }

    /**
     * 修改订单状态，只允许按 OrderStatus.canChangeTo 规定的顺序修改
     *
     * @param newStatus 新状态
     * @throws OrderStatusException 不允许从当前状态改为新状态时
     */
    public void setStatus(OrderStatus newStatus) {
        if (!status.canChangeTo(newStatus)) {
            throw new OrderStatusException("订单状态不能从「" + status.getLabel()
                    + "」改为「" + newStatus.getLabel() + "」");
        }
        this.status = newStatus;
    }
}

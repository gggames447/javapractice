package practicalpractice6.handler;

import practicalpractice6.entity.Goods;
import practicalpractice6.entity.Order;
import practicalpractice6.entity.OrderItem;
import practicalpractice6.enums.OrderStatus;
import practicalpractice6.utils.exception.BusinessException;
import practicalpractice6.utils.exception.InputException;

import java.util.Arrays;

/**
 * 管理订单：生成订单号、下单（检查并扣减库存）、查询和修改状态。
 * 存储、新增、取得全部、按 ID 删除由父类 BaseHandler 提供。
 */
public class OrderHandler extends BaseHandler<Order> {
    private final GoodsHandler goodsHandler;
    private int nextOrderNo = 1;

    /**
     * @param goodsHandler 商品数据，下单时用来检查和扣减库存
     */
    public OrderHandler(GoodsHandler goodsHandler) {
        super(new Order[MAX_SIZE]);
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
     * @throws InputException 订单里没有商品时
     * @throws BusinessException 订单数量已达上限，或有商品不存在或库存不足时
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
     * 修改订单状态
     *
     * @param orderId 订单号
     * @param newStatus 新状态
     * @throws BusinessException 找不到订单，或不允许改为新状态时
     */
    public void changeStatus(String orderId, OrderStatus newStatus) {
        getOrder(orderId).setStatus(newStatus);
    }

    /**
     * 查出某个用户的全部订单
     *
     * @param userId 用户 ID
     * @return 该用户的订单，一个都没有时长度为 0
     */
    public Order[] findByUser(String userId) {
        Order[] result = new Order[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (array[i].getUserId().equals(userId)) {
                result[found] = array[i];
                found++;
            }
        }
        return Arrays.copyOf(result, found);
    }

    /**
     * 按订单号取得订单，找不到时直接报错
     *
     * @param orderId 订单号
     * @return 找到的订单
     * @throws BusinessException 找不到订单时
     */
    public Order getOrder(String orderId) {
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
     * @throws BusinessException 有商品不存在或库存不足时
     */
    private void checkStock(Order order) {
        for (OrderItem item : order.getItems()) {
            Goods goods = goodsHandler.findById(item.getGoodsId());
            if (goods == null) {
                throw new BusinessException("商品「" + item.getGoodsName() + "」已不存在");
            }
            if (goods.getStock() < item.getNum()) {
                throw new BusinessException("商品「" + goods.getGoodsName() + "」库存不足，当前库存 "
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

}

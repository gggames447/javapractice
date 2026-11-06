package practicalpractice7.entity;

import practicalpractice7.utils.exception.InputException;

/**
 * 订单项：订单里的一种商品及其购买数量
 */
public class OrderItem extends BaseEntity {
    private final String orderId;
    private final String goodsId;
    private final String goodsName;
    private final double unitPrice;
    private final int num;

    /**
     * 创建订单项，自动生成 UUID 作为订单项 ID
     *
     * @param orderId 所属订单的 ID
     * @param goods 购买的商品
     * @param num 购买数量，必须大于 0
     * @throws InputException 数量小于等于 0 时
     */
    public OrderItem(String orderId, Goods goods, int num) {
        super();
        if (num <= 0) {
            throw new InputException("购买数量必须大于 0");
        }
        this.orderId = orderId;
        this.goodsId = goods.getGoodId();
        this.goodsName = goods.getGoodName();
        this.unitPrice = goods.getGoodPrice();
        this.num = num;
    }

    /**
     * @return 单项小计 = 单价 × 数量
     */
    public double getSubTotal() {
        return unitPrice * num;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public int getNum() {
        return num;
    }
}
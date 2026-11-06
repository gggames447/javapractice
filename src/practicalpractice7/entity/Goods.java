package practicalpractice7.entity;

import practicalpractice7.utils.exception.InputException;

/**
 * 商品
 */
public class Goods extends BaseEntity {
    private String goodsName;
    private double price;
    private int stock;
    private String cateId;

    /**
     * 创建一个商品，名称、单价和库存会经过 setter 校验
     *
     * @param goodsId 商品编号
     * @param goodsName 商品名称，不能为空
     * @param price 单价，必须大于 0
     * @param stock 库存，不能为负数
     * @param cateId 所属分类编号
     * @throws InputException 名称、单价或库存不合法时
     */
    public Goods(String goodsId, String goodsName, double price, int stock, String cateId) {
        super(goodsId);
        setGoodsName(goodsName);
        setPrice(price);
        setStock(stock);
        this.cateId = cateId;
    }

    public String getGoodsId() {
        return getId();
    }

    public String getGoodsName() {
        return goodsName;
    }

    /**
     * @param goodsName 商品名称
     * @throws InputException 名称为 null 或只有空格时
     */
    public void setGoodsName(String goodsName) {
        if (goodsName == null || goodsName.trim().isEmpty()) {
            throw new InputException("商品名称不能为空");
        }
        this.goodsName = goodsName;
    }

    public double getPrice() {
        return price;
    }

    /**
     * @param price 单价
     * @throws InputException 单价小于等于 0 时
     */
    public void setPrice(double price) {
        if (price <= 0) {
            throw new InputException("单价必须大于 0");
        }
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    /**
     * @param stock 库存
     * @throws InputException 库存为负数时
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new InputException("库存不能为负数");
        }
        this.stock = stock;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
}

package practicalpractice6.entity;

import practicalpractice6.utils.exception.InputException;

/**
 * 商品
 */
public class Goods extends BaseEntity{
    private String goodName;
    private double goodPrice;
    private int goodStock;
    private String cateId;


    /**
     * 创建一个商品，单价和库存会经过 setter 校验
     *
     * @param goodId 商品编号
     * @param goodName 商品名称
     * @param goodPrice 单价，必须大于 0
     * @param goodStock 库存，不能为负数
     * @param cateId 所属分类编号
     * @throws InputException 单价或库存不合法时
     */
    public Goods(String goodId, String goodName, double goodPrice, int goodStock, String cateId) {
        super(goodId);
        this.goodName = goodName;
        setGoodPrice(goodPrice);
        setGoodStock(goodStock);
        this.cateId = cateId;
    }

    public String getGoodId() {
        return getId();
    }


    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    /**
     * 设置单价
     *
     * @param goodPrice 单价
     * @throws InputException 单价小于等于 0 时
     */
    public void setGoodPrice(double goodPrice) {
        if (goodPrice <= 0) {
            throw new InputException("单价必须大于 0");
        }
        this.goodPrice = goodPrice;
    }

    public int getGoodStock() {
        return goodStock;
    }

    /**
     * 设置库存
     *
     * @param goodStock 库存
     * @throws InputException 库存为负数时
     */
    public void setGoodStock(int goodStock) {
        if (goodStock < 0) {
            throw new InputException("库存不能为负数");
        }
        this.goodStock = goodStock;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

}

package practicalpractice4.goods;

/**
 * 商品
 */
public class Goods {
    private final String goodId;
    private final String goodName;
    private final double goodPrice;
    private final int goodStock;
    private final String cateId;

    /**
     * 创建一个商品，单价和库存是否合法由 GoodsHandler 在新增时检查
     *
     * @param goodId 商品编号
     * @param goodName 商品名称
     * @param goodPrice 单价
     * @param goodStock 库存
     * @param cateId 所属分类编号
     */
    public Goods(String goodId, String goodName, double goodPrice, int goodStock, String cateId) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.goodPrice = goodPrice;
        this.goodStock = goodStock;
        this.cateId = cateId;
    }

    public String getGoodId() {
        return goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public double getGoodPrice() {
        return goodPrice;
    }

    public int getGoodStock() {
        return goodStock;
    }

    public String getCateId() {
        return cateId;
    }
}

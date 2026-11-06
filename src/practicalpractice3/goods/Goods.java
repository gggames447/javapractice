package practicalpractice3.goods;

// 商品
public class Goods {
    private final String goodId;
    private final String goodName;
    private final double goodPrice;
    private final int goodStock;
    private final String cateId;

    // 单价和库存是否合法由 GoodsHandler 在新增时检查
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

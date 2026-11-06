package practicalpractice4.category;

/**
 * 商品分类
 */
public class Category {
    private final String cateId;
    private final String cateName;
    private final String cateDesc;

    /**
     * 创建一个分类
     *
     * @param cateId 分类编号
     * @param cateName 分类名称
     * @param cateDesc 分类描述
     */
    public Category(String cateId, String cateName, String cateDesc) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.cateDesc = cateDesc;
    }

    public String getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }
}

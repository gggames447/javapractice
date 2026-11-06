package practicalpractice3.category;

// 商品分类
public class Category {
    private final String cateId;
    private final String cateName;
    private final String cateDesc;

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

package practicalpractice6.entity;

import practicalpractice6.utils.exception.InputException;

/**
 * 商品分类
 */
public class Category extends BaseEntity {
    private String cateName;
    private String cateDesc;

    /**
     * 创建一个分类，分类名称会经过 setter 校验
     *
     * @param cateId 分类编号
     * @param cateName 分类名称，不能为空
     * @param cateDesc 分类描述
     * @throws InputException 分类名称为空时
     */
    public Category(String cateId, String cateName, String cateDesc) {
        super(cateId);
        setCateName(cateName);
        this.cateDesc = cateDesc;
    }

    public String getCateId() {
        return getId();
    }

    public String getCateName() {
        return cateName;
    }

    /**
     * @param cateName 分类名称
     * @throws InputException 名称为 null 或只有空格时
     */
    public void setCateName(String cateName) {
        if (cateName == null || cateName.trim().isEmpty()) {
            throw new InputException("分类名称不能为空");
        }
        this.cateName = cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }
}

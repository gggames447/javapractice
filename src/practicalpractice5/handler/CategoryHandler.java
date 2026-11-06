package practicalpractice5.handler;

import practicalpractice5.category.Category;
import practicalpractice5.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 管理商品分类，用数组存储，最多 20 个。只管数据，不打印，显示交给 CategoryView。
 */
public class CategoryHandler {
    private static final int MAX_SIZE = 20;

    // 有效分类的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final Category[] categories = new Category[MAX_SIZE];

    /**
     * 判断分类编号是否存在，新增商品时用来校验分类
     *
     * @param id 分类编号
     * @return 存在返回 true，不存在返回 false
     */
    public boolean existsById(String id) {
        for (int i = 0; i < count; i++) {
            if (categories[i].getCateId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 新增分类，编号和名称都不能重复
     *
     * @param newCategory 要新增的分类
     * @throws BusinessException 存储已满，或编号、名称已存在时
     */
    public void addCategory(Category newCategory) {
        if (count == categories.length) {
            throw new BusinessException("分类存储已满，无法新增");
        }
        for (int i = 0; i < count; i++) {
            if (newCategory.getCateId().equals(categories[i].getCateId())) {
                throw new BusinessException("分类编号「" + newCategory.getCateId() + "」已存在");
            }
            if (newCategory.getCateName().equals(categories[i].getCateName())) {
                throw new BusinessException("分类名称「" + newCategory.getCateName() + "」已存在");
            }
        }
        categories[count] = newCategory;
        count++;
    }

    /**
     * @return 全部分类的副本，没有分类时长度为 0
     */
    public Category[] findAll() {
        return Arrays.copyOf(categories, count);
    }
}

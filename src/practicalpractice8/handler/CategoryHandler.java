package practicalpractice8.handler;

import practicalpractice8.entity.Category;
import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.menu.MenuUtil;

/**
 * 管理商品分类
 */
public class CategoryHandler extends BaseHandler<Category> {

    public CategoryHandler() {
        super(new Category[MAX_SIZE]);
    }

    /**
     * 新增分类时，除了父类检查的容量和编号，分类名称也不能重复
     *
     * @param item 要新增的分类
     * @throws BusinessException 分类名称已存在时
     */
    @Override
    protected void validateBeforeAdd(Category item) {
        for (int i = 0; i < count; i++) {
            if (array[i].getCateName().equals(item.getCateName())) {
                throw new BusinessException("分类名称「" + item.getCateName() + "」已存在");
            }
        }
    }

    @Override
    protected void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-20s %s%n", "分类编号", "分类名称", "分类描述");
        MenuUtil.printLine();
    }

    @Override
    protected void printItem(Category item) {
        System.out.printf("%-10s %-20s %s%n", item.getCateId(), item.getCateName(), item.getCateDesc());
    }
}

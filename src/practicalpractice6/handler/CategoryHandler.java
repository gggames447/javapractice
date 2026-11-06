package practicalpractice6.handler;

import practicalpractice6.entity.Category;

/**
 * 管理商品分类，用数组存储，最多 20 个
 */
public class CategoryHandler extends BaseHandler<Category> {

    public CategoryHandler() {
        super(new Category[MAX_SIZE]);
    }

    @Override
    protected boolean checkBeforeAdd(Category item) {
        for (int i = 0; i < count; i++) {
            if (array[i].getCateName().equals(item.getCateName())) {
                System.out.println("分类名称已存在，新增失败！");
                return false;
            }
        }
        return true;
    }
    /**
     * 判断分类编号是否存在，新增商品时用来校验分类
     *
     * @param id 分类编号
     * @return 存在返回 true，不存在返回 false
     */
    public boolean existsById(String id) {
        return findIndexById(id) != -1;
    }

    /**
     * 打印表头，由子类决定有哪些列
     */
    @Override
    protected void printHeader() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-20s\n", "分类编号", "分类名称", "分类描述");
        System.out.println("------------------------------------------------------------");
    }

    /**
     * 打印一行数据，由子类决定格式
     *
     * @param item 要打印的数据
     */
    @Override
    protected void printItem(Category item) {
        System.out.printf("%-10s %-20s %-20s\n",
                item.getCateId(), item.getCateName(), item.getCateDesc());
    }
}
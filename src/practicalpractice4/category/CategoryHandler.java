package practicalpractice4.category;

import practicalpractice4.utils.menu.MenuUtil;

/**
 * 管理商品分类，用数组存储，最多 20 个
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
     */
    public void addCategory(Category newCategory) {
        if (count == categories.length) {
            System.out.println("分类存储已满，无法新增！");
            return;
        }
        for (int i = 0; i < count; i++) {
            if (newCategory.getCateId().equals(categories[i].getCateId())) {
                System.out.println("分类编号已存在，新增失败！");
                return;
            }
            if (newCategory.getCateName().equals(categories[i].getCateName())) {
                System.out.println("分类名称已存在，新增失败！");
                return;
            }
        }
        categories[count] = newCategory;
        count++;
        System.out.println("分类添加成功！");
    }

    /**
     * 查看全部分类，没有分类时提示暂无数据
     */
    public void queryAll() {
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            printCategory(categories[i]);
        }
    }

    private void printHeader() {
        MenuUtil.printLine();
        System.out.printf("%-10s %-20s %s%n", "分类编号", "分类名称", "分类描述");
        MenuUtil.printLine();
    }

    private void printCategory(Category category) {
        System.out.printf("%-10s %-20s %s%n",
                category.getCateId(), category.getCateName(), category.getCateDesc());
    }
}

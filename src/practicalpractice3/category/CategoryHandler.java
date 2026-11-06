package practicalpractice3.category;

import practicalpractice3.utils.exception.BusinessException;
import practicalpractice3.utils.menu.MenuUtil;

// 管理商品分类，用数组存储，最多 20 个
public class CategoryHandler {
    private static final int MAX_SIZE = 20;

    // 有效分类的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    private int count = 0;
    private final Category[] categories = new Category[MAX_SIZE];

    // 判断分类编号是否存在，新增商品时用来校验分类
    public boolean existsById(String id) {
        for (int i = 0; i < count; i++) {
            if (categories[i].getCateId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // 新增分类，编号和名称都不能重复，不满足时抛出异常，由菜单提示
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

    // 查看全部分类，没有分类时提示暂无数据
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

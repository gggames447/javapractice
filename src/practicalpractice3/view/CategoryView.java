package practicalpractice3.view;

import practicalpractice3.category.Category;
import practicalpractice3.utils.menu.MenuUtil;

// 显示分类表格。CategoryHandler 只负责查数据，打印都在这里。
public class CategoryView {

    // 工具类，只提供静态方法，不需要创建对象
    private CategoryView() {}

    // 打印分类表格
    public static void printTable(Category[] categories, String emptyMessage) {
        if (categories.length == 0) {
            System.out.println(emptyMessage);
            return;
        }
        MenuUtil.printLine();
        System.out.printf("%-10s %-20s %s%n", "分类编号", "分类名称", "分类描述");
        MenuUtil.printLine();
        for (Category category : categories) {
            System.out.printf("%-10s %-20s %s%n",
                    category.getCateId(), category.getCateName(), category.getCateDesc());
        }
    }
}

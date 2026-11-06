package practicalpractice7.view;

import practicalpractice7.entity.Category;

/**
 * 显示分类表格
 */
public class CategoryView extends TableView<Category> {

    @Override
    protected void printColumnNames() {
        System.out.printf("%-10s %-20s %s%n", "分类编号", "分类名称", "分类描述");
    }

    @Override
    protected void printRow(Category item) {
        System.out.printf("%-10s %-20s %s%n", item.getCateId(), item.getCateName(), item.getCateDesc());
    }
}

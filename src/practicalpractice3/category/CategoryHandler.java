package practicalpractice3.category;

// 管理商品分类，用数组存储，最多 20 个
public class CategoryHandler {
    private static final int MAX_SIZE = 20;
    private int count = 0;
    private Category[] categories = new Category[MAX_SIZE];

    private void printHeader() {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-20s\n", "分类编号", "分类名称", "分类描述");
        System.out.println("------------------------------------------------------------");
    }

    private void printCategory(Category c) {
        System.out.printf("%-10s %-20s %-20s\n",
                c.getCateId(), c.getCateName(), c.getCateDesc());
    }

    // 判断分类编号是否存在，新增商品时用来校验分类
    public boolean existsById(String id) {
        for (int i = 0; i < count; i++) {
            if (categories[i].getCateId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // 新增分类，名称和编号都不能重复
    public void addCategory(Category newCategory) {
        if (count == categories.length) {
            System.out.println("仓库已满，无法新增！");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (newCategory.getCateName().equals(categories[i].getCateName())) {
                System.out.println("分类名称已存在，新增失败！");
                return;
            }
            if (newCategory.getCateId().equals(categories[i].getCateId())) {
                System.out.println("分类编号已存在，新增失败！");
                return;
            }
        }
        categories[count] = newCategory;
        count++;
        System.out.println("分类添加成功！");
    }

    // 查看全部分类
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
}
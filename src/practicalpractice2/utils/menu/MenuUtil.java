package practicalpractice2.utils.menu;

// 各级菜单的文字，以及表格统一使用的分隔线
public class MenuUtil {
    private static final String LINE = "-".repeat(60);

    // 工具类，只提供静态方法，不需要创建对象
    private MenuUtil() {}

    public static void printLine() {
        System.out.println(LINE);
    }

    public static void printMainMenu() {
        System.out.print("""
                ========== 电商订单管理系统 ==========
                【1】用户管理
                【2】商品分类管理
                【3】商品管理
                【4】收货地址管理
                【5】订单下单
                【6】订单查询与状态修改
                【0】退出系统
                ======================================
                请输入功能编号：""");
    }

    public static void printCategoryMenu() {
        System.out.print("""
                ============ 商品分类管理 ============
                【1】新增分类
                【2】查看所有分类
                【0】返回上级
                ======================================
                请输入功能编号：""");
    }

    public static void printGoodsMenu() {
        System.out.print("""
                ============== 商品管理 ==============
                【1】新增商品
                【2】查看全部商品
                【3】按价格区间查询
                【4】库存预警查询
                【0】返回上级
                ======================================
                请输入功能编号：""");
    }
}

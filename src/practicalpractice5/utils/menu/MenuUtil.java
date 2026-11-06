package practicalpractice5.utils.menu;

/**
 * 各级菜单的文字
 */
public class MenuUtil {
    /**
     * 打印商品分类管理菜单
     */
    public static void printCategoryMenu() {
        String menu = """
                =======商品分类管理系统=======
                【1】新增分类
                【2】查看所有分类
                【0】返回上级
                ===========================
                请输入功能编号：""";
        System.out.print(menu);
    }

    /**
     * 打印主菜单
     */
    public static void printMainMenu(){

        String mainMenu = """
                =======电商订单管理系统=======
                【1】用户管理
                【2】商品分类管理
                【3】商品管理
                【4】收货地址管理
                【5】订单下单
                【6】订单查询与状态修改
                【0】退出系统
                ============================
                请输入功能编号：""";

        System.out.print(mainMenu);
    }
    /**
     * 打印商品管理菜单
     */
    public static void printGoodsMenu() {
        String goodsMenu = """
        =======商品管理系统=======
        【1】新增商品
        【2】查看全部商品
        【3】按价格区间查询
        【4】库存预警查询
        【5】按名称查找商品
        【6】删除商品
        【7】按价格排序
        【8】模糊查询商品
        【9】库存与价格统计
        【0】返回上级
        ===========================
        请输入功能编号：""";
        System.out.print(goodsMenu);
    }
    /**
     * 打印价格排序菜单
     */
    public static void printSortMenu() {
        String sortMenu = """
        =======价格排序=======
        【1】价格升序
        【2】价格降序
        【0】返回
        =====================
        请选择排序方式：""";
        System.out.print(sortMenu);
    }
    /**
     * 打印用户管理菜单
     */
    public static void printUserMenu() {
        String userMenu = """
        =======用户管理系统=======
        【1】新增用户
        【2】查看全部用户
        【3】按用户名查找
        【0】返回上级
        ===========================
        请输入功能编号：""";
        System.out.print(userMenu);
    }
    /**
     * 打印收货地址管理菜单
     */
    public static void printAddressMenu(){
        String addressMenu = """
        =======地址管理系统=======
        【1】新增收货地址
        【2】查看全部地址
        【0】返回上级
        ===========================
        请输入功能编号：""";
        System.out.print(addressMenu);
    }
}

package practicalpractice2.utils.menu;

import java.util.Arrays;
import java.util.List;

public class MenuUtil {
    public static void printCategoryMenu() {
        String menu = """
                =======商品分类管理系统=======
                【1】新增分类
                【2】查看所有分类
                【0】返回上级
                ===========================
                """;
        System.out.print(menu);
    }

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
    public static void printGoodsMenu(){
        String goodsMenu = """
            =======商品管理系统=======
            【1】新增商品
            【2】查看全部商品
            【3】按价格区间查询
            【4】库存预警查询
            【0】返回上级
            ===========================
            请输入功能编号：""";
        System.out.print(goodsMenu);
    }
}

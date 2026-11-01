package practicalpractice4;

import practicalpractice4.category.CategoryHandler;
import practicalpractice4.controller.MainMenuUI;
import practicalpractice4.goods.GoodsHandler;
import practicalpractice4.services.LoginService;

import java.util.Scanner;

/**
 * 程序入口：先登录，登录成功再进主菜单
 */
public class EshopManage {

    /**
     * 程序从这里开始运行
     *
     * @param args 命令行参数（没有用到）
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        String currentUser = LoginService.login(scanner);


        if (currentUser != null) {

            // Handler 只在这里创建一次，进出子菜单时数据才不会丢
            CategoryHandler categoryHandler = new CategoryHandler();
            GoodsHandler goodsHandler = new GoodsHandler();


            MainMenuUI.showMenu(scanner, currentUser, categoryHandler, goodsHandler);
        }
    }
}
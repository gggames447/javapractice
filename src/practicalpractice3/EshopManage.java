package practicalpractice3;

import practicalpractice3.category.CategoryHandler;
import practicalpractice3.controller.MainMenuUI;
import practicalpractice3.goods.GoodsHandler;
import practicalpractice3.services.LoginService;

import java.util.Scanner;

// 程序入口：先登录，登录成功再进主菜单
public class EshopManage {

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

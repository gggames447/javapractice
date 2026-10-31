package practicalpractice2;

import practicalpractice2.category.CategoryHandler;
import practicalpractice2.goods.GoodsHandler;
import practicalpractice2.services.LoginService;
import practicalpractice2.controller.MainMenuUI;
import java.util.Scanner;

public class EshopManage {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        String currentUser = LoginService.login(scanner);


        if (currentUser != null) {

            CategoryHandler categoryHandler = new CategoryHandler();
            GoodsHandler goodsHandler = new GoodsHandler();


            MainMenuUI.showMenu(scanner, currentUser, categoryHandler, goodsHandler);
        }
    }
}
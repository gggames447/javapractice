package practicalpractice8;

import practicalpractice8.controller.MainMenuUI;
import practicalpractice8.handler.AddressHandler;
import practicalpractice8.handler.CategoryHandler;
import practicalpractice8.handler.GoodsHandler;
import practicalpractice8.handler.OrderHandler;
import practicalpractice8.handler.UserHandler;
import practicalpractice8.services.LoginService;

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
            UserHandler userHandler = new UserHandler();
            AddressHandler addressHandler = new AddressHandler(userHandler);
            OrderHandler orderHandler = new OrderHandler(userHandler, goodsHandler, addressHandler);

            new MainMenuUI(scanner, currentUser, categoryHandler, goodsHandler,
                    userHandler, addressHandler, orderHandler).show();
        }
    }
}

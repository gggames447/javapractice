package practicalpractice6;

import practicalpractice6.controller.MainMenuUI;
import practicalpractice6.handler.AddressHandler;
import practicalpractice6.handler.CategoryHandler;
import practicalpractice6.handler.GoodsHandler;
import practicalpractice6.handler.OrderHandler;
import practicalpractice6.handler.UserHandler;
import practicalpractice6.services.LoginService;

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
            AddressHandler addressHandler = new AddressHandler();
            OrderHandler orderHandler = new OrderHandler(goodsHandler);

            new MainMenuUI(scanner, currentUser, categoryHandler, goodsHandler,
                    userHandler, addressHandler, orderHandler).show();
        }
    }
}

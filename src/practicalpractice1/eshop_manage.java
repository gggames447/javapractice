package practicalpractice1;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class eshop_manage {
    static String inputPassword;
    static String inputUsername;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminAccount account1 = new AdminAccount("admin", "123456");
        AdminAccount account = new AdminAccount("hys", "123456");
        boolean isLoginSuccessfully = false;
        int loginFailedTimes = 1;


        while (loginFailedTimes <= 3) {
                   inputUsername = scanner.next();
                   inputPassword = scanner.next();
            if ((account.getName().equals(inputUsername) && account.getPassword().equals(inputPassword))
                || (account1.getName().equals(inputUsername) && account1.getPassword().equals(inputPassword))) {
                System.out.println("账号输入正确");
                break;
            } else {
                loginFailedTimes++;
                System.out.println("账号输入错误");
                if(loginFailedTimes >3){
                    System.out.println("已错误三次，强制退出登陆页面");
                    return;
                }
            }
        }
        loopMenu();

    }

    public static void loopMenu(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Scanner scanner = new Scanner(System.in);
        int enterNumber;
        Boolean isValid = true;
        while (isValid){
            System.out.println("当前账号 " + inputUsername + " " + "当前密码 " + inputPassword + " " +  dateFormat.format(new Date()));
            System.out.println("----------------------------------");
            List<String> introduction = Arrays.asList(
                    "=======电商订单管理系统=======",
                    "【1】用户管理",
                    "【2】商品分类管理",
                    "【3】商品管理",
                    "【4】收货地址管理",
                    "【5】订单下单",
                    "【6】订单查询与状态修改",
                    "【0】退出系统",
                    "============================",
                    "请输入功能编号：");
            introduction.forEach(System.out::println);
            if (!scanner.hasNextInt()) {
                System.out.println("输入非法，请输入数字编号：0-6");
                scanner.next();
                continue;
            }
            enterNumber = scanner.nextInt();
            switch(enterNumber){
                case 1 -> System.out.println("开发中");
                case 2 -> {
                    manageCategory();
                    return;
                }
                case 3 -> System.out.println("开发中");
                case 4 -> System.out.println("开发中");
                case 5 -> System.out.println("开发中");
                case 6 -> System.out.println("开发中");
                case 0 -> {
                    System.out.println("成功退出系统");
                    isValid = false;
                }
                default -> System.out.println("无效编号，请输入 0-6 之间的数字");
            }
        }
    }
    public static void manageCategory(){
        boolean runFlag = true;
        List<String> introduction = Arrays.asList(
                "=======商品分类管理系统=======",
                "【1】新增分类",
                "【2】查看所有分类",
                "【0】返回上级",
                "===========================");
        while(runFlag){
            introduction.forEach(System.out::println);
            Scanner scanner = new Scanner(System.in);
            int inputNumber = scanner.nextInt();

        }

    }
}

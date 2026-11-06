package practicalpractice3.utils.input;

import practicalpractice3.utils.exception.InputException;

import java.util.Scanner;

// 所有键盘输入都走这里。统一用 nextLine()，避免 nextInt() 把回车留在缓冲区
public class InputUtil {
    // 工具类，只提供静态方法，不需要创建对象
    private InputUtil() {}

    // 读一行并去掉首尾空格，空输入直接抛异常
    private static String readNotEmpty(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new InputException("输入不能为空");
        }
        return input;
    }

    public static int getInt(Scanner scanner) {
        String input = readNotEmpty(scanner);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InputException("请输入整数");
        }
    }

    public static int getInt(Scanner scanner, String tip) {
        System.out.print(tip);
        return getInt(scanner);
    }

    public static double getDouble(Scanner scanner, String tip) {
        System.out.print(tip);
        String input = readNotEmpty(scanner);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InputException("请输入数字");
        }
    }

    public static String getString(Scanner scanner, String tip) {
        System.out.print(tip);
        return readNotEmpty(scanner);
    }
}

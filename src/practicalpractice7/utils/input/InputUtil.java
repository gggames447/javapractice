package practicalpractice7.utils.input;

import practicalpractice7.utils.exception.InputException;

import java.util.Scanner;

/**
 * 所有键盘输入都走这里。统一用 nextLine()，避免 nextInt() 把回车留在缓冲区
 */
public class InputUtil {
    // 工具类，只提供静态方法，不需要创建对象
    private InputUtil() {}

   
    /**
     * 读一行并去掉首尾空格
     *
     * @param scanner 键盘输入
     * @return 去掉首尾空格后的输入
     * @throws InputException 输入为空时
     */
    private static String readNotEmpty(Scanner scanner) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            throw new InputException("输入不能为空");
        }
        return input;
    }

    /**
     * 读取一个整数
     *
     * @param scanner 键盘输入
     * @return 读到的整数
     * @throws InputException 输入为空或不是整数时
     */
    public static int getInt(Scanner scanner) {
        String input = readNotEmpty(scanner);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InputException("请输入数字");
        }
    }

    /**
     * 先打印提示，再读取一个整数
     *
     * @param scanner 键盘输入
     * @param tip 提示文字
     * @return 读到的整数
     * @throws InputException 输入为空或不是整数时
     */
    public static int getInt(Scanner scanner, String tip) {
        System.out.print(tip);
        return getInt(scanner);
    }

    /**
     * 读取一个小数
     *
     * @param scanner 键盘输入
     * @return 读到的小数
     * @throws InputException 输入为空或不是数字时
     */
    public static double getDouble(Scanner scanner) {
        String input = readNotEmpty(scanner);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InputException("请输入数字");
        }
    }

    /**
     * 先打印提示，再读取一个小数
     *
     * @param scanner 键盘输入
     * @param tip 提示文字
     * @return 读到的小数
     * @throws InputException 输入为空或不是数字时
     */
    public static double getDouble(Scanner scanner, String tip) {
        System.out.print(tip);
        return getDouble(scanner);
    }

    /**
     * 先打印提示，再读取一行文字
     *
     * @param scanner 键盘输入
     * @param tip 提示文字
     * @return 去掉首尾空格后的输入
     * @throws InputException 输入为空时
     */
    public static String getString(Scanner scanner, String tip) {
        System.out.print(tip);
        return readNotEmpty(scanner);
    }
}
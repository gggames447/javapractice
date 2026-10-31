package practicalpractice2.utils.input;

import practicalpractice2.utils.exception.InputException;
import java.util.Scanner;

public class InputUtil {
    private InputUtil() {}

   
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
            throw new InputException("请输入数字");
        }
    }

    public static int getInt(Scanner scanner, String tip) {
        System.out.print(tip);
        return getInt(scanner);
    }

    public static double getDouble(Scanner scanner) {
        String input = readNotEmpty(scanner);
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new InputException("请输入数字");
        }
    }

    public static double getDouble(Scanner scanner, String tip) {
        System.out.print(tip);
        return getDouble(scanner);
    }

    public static String getString(Scanner scanner, String tip) {
        System.out.print(tip);
        return readNotEmpty(scanner);
    }
}
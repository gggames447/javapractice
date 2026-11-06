package practicalpractice8.utils.menu;

import practicalpractice8.utils.exception.BusinessException;
import practicalpractice8.utils.exception.ExceptionLogger;
import practicalpractice8.utils.exception.InputException;
import practicalpractice8.utils.input.InputUtil;

import java.util.Scanner;

/**
 * 所有菜单的公共流程：循环显示菜单，输入 0 退出当前菜单。
 * 子类只需要决定打印哪个菜单、每个编号做什么。
 */
public abstract class BaseMenu {
    protected final Scanner scanner;

    /**
     * @param scanner 键盘输入，所有菜单共用同一个
     */
    protected BaseMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    /** 打印这个菜单的内容 */
    protected abstract void printMenu();

    /**
     * 处理用户选择的编号（0 已经由 show 处理）
     *
     * @param choice 用户输入的编号
     */
    protected abstract void handleChoice(int choice);

    /**
     * 列出带编号的选项，让用户选一个
     *
     * @param options 每个选项显示的文字
     * @param tip 提示文字
     * @return 选中的选项在数组里的下标
     * @throws InputException 编号不在 1 ~ 选项个数之间时
     */
    protected int chooseIndex(String[] options, String tip) {
        for (int i = 0; i < options.length; i++) {
            System.out.println("【" + (i + 1) + "】" + options[i]);
        }
        int choice = InputUtil.getInt(scanner, tip);
        if (choice < 1 || choice > options.length) {
            throw new InputException("请输入 1 ~ " + options.length + " 之间的编号");
        }
        return choice - 1;
    }

    /** 输入 0 时显示的提示，子菜单默认是返回主菜单 */
    protected String exitMessage() {
        return "返回主菜单";
    }

    /**
     * 显示菜单并循环处理，直到用户输入 0。
     * 每次操作中的异常都在这里统一记录日志并提示，程序不会因为异常而退出；
     * 无论成功还是失败，finally 都会输出操作回执。
     */
    public final void show() {
        while (true) {
            printMenu();
            try {
                int choice = InputUtil.getInt(scanner);
                if (choice == 0) {
                    System.out.println(exitMessage());
                    return;
                }
                handleChoice(choice);
            } catch (InputException e) {
                ExceptionLogger.log(e);
                System.out.println("输入错误：" + e.getMessage());
            } catch (BusinessException e) {
                ExceptionLogger.log(e);
                System.out.println("操作失败：" + e.getMessage());
            } catch (RuntimeException e) {
                ExceptionLogger.log(e);
                System.out.println("系统出现异常，已返回菜单，请重试");
            } finally {
                System.out.println("—— 本次操作结束 ——");
            }
        }
    }
}

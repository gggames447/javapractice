package practicalpractice5.utils.menu;

import practicalpractice5.utils.exception.BusinessException;
import practicalpractice5.utils.exception.InputException;
import practicalpractice5.utils.input.InputUtil;

import java.util.Scanner;

/**
 * 所有菜单的公共流程：循环显示菜单，输入 0 退出当前菜单
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

    /** 输入 0 时显示的提示，子菜单默认是返回主菜单 */
    protected String exitMessage() {
        return "返回主菜单";
    }

    /**
     * 显示菜单并循环处理，直到用户输入 0
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
                System.out.println("输入错误：" + e.getMessage());
            } catch (BusinessException e) {
                // InputException 也是 BusinessException，所以必须写在它后面
                System.out.println("操作失败：" + e.getMessage());
            }
        }
    }
}

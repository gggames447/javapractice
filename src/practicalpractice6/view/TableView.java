package practicalpractice6.view;

import practicalpractice6.utils.menu.MenuUtil;

/**
 * 所有表格的公共打印流程：没有数据时打印提示，有数据时先打印表头再逐行打印。
 * 子类只需要决定有哪些列、每一行怎么打印。
 *
 * @param <T> 要显示的数据类型
 */
public abstract class TableView<T> {

    /**
     * 打印一张表格
     *
     * @param items 要显示的数据
     * @param emptyMessage 没有数据时显示的提示
     */
    public void printTable(T[] items, String emptyMessage) {
        if (items.length == 0) {
            System.out.println(emptyMessage);
            return;
        }
        printHeader();
        for (T item : items) {
            printRow(item);
        }
    }

    /**
     * 只打印一条数据，也带表头
     *
     * @param item 要显示的数据
     */
    public void printOne(T item) {
        printHeader();
        printRow(item);
    }

    private void printHeader() {
        MenuUtil.printLine();
        printColumnNames();
        MenuUtil.printLine();
    }

    /** 打印列名，由子类决定有哪些列 */
    protected abstract void printColumnNames();

    /**
     * 打印一行数据，由子类决定格式
     *
     * @param item 要打印的数据
     */
    protected abstract void printRow(T item);
}

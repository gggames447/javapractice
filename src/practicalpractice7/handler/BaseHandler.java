package practicalpractice7.handler;

import practicalpractice7.entity.BaseEntity;
import practicalpractice7.interfaces.DataOperate;

/**
 * 所有 Handler 的公共部分：用数组存数据，提供新增、查看全部、按 ID 删除。
 * 子类只需要实现表头和每一行的打印，需要额外的新增规则时重写 checkBeforeAdd。
 *
 * @param <T> 管理的实体类型，必须继承 BaseEntity，这样才能统一用 getId() 查找
 */
public abstract class BaseHandler<T extends BaseEntity> implements DataOperate<T> {
    protected static final int MAX_SIZE = 20;
    protected int  count = 0;
    protected final T[] array;

    /**
     * Java 不能直接 new T[]，所以数组由子类创建后传进来
     *
     * @param array 子类创建好的数组，例如 new Goods[MAX_SIZE]
     */
    protected BaseHandler(T[] array) {
        this.array = array;
    }

    @Override
    public void add(T item) {
        if (count == array.length) {
            System.out.println("存储已满，无法新增！");
            return;
        }
        if (findIndexById(item.getId()) != -1) {
            System.out.println("编号已存在，新增失败！");
            return;
        }
        if (!checkBeforeAdd(item)) {
            return;
        }
        array[count] = item;
        count++;
        System.out.println("添加成功！");
    }
    /**
     * 查看全部，没有数据时提示暂无数据
     */
    @Override
    public void queryAll() {
        if (count == 0) {
            System.out.println("暂无数据。");
            return;
        }
        printHeader();
        for (int i = 0; i < count; i++) {
            printItem(array[i]);
        }
    }
    /**
     * 按 ID 删除，后面的元素依次前移
     *
     * @param id 要删除的数据的 ID
     * @return 删除成功返回 true，找不到返回 false
     */
    @Override
    public boolean delete(String id) {
        int index = findIndexById(id);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        count--;
        array[count] = null;
        return true;
    }
    /**
     * 按 ID 查找下标
     *
     * @param id 要查找的 ID
     * @return 找到返回下标，找不到返回 -1
     */
    protected int findIndexById(String id) {
        for (int i = 0; i < count; i++) {
            if (array[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * @param item 要新增的数据
     * @return 可以新增返回 true，否则返回 false
     */
    protected boolean checkBeforeAdd(T item) {
        return true;
    }
    /** 打印表头，由子类决定有哪些列 */
    protected abstract void printHeader();

    /**
     * 打印一行数据，由子类决定格式
     *
     * @param item 要打印的数据
     */
    protected abstract void printItem(T item);
}


package practicalpractice6.handler;

import practicalpractice6.entity.BaseEntity;
import practicalpractice6.interfaces.DataOperate;
import practicalpractice6.utils.exception.BusinessException;

import java.util.Arrays;

/**
 * 所有 Handler 的公共部分：用数组存数据，提供新增、取得全部、查找、按 ID 删除。
 * Handler 只管数据和业务规则，不打印任何东西：
 * 查询方法返回数据，由 view 包负责显示；操作失败时抛出异常，由菜单负责提示。
 *
 * @param <T> 管理的实体类型，必须继承 BaseEntity，这样才能统一用 getId() 查找
 */
public abstract class BaseHandler<T extends BaseEntity> implements DataOperate<T> {
    protected static final int MAX_SIZE = 20;

    // 有效数据的个数，只有下标 0 ~ count-1 有数据，后面都是 null
    protected int count = 0;
    protected final T[] array;

    /**
     * Java 不能直接 new T[]，所以数组由子类创建后传进来
     *
     * @param array 子类创建好的数组，例如 new Goods[MAX_SIZE]
     */
    protected BaseHandler(T[] array) {
        this.array = array;
    }

    /**
     * 新增：先检查容量和编号是否重复，再交给子类做额外检查，都通过才保存
     *
     * @param item 要新增的数据
     * @throws BusinessException 存储已满、编号重复，或不满足子类的新增规则时
     */
    @Override
    public void add(T item) {
        if (count == array.length) {
            throw new BusinessException("存储已满，最多 " + MAX_SIZE + " 条");
        }
        if (existsById(item.getId())) {
            throw new BusinessException("编号「" + item.getId() + "」已存在");
        }
        validateBeforeAdd(item);
        array[count] = item;
        count++;
    }

    /**
     * @return 全部数据的副本，外面改这个数组不会影响 Handler 里的数据
     */
    @Override
    public T[] findAll() {
        return Arrays.copyOf(array, count);
    }

    /**
     * @return 一条数据都没有时返回 true
     */
    public boolean isEmpty() {
        return count == 0;
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
     * 按 ID 查找
     *
     * @param id 要查找的 ID
     * @return 找到返回该数据，找不到返回 null
     */
    public T findById(String id) {
        int index = findIndexById(id);
        return (index == -1) ? null : array[index];
    }

    /**
     * @param id 要判断的 ID
     * @return 存在返回 true
     */
    public boolean existsById(String id) {
        return findIndexById(id) != -1;
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
     * 子类可以重写，补充自己的新增规则，比如名称不能重复。不通过时抛出异常。
     *
     * @param item 要新增的数据
     * @throws BusinessException 不满足新增规则时
     */
    protected void validateBeforeAdd(T item) {
    }
}

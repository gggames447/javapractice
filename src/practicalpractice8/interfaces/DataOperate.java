package practicalpractice8.interfaces;

/**
 * 数据操作接口，规定所有数据管理类都必须能新增、查看和删除。
 *
 * @param <T> 管理的数据类型
 */
public interface DataOperate<T> {

    /**
     * 新增一条数据
     *
     * @param item 要新增的数据
     */
    void add(T item);

    /**
     * 取得全部数据，怎么显示由调用方决定
     *
     * @return 全部数据，没有数据时长度为 0
     */
    T[] findAll();

    /**
     * 按 ID 删除一条数据
     *
     * @param id 要删除的数据的 ID
     * @return 删除成功返回 true，找不到返回 false
     */
    boolean delete(String id);
}

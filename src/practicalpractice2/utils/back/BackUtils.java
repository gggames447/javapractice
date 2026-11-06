package practicalpractice2.utils.back;

import practicalpractice2.utils.exception.BackException;

// 处理「返回上级」
public class BackUtils {
    public static final int BACK_CODE = 0;

    // 工具类，只提供静态方法，不需要创建对象
    private BackUtils() {}

    // 判断用户是否选择了返回上级
    public static void checkBack(int input) {
        if (input == BACK_CODE) {
            throw new BackException("返回上级菜单");
        }
    }
}

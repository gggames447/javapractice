package practicalpractice4.utils.back;

import practicalpractice4.utils.exception.BackException;

/**
 * 处理「返回上级」
 */
public class BackUtils {
    public static final int backCode = 0;
    /**
     * 判断用户是否选择了返回上级
     *
     * @param input 用户输入的菜单编号
     * @throws BackException 输入为 0 时抛出，子菜单收到后返回上级
     */
    public static void checkBack(int input){
        if(input == backCode){
            throw new BackException("User intends to back to previous menu");
        }
    }
}

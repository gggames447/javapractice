package practicalpractice3.utils.back;

import practicalpractice3.utils.exception.BackException;

// 输入 0 时抛出 BackException，子菜单收到后返回上级
public class BackUtils {
    public static final int backCode = 0;
    public static void checkBack(int input){
        if(input == backCode){
            throw new BackException("User intends to back to prevous menu");
        }
    }
}

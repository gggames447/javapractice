package practicalpractice2.utils.back;

import practicalpractice2.utils.exception.BackException;

public class BackUtils {
    public static final int backCode = 0;
    public static void checkBack(int input){
        if(input == backCode){
            throw new BackException("User intends to back to prevous menu");
        }
    }
}

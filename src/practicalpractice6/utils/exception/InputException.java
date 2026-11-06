package practicalpractice6.utils.exception;

/**
 * 输入不合法时抛出，由菜单统一捕获并提示
 */
public class InputException extends RuntimeException {
    /**
     * @param message 显示给用户的错误提示
     */
    public InputException(String message) {
        super(message);
    }

}

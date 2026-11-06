package practicalpractice5.utils.exception;

/**
 * 输入不合法时抛出。它也是一种业务异常，所以继承 BusinessException
 */
public class InputException extends BusinessException {

    /**
     * @param message 显示给用户的错误提示
     */
    public InputException(String message) {
        super(message);
    }
}

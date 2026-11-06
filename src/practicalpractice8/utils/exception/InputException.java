package practicalpractice8.utils.exception;

/**
 * 输入不合法时抛出，例如输入为空、不是数字、单价小于等于 0
 */
public class InputException extends BusinessException {
    /**
     * @param message 显示给用户的错误提示
     */
    public InputException(String message) {
        super(message);
    }
}

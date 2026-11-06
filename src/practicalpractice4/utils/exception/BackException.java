package practicalpractice4.utils.exception;

/**
 * 用户选择返回上级时抛出
 */
public class BackException extends RuntimeException {

    /**
     * @param message 异常信息
     */
    public BackException(String message) {
        super(message);
    }
}

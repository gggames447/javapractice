package practicalpractice4.utils.exception;

/**
 * 业务规则不满足时抛出，比如编号重复、找不到数据、存储已满，由菜单统一捕获并提示
 */
public class BusinessException extends RuntimeException {

    /**
     * @param message 显示给用户的错误提示
     */
    public BusinessException(String message) {
        super(message);
    }
}

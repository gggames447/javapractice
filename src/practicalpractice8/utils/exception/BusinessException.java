package practicalpractice8.utils.exception;

/**
 * 业务异常：操作不满足业务规则时抛出，例如编号重复、找不到数据、存储已满。
 * 项目里所有自定义异常都继承它，菜单可以用一个 catch 统一处理。
 */
public class BusinessException extends RuntimeException {
    /**
     * @param message 显示给用户的错误提示
     */
    public BusinessException(String message) {
        super(message);
    }
}

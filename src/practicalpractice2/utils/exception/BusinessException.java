package practicalpractice2.utils.exception;

// 业务规则不满足时抛出，比如编号重复、找不到数据、存储已满，由菜单统一捕获并提示
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}

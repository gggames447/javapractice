package practicalpractice3.utils.exception;

// 用户选择返回上级时抛出
public class BackException extends RuntimeException {

    public BackException(String message) {
        super(message);
    }
}

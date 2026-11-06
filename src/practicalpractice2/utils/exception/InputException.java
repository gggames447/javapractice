package practicalpractice2.utils.exception;

// 输入不合法时抛出，由菜单统一捕获并提示
public class InputException extends RuntimeException {

    public InputException(String message) {
        super(message);
    }
}

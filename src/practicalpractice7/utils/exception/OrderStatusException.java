package practicalpractice7.utils.exception;

/**
 * 订单状态非法变更时抛出，例如把已付款的订单改回待付款
 */
public class OrderStatusException extends BusinessException {
    /**
     * @param message 显示给用户的错误提示
     */
    public OrderStatusException(String message) {
        super(message);
    }
}

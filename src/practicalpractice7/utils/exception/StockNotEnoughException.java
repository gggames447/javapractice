package practicalpractice7.utils.exception;

/**
 * 下单时商品库存不足，或商品已不存在时抛出
 */
public class StockNotEnoughException extends RuntimeException {
    /**
     * @param message 显示给用户的错误提示
     */
    public StockNotEnoughException(String message) {
        super(message);
    }
}

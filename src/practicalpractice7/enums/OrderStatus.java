package practicalpractice7.enums;

/**
 * 订单状态，只能是这 5 种。
 * 枚举名用英文（规范要求标识符不能用中文），显示给用户时用 getLabel() 取中文名称。
 */
public enum OrderStatus {
    UNPAID("待付款"),
    PAID("已付款"),
    SHIPPED("已发货"),
    FINISHED("已完成"),
    CANCELLED("已取消");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    /**
     * @return 显示给用户看的中文名称，例如「待付款」
     */
    public String getLabel() {
        return label;
    }

    /**
     * 判断能不能从当前状态改为目标状态。
     * 正常流程只能按顺序前进一步：待付款 → 已付款 → 已发货 → 已完成；
     * 只有待付款的订单可以取消；已完成和已取消的订单不能再改。
     *
     * @param target 目标状态
     * @return 允许返回 true，否则返回 false
     */
    public boolean canChangeTo(OrderStatus target) {
        return switch (this) {
            case UNPAID -> target == PAID || target == CANCELLED;
            case PAID -> target == SHIPPED;
            case SHIPPED -> target == FINISHED;
            case FINISHED, CANCELLED -> false;
        };
    }
}

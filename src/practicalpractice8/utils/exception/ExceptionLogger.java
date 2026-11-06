package practicalpractice8.utils.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 异常日志：统一输出异常的发生时间、类型和原因
 */
public class ExceptionLogger {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 工具类，只提供静态方法，不需要创建对象
    private ExceptionLogger() {}

    /**
     * 打印一条异常日志
     *
     * @param e 捕获到的异常
     */
    public static void log(Exception e) {
        System.out.println("[异常日志] " + LocalDateTime.now().format(TIME_FORMAT)
                + " " + e.getClass().getSimpleName() + "：" + e.getMessage());
    }
}
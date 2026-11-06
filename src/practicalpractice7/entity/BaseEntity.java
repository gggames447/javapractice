package practicalpractice7.entity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 所有实体的父类，统一提供 ID 和创建时间。
 * BaseHandler 靠 getId() 对所有实体做统一的查找、查重和删除。
 */
public abstract class BaseEntity {
    private final String id;
    private final LocalDateTime createTime;

    /**
     * 使用指定的 ID 创建，用于商品、分类、订单这类编号由用户输入或程序生成的实体
     *
     * @param id 编号
     */
    protected BaseEntity(String id) {
        this.id = id;
        this.createTime = LocalDateTime.now();
    }

    /**
     * 自动生成 UUID 作为 ID，用于用户、地址、订单项
     */
    protected BaseEntity() {
        this(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }
}

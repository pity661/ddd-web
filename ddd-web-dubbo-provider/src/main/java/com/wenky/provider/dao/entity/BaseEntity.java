package com.wenky.provider.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-30 09:50
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /** 主键 * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    /** 创建时间 * */
    @Column(insertable = false, updatable = false)
    protected LocalDateTime createdAt;
    /** 最后修改时间 * */
    @Column(insertable = false)
    protected LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

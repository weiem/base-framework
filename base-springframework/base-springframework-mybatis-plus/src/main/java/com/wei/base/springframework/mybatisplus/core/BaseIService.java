package com.wei.base.springframework.mybatisplus.core;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseIService<T> extends IService<T> {

    /**
     * 批量插入,如果插入字段为null不会取数据库里配置的默认值
     *
     * @param entityList ignore
     * @return ignore
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 批量插入,如果插入字段为null不会取数据库里配置的默认值
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    int insertBatchSomeColumn(List<T> entityList, int batchSize);

    /**
     * 逻辑删除 需要在实体里逻辑删除的字段上添加@TableLogic注解
     * 根据 id 逻辑删除数据,并带字段填充功能
     * 如果要更新更新时间的话需要在更新时间字段上带上 @TableField(fill = FieldFill.INSERT_UPDATE, update = "now()")
     * 注解 具体的查看注解内的注释
     *
     * @param entity ignore
     * @return ignore
     */
    int deleteByIdWithFill(T entity);
}

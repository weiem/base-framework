package com.wei.base.springframework.mysql.mybatisplus.core;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wei.base.springframework.mysql.mybatisplus.config.CustomMybatisPlusProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseIService<T> {

    @Autowired
    protected M baseMapper;

    @Autowired
    private CustomMybatisPlusProperties customMybatisPlusProperties;

    /**
     * 批量插入,如果插入字段为null不会取数据库里配置的默认值
     *
     * @param entityList ignore
     * @return ignore
     */
    @Override
    public int insertBatchSomeColumn(List<T> entityList) {
        return insertBatchSomeColumn(entityList, customMybatisPlusProperties.getBatchSize());
    }

    /**
     * 批量插入,如果插入字段为null不会取数据库里配置的默认值
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    @Override
    public int insertBatchSomeColumn(List<T> entityList, int batchSize) {
        List<List<T>> partition = Lists.partition(entityList, batchSize);
        int i = 0;
        for (List<T> list : partition) {
            i += baseMapper.insertBatchSomeColumn(list);
        }

        return i;
    }

    /**
     * 逻辑删除 需要在实体里逻辑删除的字段上添加@TableLogic注解
     * 根据 id 逻辑删除数据,并带字段填充功能
     * 如果要更新更新时间的话需要在更新时间字段上带上 @TableField(fill = FieldFill.INSERT_UPDATE, update = "now()")
     * 注解 具体的查看注解内的注释
     *
     * @param entity ignore
     * @return ignore
     */
    @Override
    public int deleteByIdWithFill(T entity) {
        return baseMapper.deleteByIdWithFill(entity);
    }
}
package com.wei.base.springframework.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteWorkbook;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wei.base.springframework.constant.exception.ServiceException;
import com.wei.base.springframework.easyexcel.enums.EsayExcelExceptionEnum;
import com.wei.base.springframework.easyexcel.enums.ExportFileTypeEnum;
import com.wei.base.springframework.easyexcel.vo.ExportParam;
import com.wei.base.springframework.easyexcel.vo.ExportRequest;
import com.wei.base.springframework.util.BeanUtil;
import com.wei.base.springframework.util.PageUtil;
import com.wei.base.springframework.util.SpringBeanUtil;
import com.wei.base.springframework.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.OutputStream;

/**
 * 导出公共逻辑实现
 *
 * @author : weierming
 * @date : 2020/9/30
 */
@Slf4j
public class BaseExportLogic {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 每页大小
     */
    private static final Integer SIZE = 5000;

    /**
     * 校验导出参数
     *
     * @param exportRequest 导出入参
     * @return 转换后的导出入参
     */
    public static ExportParam checkParameter(ExportRequest exportRequest) {
        String parameter = exportRequest.getParameter();
        ExportParam exportParam = new ExportParam();
        ObjectNode objectNode = objectMapper.createObjectNode();
        if (StringUtil.isNotBlank(parameter)) {
            try {
                // 校验查询参数是否是json字符串
                objectNode = (ObjectNode) objectMapper.readTree(parameter);
                if (!objectNode.isObject()) {
                    throw new ServiceException(EsayExcelExceptionEnum.EXPORT_PARAMETER_ERROR);
                }
            } catch (JsonProcessingException e) {
                log.error(e.getMessage(), e);
                throw new ServiceException(EsayExcelExceptionEnum.EXPORT_PARAMETER_ERROR);
            }
        }

        exportParam.setParam(objectNode);
        exportParam.setFileType(ObjectUtils.defaultIfNull(exportRequest.getFileType(), ExportFileTypeEnum.XLSX));
        return exportParam;
    }

    /**
     * excel写入逻辑
     *
     * @param outputStream 输出流
     * @param exportParam  转换后的导出入参
     */
    public static void excelWriter(OutputStream outputStream, ExportParam exportParam) {
        WriteWorkbook writeWorkbook = new WriteWorkbook();
        writeWorkbook.setOutputStream(outputStream);
        // excel类型
        writeWorkbook.setExcelType(exportParam.getFileType().getExcelTypeEnum());
        // 导出excel实体
        writeWorkbook.setClazz(exportParam.getExcelEntityClass());
        ExcelWriter excelWriter = new ExcelWriter(writeWorkbook);
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        Integer pageCount = excelWriteLogic(excelWriter, writeSheet, exportParam.getParam(), 1, exportParam.getSubClass(),
                exportParam.getSuperGenericityClass(), exportParam.getExcelEntityClass());
        if (pageCount > 1) {
            for (int i = 2; i <= pageCount; i++) {
                excelWriteLogic(excelWriter, writeSheet, exportParam.getParam(), i, exportParam.getSubClass(),
                        exportParam.getSuperGenericityClass(), exportParam.getExcelEntityClass());
            }
        }

        excelWriter.finish();
    }

    /**
     * 将查询出来的数据存入excel中
     *
     * @param excelWriter          ExcelWriter对象
     * @param writeSheet           excelSheet对象
     * @param param                查询参数
     * @param current              页码
     * @param subClass             实现导出类的实体
     * @param superGenericityClass 查询数据接口实体类
     * @param excelEntityClass     excel实体类
     * @return 总页数
     */
    private static Integer excelWriteLogic(ExcelWriter excelWriter, WriteSheet writeSheet, ObjectNode param,
                                           Integer current, Class<?> subClass,
                                           Class<?> superGenericityClass, Class<?> excelEntityClass) {
        param.put("size", SIZE);
        param.put("current", current);

        BaseExport baseExport = SpringBeanUtil.getBean(subClass);
        Object exportDataParam = objectMapper.convertValue(param, superGenericityClass);
        Page<Object> exportData = baseExport.getExportData(exportDataParam);
        if (exportData == null) {
            exportData = new Page<>();
        }

        excelWriter.write(BeanUtil.copy(exportData.getRecords(), excelEntityClass), writeSheet);
        Integer pageCount = PageUtil.getPageCount(exportData.getTotal(), SIZE);
        return pageCount;
    }
}
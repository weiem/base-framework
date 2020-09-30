package com.wei.base.springframework.easyexcel.vo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wei.base.springframework.easyexcel.enums.ExportFileTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExportParam implements Serializable {

    private static final long serialVersionUID = -7105868777500886435L;

    @ApiModelProperty(value = "导出文件名!")
    private String fileName;

    @ApiModelProperty(value = "导出查询条件,json串格式")
    private ObjectNode param;

    @ApiModelProperty(value = "时间类型(不传默认:yyyy-MM-dd HH:mm:ss)")
    private String dateType = "yyyy-MM-dd HH:mm:ss";

    @ApiModelProperty(value = "导出文件格式 (1:XLS, 2:XLSX, 3:CSV  不传默认1)")
    private ExportFileTypeEnum fileType;

    @ApiModelProperty(value = "查询数据接口实体类")
    private Class<?> superGenericityClass;

    @ApiModelProperty(value = "导出文件实体类")
    private Class<?> excelEntityClass;

    @ApiModelProperty(value = "实现BaseExport实体类")
    private Class<?> subClass;
}
package com.wei.base.springframework.easyexcel.vo;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wei.base.springframework.easyexcel.enums.ExportFileTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExportRequest implements Serializable {

    private static final long serialVersionUID = -7105868777500886435L;

    @ApiModelProperty(value = "导出文件名!")
    private String fileName = IdWorker.getIdStr();

    @ApiModelProperty(value = "导出查询条件,json串格式")
    private String parameter;

    @ApiModelProperty(value = "时间类型(不传默认:yyyy-MM-dd HH:mm:ss)")
    private String dateType = "yyyy-MM-dd HH:mm:ss";

    @ApiModelProperty(value = "导出文件格式 (1:XLS, 2:XLSX, 3:CSV  不传默认1)")
    private Integer fileType = ExportFileTypeEnum.XLSX.getCode();

    public ExportFileTypeEnum getFileType() {
        return ExportFileTypeEnum.getEnumByCode(fileType);
    }
}
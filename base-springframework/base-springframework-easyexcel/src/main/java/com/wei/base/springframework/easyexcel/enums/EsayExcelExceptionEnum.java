package com.wei.base.springframework.easyexcel.enums;

import com.wei.base.springframework.constant.exception.BaseException;
import lombok.Getter;

@Getter
public enum EsayExcelExceptionEnum implements BaseException {

    COMMENT_SERVICE_EXCEPTION(1000, "%s"),
    EXPORT_PARAMETER_ERROR(1001, "导出查询参数错误!"),
    GENERATE_FILE_FAIL(1002, "生成导出文件失败!"),
    ;

    private Integer code;
    private String msg;

    EsayExcelExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
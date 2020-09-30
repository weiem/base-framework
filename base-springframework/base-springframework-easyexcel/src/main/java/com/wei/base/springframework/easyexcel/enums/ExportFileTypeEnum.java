/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wei.base.springframework.easyexcel.enums;

import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 导出文件类型
 *
 * @author : weierming
 * @date : 2020/9/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExportFileTypeEnum {

    XLS(1, ExcelTypeEnum.XLS),
    XLSX(2, ExcelTypeEnum.XLSX),
    ;

    private Integer code;
    private ExcelTypeEnum excelTypeEnum;

    public static ExportFileTypeEnum getEnumByCode(Integer code) {
        for (ExportFileTypeEnum result : ExportFileTypeEnum.values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }

        return null;
    }
}
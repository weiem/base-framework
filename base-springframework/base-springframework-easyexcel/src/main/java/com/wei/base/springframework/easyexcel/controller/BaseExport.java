package com.wei.base.springframework.easyexcel.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Charsets;
import com.wei.base.springframework.constant.exception.ServiceException;
import com.wei.base.springframework.easyexcel.enums.EsayExcelExceptionEnum;
import com.wei.base.springframework.easyexcel.vo.ExportParam;
import com.wei.base.springframework.easyexcel.vo.ExportRequest;
import com.wei.base.springframework.util.HttpServletUtil;
import com.wei.base.springframework.util.ReflectionUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 公共导出
 *
 * @param <T> 查询分页数据实体类
 * @param <E> excel实体对象
 * @author : weierming
 * @date : 2020/9/22
 */
public interface BaseExport<T, E> {

    Logger log = LoggerFactory.getLogger(BaseExport.class);

    /**
     * 导出
     *
     * @param exportRequest
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(value = "导出", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    default Object export(@Validated ExportRequest exportRequest) {
        ExportParam exportParam = BaseExportLogic.checkParameter(exportRequest);
        Class<? extends BaseExport> subClass = getClass();
        exportParam.setSubClass(subClass);

        // 通过反射获取子类上的泛型集合
        List<Class<?>> list = ReflectionUtil.getSuperGenericityClass(subClass);
        exportParam.setSuperGenericityClass(list.size() >= 1 ? list.get(0) : null);
        exportParam.setExcelEntityClass(list.size() >= 2 ? list.get(1) : null);

        HttpServletResponse httpServletResponse = HttpServletUtil.getHttpServletResponse();
        httpServletResponse.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try (OutputStream outputStream = httpServletResponse.getOutputStream()) {
            httpServletResponse.addHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment;fileName=%s",
                    URLEncoder.encode(exportRequest.getFileName() + exportParam.getFileType().getExcelTypeEnum().getValue(),
                            String.valueOf(Charsets.UTF_8))));
            BaseExportLogic.excelWriter(outputStream, exportParam);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(EsayExcelExceptionEnum.GENERATE_FILE_FAIL);
        }

        return null;
    }

    /**
     * 获取导出数据
     *
     * @param t 查询条件 实体里必须包含size current 这两个参数 不要一次性查出所有数据
     * @return 分页参数
     */
    Page<?> getExportData(T t);
}
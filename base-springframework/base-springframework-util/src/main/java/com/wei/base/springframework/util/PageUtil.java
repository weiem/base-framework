package com.wei.base.springframework.util;

import java.math.BigDecimal;

/**
 * 分页工具类
 *
 * @author admin
 */
public class PageUtil {

    /**
     * 根据总条数获取每页大小
     *
     * @param total
     * @param pageSize
     * @return
     */
    public static Integer getPageCount(Integer total, Integer pageSize) {
        BigDecimal totalB = new BigDecimal(total);
        BigDecimal pageSizeB = new BigDecimal(pageSize);

        return totalB.divide(pageSizeB, 0, BigDecimal.ROUND_UP).intValue();
    }

    public static Integer getPageCount(Long total, Integer pageSize) {
        BigDecimal totalB = new BigDecimal(total);
        BigDecimal pageSizeB = new BigDecimal(pageSize);

        return totalB.divide(pageSizeB, 0, BigDecimal.ROUND_UP).intValue();
    }

    public static Integer getPageCount(Long total, Long pageSize) {
        BigDecimal totalB = new BigDecimal(total);
        BigDecimal pageSizeB = new BigDecimal(pageSize);

        return totalB.divide(pageSizeB, 0, BigDecimal.ROUND_UP).intValue();
    }
}

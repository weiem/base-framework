package com.wei.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.base.springframework.easyexcel.controller.BaseExport;
import com.wei.user.excelEntity.UserExcel;
import com.wei.user.vo.UserVO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test implements BaseExport<UserVO, UserExcel> {
    @Override
    public Page<UserExcel> getExportData(UserVO userVO) {
        return null;
    }
}

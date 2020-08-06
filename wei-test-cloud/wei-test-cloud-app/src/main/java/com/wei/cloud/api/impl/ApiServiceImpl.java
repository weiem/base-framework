package com.wei.cloud.api.impl;

import com.wei.cloud.api.ApiService;
import com.wei.cloud.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiServiceImpl implements ApiService {

    @Autowired
    private CloudService cloudService;

    @Override
    public String test(String b) {
        return cloudService.test();
    }
}

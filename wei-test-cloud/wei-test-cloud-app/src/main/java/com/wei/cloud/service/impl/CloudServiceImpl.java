package com.wei.cloud.service.impl;

import com.wei.cloud.service.CloudService;
import org.springframework.stereotype.Service;

@Service
public class CloudServiceImpl implements CloudService {

    @Override
    public String test() {
        return "test";
    }
}
package com.xll.xmall.admin.web.admin.controller;

import com.xll.xmall.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${spring.application.name}")
    private String applicationName;

    @RequestMapping("/get")
    public CommonResult get() {
        return CommonResult.success("nacos config RefreshScope :" + applicationName);
    }


}

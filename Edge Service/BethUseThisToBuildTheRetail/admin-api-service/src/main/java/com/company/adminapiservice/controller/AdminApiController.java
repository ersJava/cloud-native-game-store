package com.company.adminapiservice.controller;

import com.company.adminapiservice.service.ServiceLayer;
import com.company.adminapiservice.service.ServiceLayerAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class AdminApiController {

    @Autowired
    ServiceLayer serviceLayer;

    @Autowired
    ServiceLayerAdmin serviceLayerAdmin;



}

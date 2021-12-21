package com.dingtalk.controller;

import com.dingtalk.model.ServiceResult;
import com.dingtalk.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 同步控制器
 *
 * @date 2021/12/12
 */
@RestController
public class SyncController {

    @Autowired
    SyncService syncService;

    @PutMapping("/sync")
    public ServiceResult sync() {
        syncService.sync();
        return ServiceResult.getSuccessResult(null);
    }

}

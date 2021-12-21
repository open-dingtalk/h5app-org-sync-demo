package com.dingtalk.quartz;

import com.dingtalk.service.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * quartz调度器任务
 *
 * @author dingping
 * @date 2021/08/19
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QuartzSchedulerTask {

    @Autowired
    SyncService syncService;

    /**
     * 每1个小时同步一次
     **/
//    @Scheduled(cron = "0 0 */1 * * ?")
    private void generateDingTalkAccessToken() throws Exception {
        syncService.sync();
    }
}

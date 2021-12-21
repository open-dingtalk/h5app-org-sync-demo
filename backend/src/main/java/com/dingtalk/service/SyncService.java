package com.dingtalk.service;

import com.dingtalk.model.PsDingDept;
import com.dingtalk.model.PsDingUser;

import java.util.List;

public interface SyncService {

    void sync();

    List<PsDingDept> syncDept();

    List<PsDingUser> syncUser();

    List<PsDingDept> syncDeptManager();

}

package com.dingtalk.service.impl;

import cn.hutool.json.JSONUtil;
import com.dingtalk.constant.NumberConstant;
import com.dingtalk.constant.UrlConstant;
import com.dingtalk.model.Department;
import com.dingtalk.model.PsDingUser;
import com.dingtalk.service.DingService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class DingServiceImpl implements DingService {

    @Override
    public OapiV2DepartmentCreateResponse createDepartment(Department department, String token) {

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.CREATE_DEPT_URL);
        OapiV2DepartmentCreateRequest req = new OapiV2DepartmentCreateRequest();
        req.setName(department.getName());
        req.setParentId(department.getParent_id());
        req.setHideDept(department.getHide_dept());
        req.setDeptPermits(department.getDept_permits());
        req.setUserPermits(department.getUser_permits());
        req.setOuterDept(department.getOuter_dept());
        req.setCreateDeptGroup(true);
        req.setOuterDeptOnlySelf(department.getOuter_dept_only_self());
        req.setOuterPermitUsers(department.getOuter_permit_users());
        req.setOuterPermitDepts(department.getOuter_permit_depts());
        req.setCreateDeptGroup(department.getCreate_dept_group());
        req.setOrder(department.getOrder());
        req.setSourceIdentifier(department.getSource_identifier());
        OapiV2DepartmentCreateResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("CreateDepartment错误原因:" + response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("获取token错误:" + e.getErrMsg());
        }
        return response;
    }

    @Override
    public OapiV2DepartmentUpdateResponse updateDepartment(Department department, String token) {

        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.UPDATE_DEPT_URL);
        OapiV2DepartmentUpdateRequest req = new OapiV2DepartmentUpdateRequest();
        req.setName(department.getName());
        req.setDeptId(Long.parseLong(department.getDept_id()));
        req.setParentId(department.getParent_id());
        req.setHideDept(department.getHide_dept());
        req.setDeptPermits(department.getDept_permits());
        req.setUserPermits(department.getUser_permits());
        req.setOuterDept(department.getOuter_dept());
        req.setOuterDeptOnlySelf(department.getOuter_dept_only_self());
        req.setOuterPermitUsers(department.getOuter_permit_users());
        req.setOuterPermitDepts(department.getOuter_permit_depts());
        req.setCreateDeptGroup(department.getCreate_dept_group());
        req.setOrder(department.getOrder());
        req.setSourceIdentifier(department.getSource_identifier());
        OapiV2DepartmentUpdateResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("UpdateDepartment错误原因:" + response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("获取token错误:" + e.getErrMsg());
        }
        return response;
    }

    @Override
    public OapiV2UserCreateResponse createDingUser(PsDingUser dingUser, String dingDeptId, String token) {
        log.info("开始创建钉钉人员信息：{}", dingUser);
        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.CREATE_USER_URL);
        OapiV2UserCreateRequest req = new OapiV2UserCreateRequest();
        req.setUserid(dingUser.getDingUserId());
        req.setName(dingUser.getName());
        req.setMobile(dingUser.getMobile());
        req.setHideMobile(false);
        req.setTitle(dingUser.getJobname());
        req.setJobNumber(dingUser.getCode());

        // 设置扩展属性
        Map<String, String> extensionInfo = new ConcurrentHashMap<>(4);
        extensionInfo.put("岗位", dingUser.getPostname() != null ? dingUser.getPostname() : "");
        extensionInfo.put("职务", dingUser.getJobname() != null ? dingUser.getJobname() : "");
        extensionInfo.put("人员状态", dingUser.getPstype() != null ? dingUser.getPstype() : "");
        extensionInfo.put("员工编码", dingUser.getCode() != null ? dingUser.getCode() : "");
        req.setExtension(JSONUtil.toJsonStr(extensionInfo));

        req.setDeptIdList(dingDeptId);

        OapiV2UserCreateResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("createDingUser错误原因：{}", response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("创建钉钉人员信息错误:" + e.getErrMsg());
        } 
        return response;
    }

    @Override
    public OapiV2UserUpdateResponse updateDingUser(PsDingUser dingUser, String dingDeptId, Map<String, String> mobileMap, String token) {
        log.info("开始更新钉钉人员信息：{}，dingDeptId：{}", dingUser, dingDeptId);
        DefaultDingTalkClient client = new DefaultDingTalkClient(UrlConstant.UPDATE_USER_URL);
        OapiV2UserUpdateRequest req = new OapiV2UserUpdateRequest();
        req.setUserid(dingUser.getDingUserId());
        req.setName(dingUser.getName());
        req.setHideMobile(false);
        req.setTitle(dingUser.getJobname());
        req.setJobNumber(dingUser.getCode());
        // 设置扩展属性
        Map<String, String> extensionInfo = new ConcurrentHashMap<>(6);
        extensionInfo.put("岗位", dingUser.getPostname() != null ? dingUser.getPostname() : "");
        extensionInfo.put("职务", dingUser.getJobname() != null ? dingUser.getJobname() : "");
        extensionInfo.put("人员状态", dingUser.getPstype() != null ? dingUser.getPstype() : "");
        extensionInfo.put("员工编码", dingUser.getCode() != null ? dingUser.getCode() : "");
        if (mobileMap.containsKey(dingUser.getPkPsdoc())) {
            extensionInfo.put("联系方式", mobileMap.get(dingUser.getPkPsdoc()));
        }
        req.setExtension(JSONUtil.toJsonStr(extensionInfo));
        req.setDeptIdList(dingDeptId);
        req.setLanguage("zh_CN");
        OapiV2UserUpdateResponse response = null;
        try {
            response = client.execute(req, token);
            Thread.sleep(10);
            if (response.getErrcode() == NumberConstant.ZERO) {
                return response;
            } else {
                log.info("updateDingUser错误原因：{}", response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("更新钉钉人员信息错误:" + e.getErrMsg());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public OapiV2UserGetbymobileResponse getUserIdByMobile(String mobile, String token) {
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.GET_USER_ID_BY_MOBILE_URL);
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(mobile);
        OapiV2UserGetbymobileResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("getUserIdByMobile错误原因：{}", response.getErrmsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public OapiV2UserDeleteResponse deleteUserInfo(String userId, String token) {
        log.info("开始删除钉钉人员userId：{}", userId);
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.DELETE_USER_URL);
        OapiV2UserDeleteRequest req = new OapiV2UserDeleteRequest();
        req.setUserid(userId);
        OapiV2UserDeleteResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("DeleteUserInfo错误原因:" + response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("删除钉钉人员信息错误:" + e.getErrMsg());
        }
        return response;
    }

    @Override
    public OapiV2DepartmentUpdateResponse updateDepartmentManager(Department department, String token) {
        log.info("开始同步钉钉部门主管：{}", department);
        DingTalkClient client = new DefaultDingTalkClient(UrlConstant.UPDATE_DEPT_URL);
        OapiV2DepartmentUpdateRequest req = new OapiV2DepartmentUpdateRequest();
        req.setDeptId(Long.parseLong(department.getDept_id()));
        req.setDeptManagerUseridList(department.getDept_manager_userid_list());
        OapiV2DepartmentUpdateResponse response = null;
        try {
            response = client.execute(req, token);
            if (response.getErrcode() == 0) {
                return response;
            } else {
                log.info("updateDepartmentManager错误原因:" + response.getErrmsg());
            }
        } catch (ApiException e) {
            log.info("获取token错误:" + e.getErrMsg());
        }
        return response;
    }
}

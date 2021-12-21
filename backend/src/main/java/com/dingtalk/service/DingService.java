package com.dingtalk.service;

import com.dingtalk.model.Department;
import com.dingtalk.model.PsDingUser;
import com.dingtalk.api.response.*;

import java.util.Map;

public interface DingService {

    OapiV2DepartmentCreateResponse createDepartment(Department department, String token);

    OapiV2DepartmentUpdateResponse updateDepartment(Department department, String token);

    OapiV2UserCreateResponse createDingUser(PsDingUser dingUser, String dingDeptId, String token);

    OapiV2UserUpdateResponse updateDingUser(PsDingUser dingUser, String dingDeptId, Map<String, String> mobileMap, String token);

    OapiV2UserGetbymobileResponse getUserIdByMobile(String mobile, String token);

    OapiV2UserDeleteResponse deleteUserInfo(String userId, String token);

    OapiV2DepartmentUpdateResponse updateDepartmentManager(Department department, String token);
}

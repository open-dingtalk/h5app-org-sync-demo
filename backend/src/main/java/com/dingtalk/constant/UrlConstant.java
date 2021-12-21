package com.dingtalk.constant;

/**
 * 钉钉开放接口网关常量
 */
public class UrlConstant {

    /**
     * 获取access_token url
     */
    public static final String GET_ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    /**
     * 创建部门url
     */
    public static final String CREATE_DEPT_URL = "https://oapi.dingtalk.com/topapi/v2/department/create";

    /**
     * 更新部门url
     */
    public static final String UPDATE_DEPT_URL = "https://oapi.dingtalk.com/topapi/v2/department/update";

    /**
     * 创建用户url
     */
    public static final String CREATE_USER_URL = "https://oapi.dingtalk.com/topapi/v2/user/create";

    /**
     * 更新用户url
     */
    public static final String UPDATE_USER_URL = "https://oapi.dingtalk.com/topapi/v2/user/update";

    /**
     * 根据手机号获取userId url
     */
    public static final String GET_USER_ID_BY_MOBILE_URL = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile";

    /**
     * 删除用户url
     */
    public static final String DELETE_USER_URL = "https://oapi.dingtalk.com/topapi/v2/user/delete";

}

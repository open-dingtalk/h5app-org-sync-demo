package com.dingtalk.model;

import lombok.Data;

import java.util.List;

/**
 * 部门
 * 可以自行添加需要传入的字段
 */
@Data
public class Department {

    private String dept_id;

    private String name;

    private Long parent_id;

    private Boolean hide_dept;

    private String dept_permits;

    private String user_permits;

    private Boolean outer_dept;

    private Boolean outer_dept_only_self;

    private String outer_permit_users;

    private String outer_permit_depts;

    private Boolean create_dept_group;

    private Boolean auto_approve_apply;

    private Long order;

    private String source_identifier;

    private String dept_manager_userid_list;
}

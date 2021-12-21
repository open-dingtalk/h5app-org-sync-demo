package com.dingtalk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ncc用户
 *
 * @author WY
 * @date 2021/09/07
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ps_user_dto")
public class PsUserDto {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 人员主键（唯一值）
     */
    @Column
    private String pkPsndoc;

    /**
     * 人员姓名
     */
    @Column
    private String name;

    /**
     * 人员编码（非唯一值）
     */
    @Column
    private String code;

    /**
     * 删除标志
     * 1：删除；
     * 0：正常
     */
    @Column
    private Integer dr;

    /**
     * 组织主键（唯一值）
     */
    @Column
    private String pkOrg;

    /**
     * 部门主键（唯一值）
     */
    @Column
    private String pkDept;

    /**
     * 部门名称
     */
    @Column
    private String deptname;

    /**
     * 人员类别
     */
    @Column
    private String pstype;

    /**
     * 岗位名称
     */
    @Column
    private String postname;

    /**
     * 岗位编码
     */
    @Column
    private String postcode;

    /**
     * 职务
     */
    @Column
    private String jobname;

    /**
     * 手机号
     */
    @Column
    private String mobile;

    /**
     * 人员状态
     * 1：未启用
     * 2：已启用
     * 3：已停用
     */
    @Column
    private Integer enablestate;

    /**
     * 钉钉的用户id
     */
    @Column
    private String dingUserId;

    /**
     * 钉钉UnionId
     */
    @Column
    private String dingUnionId;

    /**
     * 部门级别
     */
    @Column
    private Integer assgid;

    /**
     * 部门标识符
     * N:有效
     * Y:无效
     */
    @Column
    private String endflag;

    /**
     * 更新时间
     */
    @Column
    private Date ts;


    /**
     * 是否挂起：N：非挂起；Y：挂起
     */
    @Column
    private String glbdef5;
}

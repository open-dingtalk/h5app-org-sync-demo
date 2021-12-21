package com.dingtalk.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ncc部门
 *
 * @author WY
 * @date 2021/09/07
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ps_dept_dto")
public class PsDeptDto implements Serializable {

    /**
     * 部门ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 部门名称
     */
    @Column
    private String name;

    /**
     * 部门编码（非唯一值）
     */
    @Column
    private String code;

    /**
     * 部门主键（唯一值）
     */
    @Column
    private String pkDept;

    /**
     * 组织主键（唯一值）
     */
    @Column
    private String pkOrg;

    /**
     * 部门负责人主键（唯一值）
     */
    @Column
    private String principal;

    /**
     * 部门负责人编码（非唯一值）
     */
    @Column
    private String pcode;

    /**
     * 部门负责人名称
     */
    @Column
    private String pname;

    /**
     * 上级部门主键（唯一值）
     */
    @Column
    private String popk;

    /**
     * 上级部门编码（非唯一值）
     */
    @Column
    private String pocode;

    /**
     * 上级部门名称
     */
    @Column
    private String poname;

    /**
     * 上级部门负责人
     */
    @Column
    private String pfname;

    /**
     * 上级部门负责人编码
     */
    @Column
    private String pfcode;

    /**
     * 分管领导编码
     */
    @Column
    private String fgldcode;

    /**
     * 分管领导主键
     */
    @Column
    private String fgld;

    /**
     * 所属子集团名称
     */
    @Column
    private String groupname;

    /**
     * 所属子集团编码
     */
    @Column
    private String groupno;

    /**
     * 钉钉部门id
     */
    @Column
    private String dingDeptId;


    /**
     * 删除标志
     *      1：删除
     *      0：正常
     */
    @Column
    private Integer dr;

    /**
     * 更新时间
     */
    @Column
    private Date ts;

}

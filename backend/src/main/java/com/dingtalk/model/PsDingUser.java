package com.dingtalk.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "ps_ding_user")
public class PsDingUser implements Serializable {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    /**
     * 人员主键
     */
    @Column
    private String pkPsdoc;

    /**
     * 人员姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 人员编码（非唯一值）
     */
    @Column
    private String code;

    /**
     * 删除标志 0：正常；1：删除
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
     * 人员状态 1：未启用；2：已启用；3：已停用
     */
    @Column
    private Integer enablestate;

    /**
     * 钉钉用户ID
     */
    @Column
    private String dingUserId;

    /**
     * 钉钉用户unionId
     */
    @Column
    private String dingUnionId;

    /**
     * 钉钉部门id
     */
    @Column
    private String dingDeptId;

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
     * 人员挂起标志：
     * N: 非挂起
     * Y: 挂起
     */
    @Column
    private String glbdef5;

    /**
     * 更新时间
     */
    @Column
    private LocalDateTime ts;

    /**
     * 创建时间
     */
    @Column
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column
    private LocalDateTime updateTime;

    /**
     * 同步结果
     */
    @Column
    private String syncResult;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        PsDingUser that = (PsDingUser) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

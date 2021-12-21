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
@Table(name = "ps_ding_dept")
public class PsDingDept implements Serializable {

    /**
     * 部门ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

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
     * 删除标志 0：正常；1：删除
     */
    @Column
    private Integer dr;

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
        PsDingDept that = (PsDingDept) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

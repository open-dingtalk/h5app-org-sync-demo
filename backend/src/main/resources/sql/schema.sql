DROP TABLE IF EXISTS `ps_ding_dept`;
CREATE TABLE `ps_ding_dept`
(
    `id`           varchar(64) COMMENT '部门ID',
    `name`         varchar(30) COMMENT '部门名称',
    `code`         varchar(20) COMMENT '部门编码（非唯一值）',
    `pk_dept`      varchar(32) COMMENT '部门主键（唯一值）',
    `pk_org`       varchar(32) COMMENT '组织主键（唯一值）',
    `principal`    varchar(32) COMMENT '部门负责人主键（唯一值）',
    `pcode`        varchar(20) COMMENT '部门负责人编码（非唯一值）',
    `pname`        varchar(30) COMMENT '部门负责人名称',
    `popk`         varchar(32) COMMENT '上级部门主键（唯一值）',
    `pocode`       varchar(20) COMMENT '上级部门编码（非唯一值）',
    `poname`       varchar(30) COMMENT '上级部门名称',
    `pfname`       varchar(30) COMMENT '上级部门负责人',
    `pfcode`       varchar(20) COMMENT '上级部门负责人编码',
    `fgldcode`     varchar(20) COMMENT '分管领导编码',
    `fgld`         varchar(32) COMMENT '分管领导主键',
    `groupname`    varchar(30) COMMENT '所属子集团名称',
    `groupno`      varchar(20) COMMENT '所属子集团编码',
    `ding_dept_id` varchar(64) COMMENT '钉钉部门id',
    `dr`           tinyint COMMENT '删除标志 0：正常；1：删除',
    `ts`           datetime COMMENT '更新时间',
    `create_time`  datetime COMMENT '创建时间',
    `update_time`  datetime COMMENT '更新时间',
    `sync_result`  varchar(255) COMMENT '同步结果',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `ps_ding_user`;
CREATE TABLE `ps_ding_user`
(
    `id`            varchar(64) NOT NULL COMMENT '主键ID',
    `pk_psdoc`      varchar(32) COMMENT '人员主键',
    `name`          varchar(30) COMMENT '人员姓名',
    `code`          varchar(20) COMMENT '人员编码（非唯一值）',
    `dr`            tinyint     NULL COMMENT '删除标志 0：正常；1：删除',
    `pk_org`        varchar(32) COMMENT '组织主键（唯一值）',
    `pk_dept`       varchar(32) COMMENT '部门主键（唯一值）',
    `deptname`      varchar(30) COMMENT '部门名称',
    `pstype`        varchar(30) COMMENT '人员类别',
    `postname`      varchar(30) COMMENT '岗位名称',
    `postcode`      varchar(20) COMMENT '岗位编码',
    `jobname`       varchar(30) COMMENT '职务',
    `mobile`        varchar(20) COMMENT '手机号',
    `enablestate`   tinyint COMMENT '人员状态 1：未启用；2：已启用；3：已停用',
    `ding_user_id`  varchar(64) COMMENT '钉钉用户ID',
    `ding_union_id` varchar(64) COMMENT '钉钉用户unionId',
    `ding_dept_id`  varchar(64) COMMENT '钉钉部门id',
    `assgid`        int COMMENT '部门辅助字段',
    `endflag`       varchar(2) COMMENT '部门是否生效，N：生效；Y：失效',
    `ts`            datetime COMMENT '更新时间',
    `create_time`   datetime COMMENT '创建时间',
    `update_time`   datetime COMMENT '更新时间',
    `sync_result`   varchar(255) COMMENT '同步结果',
    `glbdef5`       varchar(2) COMMENT '挂起状态，N：非挂起；Y：挂起',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `ps_dept_dto`;
CREATE TABLE `ps_dept_dto`
(
    `id`           bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
    `name`         varchar(30) COMMENT '部门名称',
    `code`         varchar(20) COMMENT '部门编码（非唯一值）',
    `pk_dept`      varchar(32) COMMENT '部门主键（唯一值）',
    `pk_org`       varchar(32) COMMENT '组织主键（唯一值）',
    `principal`    varchar(32) COMMENT '部门负责人主键（唯一值）',
    `pcode`        varchar(20) COMMENT '部门负责人编码（非唯一值）',
    `pname`        varchar(30) COMMENT '部门负责人名称',
    `popk`         varchar(32) COMMENT '上级部门主键（唯一值）',
    `pocode`       varchar(20) COMMENT '上级部门编码（非唯一值）',
    `poname`       varchar(30) COMMENT '上级部门名称',
    `pfname`       varchar(30) COMMENT '上级部门负责人',
    `pfcode`       varchar(20) COMMENT '上级部门负责人编码',
    `fgldcode`     varchar(20) COMMENT '分管领导编码',
    `fgld`         varchar(32) COMMENT '分管领导主键',
    `groupname`    varchar(30) COMMENT '所属子集团名称',
    `groupno`      varchar(20) COMMENT '所属子集团编码',
    `ding_dept_id` varchar(64) COMMENT '钉钉部门id',
    `dr`           tinyint COMMENT '删除标志 0：正常；1：删除',
    `ts`           datetime COMMENT '更新时间',
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `ps_user_dto`;
CREATE TABLE `ps_user_dto`
(
    `id`            bigint  NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `pk_psndoc`     varchar(32) COMMENT '人员主键',
    `name`          varchar(30) COMMENT '人员姓名',
    `code`          varchar(20) COMMENT '人员编码（非唯一值）',
    `dr`            tinyint NULL COMMENT '删除标志 0：正常；1：删除',
    `pk_org`        varchar(32) COMMENT '组织主键（唯一值）',
    `pk_dept`       varchar(32) COMMENT '部门主键（唯一值）',
    `deptname`      varchar(30) COMMENT '部门名称',
    `pstype`        varchar(30) COMMENT '人员类别',
    `postname`      varchar(30) COMMENT '岗位名称',
    `postcode`      varchar(20) COMMENT '岗位编码',
    `jobname`       varchar(30) COMMENT '职务',
    `mobile`        varchar(20) COMMENT '手机号',
    `enablestate`   tinyint COMMENT '人员状态 1：未启用；2：已启用；3：已停用',
    `ding_user_id`  varchar(64) COMMENT '钉钉用户ID',
    `ding_union_id` varchar(64) COMMENT '钉钉用户unionId',
    `assgid`        int COMMENT '部门辅助字段',
    `endflag`       varchar(2) COMMENT '部门是否生效，N：生效；Y：失效',
    `ts`            datetime COMMENT '更新时间',
    `glbdef5`       varchar(2) COMMENT '挂起状态，N：非挂起；Y：挂起',
    PRIMARY KEY (`id`)
);


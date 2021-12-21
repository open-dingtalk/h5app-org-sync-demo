INSERT INTO `ps_dept_dto` (`name`, `code`, `pk_dept`, `pk_org`, `principal`, `pcode`, `pname`, `popk`, `pocode`,
                           `poname`, `pfname`, `pfcode`, `fgldcode`, `fgld`, `groupname`, `groupno`, `ding_dept_id`,
                           `dr`, `ts`)
VALUES ('技术部测试用', '1012', '1001A1100000000007PM', '0001A110000000001V4D', '1001A110000000000W88', 'renjd11', '王五',
        '1', NULL, NULL, NULL, NULL, 'renjd11', '1001A110000000000W88', '组织同步', '10', '548330356', 0, NULL);

INSERT INTO `ps_dept_dto` (`name`, `code`, `pk_dept`, `pk_org`, `principal`, `pcode`, `pname`, `popk`, `pocode`,
                           `poname`, `pfname`, `pfcode`, `fgldcode`, `fgld`, `groupname`, `groupno`, `ding_dept_id`,
                           `dr`, `ts`)
VALUES ('招标部测试用', '1005', '1001A1100000000007MI', '0001A110000000001V4D', '1001A1100000000029HO', 'gouk59', '李四',
        '1', NULL, NULL, NULL, NULL, 'gouk59', '1001A1100000000029HO', '组织同步', '10', '548077534', 0, NULL);

INSERT INTO `ps_dept_dto` (`name`, `code`, `pk_dept`, `pk_org`, `principal`, `pcode`, `pname`, `popk`, `pocode`,
                           `poname`, `pfname`, `pfcode`, `fgldcode`, `fgld`, `groupname`, `groupno`, `ding_dept_id`,
                           `dr`, `ts`)
VALUES ('市场部测试用', '1006', '1001A1100000000007MP', '0001A110000000001V4D', '1001A1100000000029G2', 'yangs67', '张三',
        '1', NULL, NULL, NULL, NULL, 'yangs67', '1001A1100000000029G2', '组织同步', '10', '548471277', 0, NULL);


INSERT INTO `ps_user_dto` (`pk_psndoc`, `name`, `code`, `dr`, `pk_org`, `pk_dept`, `deptname`, `pstype`, `postname`,
                           `jobname`, `mobile`, `enablestate`, `ding_user_id`, `ding_union_id`, `assgid`, `endflag`,
                           `ts`, `glbdef5`)
VALUES ('1001A1100000000029G2', '张三', 'yangs67', 0, '0001A110000000001V4D', '1001A1100000000007MP', '市场部', '在职_正式员工',
        '部长', '部长', '+86-18588888888', 2, NULL, NULL, 1, 'N', '2021-10-07 21:25:04', 'N');

INSERT INTO `ps_user_dto` (`pk_psndoc`, `name`, `code`, `dr`, `pk_org`, `pk_dept`, `deptname`, `pstype`, `postname`,
                           `jobname`, `mobile`, `enablestate`, `ding_user_id`, `ding_union_id`, `assgid`, `endflag`,
                           `ts`, `glbdef5`)
VALUES ('1001A1100000000029HO', '李四', 'gouk59', 0, '0001A110000000001V4D', '1001A1100000000007MI', '招标部', '在职_正式员工',
        '部长', '部长', '+86-18688888888', 2, NULL, NULL, 1, 'N', '2021-09-29 19:58:37', 'N');

INSERT INTO `ps_user_dto` (`pk_psndoc`, `name`, `code`, `dr`, `pk_org`, `pk_dept`, `deptname`, `pstype`, `postname`,
                           `jobname`, `mobile`, `enablestate`, `ding_user_id`, `ding_union_id`, `assgid`, `endflag`,
                           `ts`, `glbdef5`)
VALUES ('1001A110000000000W88', '王五', 'renjd11', 0, '0001A110000000001V4D', '1001A1100000000007PM', '技术部', '在职_正式员工',
        '技术总工', '技术总工', '+86-19088888888', 2, NULL, NULL, 1, 'N', '2021-10-09 16:23:04', 'N');


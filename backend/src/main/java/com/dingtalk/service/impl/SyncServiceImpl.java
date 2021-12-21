package com.dingtalk.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.dingtalk.api.response.*;
import com.dingtalk.config.AppConfig;
import com.dingtalk.constant.NumberConstant;
import com.dingtalk.constant.StringConstant;
import com.dingtalk.model.*;
import com.dingtalk.service.*;
import com.dingtalk.util.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    DingService dingService;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    PsDingDeptService psDingDeptService;

    @Autowired
    PsDeptDtoService psDeptDtoService;

    @Autowired
    PsDingUserService psDingUserService;

    @Autowired
    PsUserDtoServcie psUserDtoServcie;

    @Override
    public void sync() {
        // 同步部门
        syncDept();
        // 同步用户
        syncUser();
        // 同步部门经理
        syncDeptManager();
    }

    @Override
    public List<PsDingDept> syncDept() {
        String token = AccessTokenUtil.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
        // PS返回数据
        List<PsDeptDto> psDeptDtoList = psDeptDtoService.findAll();
        // 数据库中存储的部门数据
        List<PsDingDept> psDingDepts = psDingDeptService.findAll();
        // 获取两者不重复的，同步到钉钉
        List<PsDingDept> savePsDingDepts = new ArrayList<>();

        psDeptDtoList.stream().filter(psDeptDto -> !psDingDepts.stream().map(PsDingDept::getPkDept).collect(Collectors.toList()).contains(psDeptDto.getPkDept())).forEach(psDeptDto -> {
            PsDingDept psDingDept = new PsDingDept();
            BeanUtils.copyProperties(psDeptDto, psDingDept);
            psDingDept.setCreateTime(LocalDateTime.now());
            // 同步钉钉部门
            Long order = 0L;
            try {
                order = Long.parseLong(psDingDept.getCode());
            } catch (Exception e) {
                order = 0L;
            }
            String dingDeptId = syncDingDept(psDingDept.getName() + " " + psDingDept.getPkDept(), order, token);
            psDingDept.setDingDeptId(dingDeptId);
            savePsDingDepts.add(psDingDept);
        });
        // 重复的编辑
        psDingDepts.forEach(psDingDept -> {
            Optional<PsDeptDto> psDeptDto = psDeptDtoList.stream().filter(psDept -> psDept.getPkDept().equals(psDingDept.getPkDept())).findFirst();
            psDeptDto.ifPresent(deptDto -> {
                String dingDeptId = psDingDept.getDingDeptId();
                BeanUtils.copyProperties(deptDto, psDingDept);
                psDingDept.setDingDeptId(dingDeptId);
            });
        });

        // 编辑钉钉用户部门信息
        List<PsDingDept> allPsDingDept = new ArrayList<PsDingDept>();
        allPsDingDept.addAll(savePsDingDepts);
        allPsDingDept.addAll(psDingDepts);

        updateDingDept(allPsDingDept, token);
        // 同步部门数据保存至本地数据库中
        psDingDeptService.saveAll(allPsDingDept);
        return allPsDingDept;
    }

    @Override
    public List<PsDingUser> syncUser() {
        String token = AccessTokenUtil.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
        // 获取需要同步的PS用户数据
        List<PsUserDto> psUserDtoList = psUserDtoServcie.findAll();

        List<PsUserDto> filterPsUserDtoList = new ArrayList<>();
        Set<String> filterKeySet = new HashSet<>();
        psUserDtoList.forEach(psUserDto -> {
            String key = psUserDto.getPkPsndoc() + "-" + psUserDto.getPkDept();
            if (!filterKeySet.contains(key)) {
                filterPsUserDtoList.add(psUserDto);
                filterKeySet.add(key);
            }
        });
        psUserDtoList = filterPsUserDtoList;

        // 获取PS人员主键
        List<String> psUserIds = psUserDtoList.stream().map(PsUserDto::getPkPsndoc).collect(Collectors.toList());

        // 根据PS人员主键获取数据库中的人员信息
        List<PsDingUser> psDingUserList = psDingUserService.findAll();

        // 需要同步的用人员信息
        List<PsDingUser> allDingUser = new ArrayList<>();

        // 获取PS人员中的部门主键
        Set<String> ncDeptIds = psUserDtoList.stream().map(PsUserDto::getPkDept).collect(Collectors.toSet());

        // 根据PS部门主键获取数据库中的部门信息
        List<PsDingDept> psDingDepts = psDingDeptService.findAll();

        Map<String, String> mobileMap = new ConcurrentHashMap<>(4);

        // 结合数据库人员信息以及获取的PS人员信息进行数据的合并、处理
        unionPsDingUserData(psUserDtoList, psUserIds, psDingUserList, allDingUser, psDingDepts, mobileMap);
        // 同步Ps用户到钉钉中
        syncDingUser(allDingUser, token, mobileMap);
        // 同步用户数据到本地数据库中
        psDingUserService.saveAll(allDingUser);
        return allDingUser;

    }

    @Override
    public List<PsDingDept> syncDeptManager() {
        String token = AccessTokenUtil.getAccessToken(appConfig.getAppKey(), appConfig.getAppSecret());
        // 部门
        List<PsDingDept> psDingDeptList = psDingDeptService.findAll();

        // PS部门经理主键
        Set<String> principalIdSet = psDingDeptList.stream().map(PsDingDept::getPrincipal).collect(Collectors.toSet());

        // 根据部门经理主键从数据库中获取用户信息
        List<PsDingUser> psDingUserList = psDingUserService.findAll();

        psDingDeptList.forEach(psDeptDto -> {
            Optional<PsDingUser> principal = psDingUserList.stream().filter(psDingUser -> psDingUser.getPkPsdoc().equals(psDeptDto.getPrincipal())).findFirst();
            if (principal.isPresent()) {
                Department department = new Department();
                department.setDept_id(psDeptDto.getDingDeptId());
                department.setDept_manager_userid_list(principal.get().getDingUserId());
                dingService.updateDepartmentManager(department, token);
            }
        });
        return psDingDeptList;
    }

    /**
     * 同步钉钉部门
     *
     * @param deptName 部门名称
     * @param order    排序
     * @param token    令牌
     * @return {@link String}
     */
    private String syncDingDept(String deptName, Long order, String token) {
        Department department = new Department();
        department.setName(deptName);
        department.setParent_id(1L);
        department.setOrder(order);
        OapiV2DepartmentCreateResponse createDepartmentResult = dingService.createDepartment(department, token);
        if (createDepartmentResult.getErrcode() == NumberConstant.ZERO) {
            return createDepartmentResult.getResult().getDeptId().toString();
        } else {
            return "";
        }
    }

    /**
     * 更新钉钉部门
     *
     * @param allPsDingDept 待同步部门
     * @param token         令牌
     */
    private void updateDingDept(List<PsDingDept> allPsDingDept, String token) {
        log.info("更新钉钉部门开始{}:{}", Thread.currentThread().getName(), DateUtil.now());
        // 过滤钉钉ID不存在的部门
        List<PsDingDept> psDingDepts = allPsDingDept.stream().filter(psDingDept -> CharSequenceUtil.isNotEmpty(psDingDept.getDingDeptId())).collect(Collectors.toList());
        psDingDepts.forEach(psDingDept -> {
            Department department = new Department();
            department.setDept_id(psDingDept.getDingDeptId());
            department.setName(psDingDept.getName());
            try {
                department.setOrder(Long.parseLong(psDingDept.getCode()));
            } catch (Exception e) {
                department.setOrder(0L);
            }
            String parentId = "";
            // 获取父级部门ID
            Optional<PsDingDept> ncDingDeptOptional = psDingDepts.stream().filter(psDingDept1 -> psDingDept1.getPkDept().equals(psDingDept.getPopk())).findFirst();
            if (ncDingDeptOptional.isPresent()) {
                parentId = ncDingDeptOptional.get().getDingDeptId();
            } else {
                parentId = psDingDept.getPopk();
            }
            // 删除隐藏部门
            if (psDingDept.getDr() == NumberConstant.ONE) {
                department.setHide_dept(Boolean.TRUE);
            }
            if (CharSequenceUtil.isNotEmpty(parentId)) {
                department.setParent_id(Long.parseLong(parentId));
            }
            log.info("更新部门信息：{}", department);
            OapiV2DepartmentUpdateResponse response = dingService.updateDepartment(department, token);
            if (response != null) {
                psDingDept.setSyncResult(response.getErrcode() == NumberConstant.ZERO ? StringConstant.SUCCESS : response.getErrmsg());
            }
        });
        log.info("更新钉钉部门结束{}:{}", Thread.currentThread().getName(), DateUtil.now());
    }

    /**
     * 结合数控丁用户数据
     * 获取并集数据
     *
     * @param psUserDtoList  ps用户dto列表
     * @param psUserIds     ps用户id
     * @param psDingUserList 数控丁用户列表
     * @param allDingUser    丁所有用户
     * @param psDingDepts    数控丁部门
     * @param mobileMap      手机号
     */
    private void unionPsDingUserData(List<PsUserDto> psUserDtoList, List<String> psUserIds, List<PsDingUser> psDingUserList, List<PsDingUser> allDingUser, List<PsDingDept> psDingDepts, Map<String, String> mobileMap) {
        psUserDtoList.forEach(psUserDto -> {
            // 获取数据库中已存在用户 存在=>编辑 不存在=>新增
            Optional<PsDingUser> ncDingUserOptional = psDingUserList.stream().filter(psDingUser1 -> psDingUser1.getPkPsdoc().equals(psUserDto.getPkPsndoc()) && psDingUser1.getAssgid().equals(psUserDto.getAssgid())).findFirst();
            PsDingUser ncDingUser = new PsDingUser();
            BeanUtils.copyProperties(psUserDto, ncDingUser);
            ncDingUser.setPkPsdoc(psUserDto.getPkPsndoc());
            if (ncDingUserOptional.isPresent()) {
                PsDingUser psDingUser1 = ncDingUserOptional.get();
                ncDingUser.setId(psDingUser1.getId());
                ncDingUser.setDingUserId(psDingUser1.getDingUserId());
                ncDingUser.setDingUnionId(psDingUser1.getDingUnionId());
                if (StringUtils.isNotEmpty(psDingUser1.getDingDeptId()) && psDingUser1.getPkDept().equals(psUserDto.getPkDept())) {
                    ncDingUser.setDingDeptId(psDingUser1.getDingDeptId());
                } else {
                    Optional<PsDingDept> ncDingDeptOptional = psDingDepts.stream().filter(psDingDept -> psDingDept.getPkDept().equals(psUserDto.getPkDept())).findFirst();
                    if (ncDingDeptOptional.isPresent()) {
                        PsDingDept psDingDept = ncDingDeptOptional.get();
                        ncDingUser.setDingDeptId(psDingDept.getDingDeptId());
                        psDingUser1.setPkDept(psDingDept.getPkDept());
                        psDingUser1.setDingDeptId(psDingDept.getDingDeptId());
                    }
                }
                if (!psDingUser1.getMobile().equals(ncDingUser.getMobile())) {
                    mobileMap.put(ncDingUser.getPkPsdoc(), ncDingUser.getMobile());
                }
                ncDingUser.setUpdateTime(LocalDateTime.now());
            } else {
                // 获取当前用户所在部门对应钉钉部门ID
                Optional<PsDingDept> ncDingDeptOptional = psDingDepts.stream().filter(psDingDept -> psDingDept.getPkDept().equals(psUserDto.getPkDept())).findFirst();
                if (ncDingDeptOptional.isPresent()) {
                    PsDingDept psDingDept = ncDingDeptOptional.get();
                    ncDingUser.setDingDeptId(psDingDept.getDingDeptId());
                }
                ncDingUser.setCreateTime(LocalDateTime.now());
            }
            if (StringConstant.Y_STR.equals(psUserDto.getEndflag())) {
                ncDingUser.setEnablestate(NumberConstant.THREE);
            }
            ncDingUser.setTs(psUserDto.getTs().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            ncDingUser.setPkPsdoc(psUserDto.getPkPsndoc());
            allDingUser.add(ncDingUser);
        });

        // 获取allDingUser和ncDingUserList的交集
        Map<String, Set<String>> psKey = new HashMap<>(psUserIds.size());
        allDingUser.forEach(psDingUser -> {
            String pkPsdoc = psDingUser.getPkPsdoc();
            String pkDept = psDingUser.getPkDept();
            if (!psKey.containsKey(pkPsdoc)) {
                psKey.put(pkPsdoc, new HashSet<>());
            }
            if (!psKey.get(pkPsdoc).contains(pkDept)) {
                psKey.get(pkPsdoc).add(pkDept);
            }
        });
        psDingUserList.forEach(psDingUser -> {
            String pkPsdoc = psDingUser.getPkPsdoc();
            String pkDept = psDingUser.getPkDept();
            if (psKey.containsKey(pkPsdoc) && !psKey.get(pkPsdoc).contains(pkDept)) {
                allDingUser.add(psDingUser);
            }
        });
    }

    /**
     * 用户同步叮
     * 同步钉钉用户
     *
     * @param allDingUser      同步用户
     * @param token            令牌
     */
    private void syncDingUser(List<PsDingUser> allDingUser, String token, Map<String, String> mobileMap) {
        // 去重 合并 排序
        allDingUser.sort(Comparator.comparing(PsDingUser::getTs).reversed());
        // 根据用户ID分组 待处理用户
        Map<String, List<PsDingUser>> pendingUser = allDingUser.stream().collect(Collectors.groupingBy(PsDingUser::getPkPsdoc));
        pendingUser.forEach((k, v) -> {
            int size = v.size();
            // 取最新一个
            PsDingUser dingUser = v.get(0);
            String dingUserId = dingUser.getDingUserId();
            if (size != NumberConstant.ONE) {
                for (int i = 0; i < v.size(); i++) {
                    if (StringUtils.isNotEmpty(v.get(i).getDingUserId())) {
                        dingUserId = v.get(i).getDingUserId();
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(dingUserId)) {
                    for (int i = 0; i < v.size(); i++) {
                        if (StringUtils.isEmpty(v.get(i).getDingUserId())) {
                            v.get(i).setDingUserId(dingUserId);
                        }
                    }
                }
            }

            // 获取不需要删除的用户
            List<PsDingUser> users = v.stream().filter(psDingUser -> psDingUser.getEnablestate() != NumberConstant.THREE && !StringConstant.Y_STR.equals(psDingUser.getGlbdef5())).collect(Collectors.toList());
            // 获取部门ID
            String dingDeptId = users.stream().map(PsDingUser::getDingDeptId).collect(Collectors.joining(","));

            if (StringUtils.isEmpty(dingUserId) && users.size() != NumberConstant.ZERO) {
                OapiV2UserCreateResponse response = dingService.createDingUser(dingUser, dingDeptId, token);
                if (response != null) {
                    if (response.getErrcode() == NumberConstant.ZERO) {
                        for (int i = 0; i < v.size(); i++) {
                            v.get(i).setDingUnionId(response.getResult().getUnionId());
                            v.get(i).setDingUserId(response.getResult().getUserid());
                            v.get(i).setSyncResult(StringConstant.SUCCESS);
                        }
                    } else if (response.getErrcode() == NumberConstant.DING_USER_HAVE) {
                        OapiV2UserGetbymobileResponse response1 = dingService.getUserIdByMobile(dingUser.getMobile(), token);
                        if (response1.getErrcode() == NumberConstant.ZERO) {
                            dingUserId = response1.getResult().getUserid();
                            dingUser.setDingUserId(dingUserId);
                            dingService.updateDingUser(dingUser, dingDeptId, mobileMap, token);
                            for (int i = 0; i < v.size(); i++) {
                                v.get(i).setDingUserId(dingUserId);
                                v.get(i).setSyncResult(StringConstant.SUCCESS);
                            }
                        } else {
                            for (int i = 0; i < v.size(); i++) {
                                v.get(i).setSyncResult(response1.getErrmsg());
                            }
                        }
                    } else {
                        for (int i = 0; i < v.size(); i++) {
                            v.get(i).setSyncResult(response.getErrmsg());
                        }
                    }
                }
            } else {
                // 只有1个部门并且删除状态==>删除用户
                // 从通过dr来判断是否删除，改成通过enablestate来判断，enablestate=3删除
                // 删除标志增加一个glbdef5,glbdef5=Y也删除
                if (StringUtils.isNotEmpty(dingUserId)) {
                    if (users.size() == NumberConstant.ZERO) {
                        OapiV2UserDeleteResponse response = dingService.deleteUserInfo(dingUserId, token);
                        if (response != null) {
                            for (int i = 0; i < v.size(); i++) {
                                v.get(i).setDingUserId(null);
                                v.get(i).setDingUnionId(null);
                                v.get(i).setDingDeptId(null);
                                v.get(i).setSyncResult(response.getErrcode() == NumberConstant.ZERO ? StringConstant.SUCCESS : response.getErrmsg());
                            }
                        }
                    } else {
                        OapiV2UserUpdateResponse response = dingService.updateDingUser(dingUser, dingDeptId, mobileMap, token);
                        if (response != null) {
                            for (int i = 0; i < v.size(); i++) {
                                v.get(i).setSyncResult(response.getErrcode() == NumberConstant.ZERO ? StringConstant.SUCCESS : response.getErrmsg());
                            }
                        }
                    }
                }
            }
        });
    }

}

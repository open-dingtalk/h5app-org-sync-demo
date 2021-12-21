package com.dingtalk.service;

import com.dingtalk.model.PsDingDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsDingDeptService extends JpaRepository<PsDingDept, Long> {
}

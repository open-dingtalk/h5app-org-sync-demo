package com.dingtalk.service;

import com.dingtalk.model.PsDeptDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsDeptDtoService extends JpaRepository<PsDeptDto, Long> {
}

package com.dingtalk.service;

import com.dingtalk.model.PsUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsUserDtoServcie extends JpaRepository<PsUserDto, Long> {
}

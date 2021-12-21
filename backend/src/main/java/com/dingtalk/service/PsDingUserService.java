package com.dingtalk.service;

import com.dingtalk.model.PsDingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsDingUserService extends JpaRepository<PsDingUser, Long> {
}

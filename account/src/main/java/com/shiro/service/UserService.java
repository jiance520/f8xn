package com.shiro.service;

import com.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserService {
	public User findByName(String name);
	public List<String> findPermissionByUserId(Integer userId);
}

package com.shiro.service.impl;

import com.dao.UserMapper;
import com.entity.User;
import com.shiro.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Transactional
@MapperScan(basePackages = "com.dao")
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	@Override
	public User findByName(String name) {

		return userMapper.findByName(name);
	}
	@Override
	public List<String> findPermissionByUserId(Integer userId) {
		return userMapper.findPermissionByUserId(userId);
	}
}

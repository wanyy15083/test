package com.test.service;

import com.test.entity.TestUser;

import java.util.List;

public interface TestUserService {
	public void findUser();

	public void insertUser();

	public List<TestUser> findAllUser();

	public String insertDupliUser();
}

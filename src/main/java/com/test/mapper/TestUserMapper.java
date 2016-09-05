package com.test.mapper;

import com.test.entity.TestUser;

import java.util.List;

public interface TestUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestUser record);

    int insertSelective(TestUser record);

    TestUser selectByPrimaryKey(Integer id);

    List<TestUser> selectAll();

    int updateByPrimaryKeySelective(TestUser record);

    int updateByPrimaryKey(TestUser record);

    void selectIntoFile();
}
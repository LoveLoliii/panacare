package com.panacealab.panacare.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author loveloliii
 * @date 2019/5/14.
 */
@Mapper
public interface EmailSubDao {
    /**
     * 向数据库插入邮箱记录
     * @param email 邮箱
     * */
    void insert(String email);
}

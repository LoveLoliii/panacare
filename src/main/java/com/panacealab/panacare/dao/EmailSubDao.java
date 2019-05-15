package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.EmailSubscribe;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 查询某一邮箱信息
     * @param email 邮箱
     * @return EmailSubscribe
     */
    List<EmailSubscribe> query(String email);
}

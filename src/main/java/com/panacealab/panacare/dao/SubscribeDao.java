package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.SubscribeInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface SubscribeDao {
    @Select("SELECT * FROM subscribe_info WHERE user_uniq_id = #{user_uniq_id}")
    List<SubscribeInfo> query(String user_uniq_id);

    @Select("SELECT * FROM subscribe_info")
    List<SubscribeInfo> queryAll();

    //对于直接插入对象 可能不行 需要具体到属性进行插入 考虑使用xml
    @Insert("INSERT INTO subscribe_info")
    int insert(SubscribeInfo subscribeInfo);
}

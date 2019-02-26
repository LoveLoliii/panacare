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


    int insert(SubscribeInfo subscribeInfo);

    int update(SubscribeInfo subscribeInfo);

    /***
     * 查询重复数量
     * @param userUniqId uud
     * @param goodsUniqId gud
     * @return int 存在数量
     */
    int queryRS(String userUniqId, String goodsUniqId);
}

package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.RecommendInfo;
import com.panacealab.panacare.entity.RecommendReward;
import com.panacealab.panacare.entity.RecommendRewardRecord;
import com.panacealab.panacare.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface RecommendDao {

    @Select("SELECT user_uniq_id FROM user_info WHERE user_referee = #{user_referee}")
    String queryUniqId(String user_referee);

    @Insert("INSERT INTO  recommend_info")
    int insert(RecommendInfo recommendInfo);

    @Select("SELECT * FROM recommend_reward")
    List<RecommendReward> queryRecommendReward();
    @Select("SELECT * FROM recommend_reward_record")
    List<RecommendRewardRecord> queryRecommendRewardRecord();

    int updateRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);

    int insertRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);

    UserInfo queryByUserReferee(String user_uniq_id);

    @Select("SELECT count(*) FROM recommend_info WHERE user_uniq_id = #{user_uniq_id}")
    int queryRecommendRewardCount(String user_uniq_id);

}

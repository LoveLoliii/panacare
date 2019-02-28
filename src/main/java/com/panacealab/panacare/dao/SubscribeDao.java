package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.SubscribeInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Loveloliii
 */
@Mapper
public interface SubscribeDao {
    @Select("SELECT * FROM subscribe_info WHERE user_uniq_id = #{user_uniq_id} and subscribe_state = 'SING' ")
    List<SubscribeInfo> query(String user_uniq_id);

    @Select("SELECT * FROM subscribe_info")
    List<SubscribeInfo> queryAll();


    int insert(SubscribeInfo subscribeInfo);

    int update(SubscribeInfo subscribeInfo);

    /***
     * 查询重复数量
     * @param userUniqId uud
     * @param goodsUniqId gud
     * @return SubscribeInfo 相同的订阅
     */
    SubscribeInfo queryRS(String userUniqId, String goodsUniqId);
    /**
     * 通过goos_uniq_id查询goodsinfo
     * @param goods_uniq_id gud
     * @return GoodsInfo
     * 一个参数需要加@param
     * */
    GoodsInfo queryGoodsInfo(@Param("goods_uniq_id") String goods_uniq_id);

    /**
     * 通过userUniqId与goodSUniqId改变订阅状态
     * @param userUniqId uud
     * @param goodsUniqId gud
     * @param subscribeState 订阅状态
     * @return int update成功的数量
     * */
    int updateSub(String userUniqId, String goodsUniqId,String subscribeState);
}

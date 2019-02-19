package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.GoodsInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GoodsDao {

    /**
     * 查询所有商品信息
     * @return List<GoodsInfo> 商品信息
     * */
    List<GoodsInfo> queryAll();
    /**
     * 插入商品信息
     * @param goodsInfo 商品信息
     * @return int 插入的条数
     * */
    @Insert("INSERT INTO goods_info")
    int insert(GoodsInfo goodsInfo);
}

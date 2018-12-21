package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.GoodsInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GoodsDao {

    @Select("SELECT * FROM goods_info")
    List<GoodsInfo> queryAll();
    //error
    @Insert("INSERT INTO goods_info")
    int insert(GoodsInfo goodsInfo);
}

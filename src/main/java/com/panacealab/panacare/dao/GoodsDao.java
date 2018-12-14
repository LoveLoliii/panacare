package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.GoodsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GoodsDao {
    List<GoodsInfo> queryAll();
}

package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface OrderDao {
    int insert(OrderInfo orderInfo);

    @Select("SELECT * FROM order_info WHERE order_number = #{order_number}")
    OrderInfo query(String order_number);

    @Select("SELECT * FROM goods_info WHERE goods_uniq_id = #{goods_uniq_id}")
    GoodsInfo queryGoods(String goods_uniq_id);

    int update(OrderInfo orderInfo);
}

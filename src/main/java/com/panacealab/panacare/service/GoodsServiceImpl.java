package com.panacealab.panacare.service;

import com.panacealab.panacare.dao.GoodsDao;
import com.panacealab.panacare.entity.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class GoodsServiceImpl implements GoodsService{
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public Map getAllGoodsInfo() {
        //
        Map rsMap = new HashMap();
        List<GoodsInfo> goodsInfoList = goodsDao.queryAll();
        rsMap.put("state",1);
        rsMap.put("goods_info",goodsInfoList);

        return rsMap;
    }
}

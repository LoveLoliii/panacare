package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.GoodsDao;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.service.GoodsService;
import com.panacealab.panacare.utils.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Loveloliii
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public Map getAllGoodsInfo() {
        //
        Map<String,Object> rsMap = new HashMap<>(2);
        List<GoodsInfo> goodsInfoList = goodsDao.queryAll();
        rsMap.put("state", StateCode.DATA_RETURN_SUCCESS);
        rsMap.put("data",goodsInfoList);
        return rsMap;
    }

    @Override
    public String addGoodsInfo(GoodsInfo goodsInfo) {

        int rs = goodsDao.insert(goodsInfo);
        return rs==0?"":"";
    }
}

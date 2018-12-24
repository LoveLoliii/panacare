package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.SubscribeDao;
import com.panacealab.panacare.entity.SubscribeInfo;
import com.panacealab.panacare.service.SubscribeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SubscribeServiceImpl implements SubscribeService {
    private Logger logger = LoggerFactory.getLogger(SubscribeServiceImpl.class);
    @Autowired
    private SubscribeDao subscribeDao;
    @Override
    public Map getSubscribeInfoByUid(String user_uniq_id) {
        Map map = new HashMap();
        List subList = new ArrayList();
        try{
            subList = subscribeDao.query(user_uniq_id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取单个订阅信息出错:{}",e.getMessage());
            map.put("state","671");
            //返回null好 还是""?
            map.put("data","");
            return map;
        }
            map.put("state","672");
            map.put("data",subList);
        return map;
    }

    @Override
    public Map getSubscribeInfoAll() {
        Map map = new HashMap();
        List subList;
        try{
            subList = subscribeDao.queryAll();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取所有订阅信息出错:{}",e.getMessage());
            map.put("state","671");
            //返回null好 还是""?
            map.put("data","");
            return map;
        }
        map.put("state","672");
        map.put("data",subList);
        return map;

    }

    @Override
    public String addSubscribeInfo(SubscribeInfo subscribeInfo) {

        int rs = subscribeDao.insert(subscribeInfo);

        return rs==0?"":"";
    }

    @Override
    public String modifySubscribeInfo(SubscribeInfo subscribeInfo) {
        //更新有值的属性

        int rs = subscribeDao.update(subscribeInfo);
        return rs==0?"":"";
    }
}

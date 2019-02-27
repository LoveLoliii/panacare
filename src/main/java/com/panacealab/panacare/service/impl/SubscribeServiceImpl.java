package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.SubscribeDao;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.SubscribeInfo;
import com.panacealab.panacare.service.SubscribeService;
import com.panacealab.panacare.utils.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Loveloliii
 */
@Service
public class SubscribeServiceImpl implements SubscribeService {
    private Logger logger = LoggerFactory.getLogger(SubscribeServiceImpl.class);
    @Autowired
    private SubscribeDao subscribeDao;
    @Override
    public Map getSubscribeInfoByUid(String user_uniq_id) {
        Map map = new HashMap(16);
        List subList = new ArrayList();
        try{
            subList = subscribeDao.query(user_uniq_id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取单个订阅信息出错:{}",e.getMessage());
            map.put("state",StateCode.DATA_QUERY_FAILED);
            map.put("data","");
            return map;
        }
            map.put("state",StateCode.DATA_RETURN_SUCCESS);
            map.put("data",subList);
        return map;
    }
    @Override
    public Map getSubscribeInfoForApp(String userUniqId) {
        Map map = new HashMap(16);
        List subList = new ArrayList();
        List fsList = new ArrayList();
        try{
            subList = subscribeDao.query(userUniqId);
            //对每一条订阅信息构造一个用于展示的map
            for (Object s:subList){
                Map newM = new HashMap(16);

                GoodsInfo g = subscribeDao.queryGoodsInfo(((SubscribeInfo)s).getGoods_uniq_id());
                newM.put("goods_palate",g.getGoods_palate());
                newM.put("goods_type",g.getGoods_type());
                newM.put("goods_onsale_buy_uprice",g.getGoods_onsale_buy_uprice());
                newM.put("goods_next_arrive_time","2019/05/02");
                newM.put("goods_uniq_id",((SubscribeInfo) s).getGoods_uniq_id());
                fsList.add(newM);

            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取单个订阅信息出错:{}",e.getMessage());
            map.put("state",StateCode.DATA_QUERY_FAILED);
            map.put("data","");
            return map;
        }
        map.put("state",StateCode.DATA_RETURN_SUCCESS);
        map.put("data",fsList);
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

        return rs==0? StateCode.DATABASE_INSERT_ERROR :StateCode.DATA_RETURN_SUCCESS;
    }

    @Override
    public String modifySubscribeInfo(SubscribeInfo subscribeInfo) {
        //更新有值的属性

        int rs = subscribeDao.update(subscribeInfo);
        return rs==0?"":"";
    }

    @Override
    public int checkRepeatSub(String userUniqId, String goodsUniqId) {
        int rs = subscribeDao.queryRS(userUniqId,goodsUniqId);
        return rs;
    }


}

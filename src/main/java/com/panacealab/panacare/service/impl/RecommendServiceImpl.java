package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.RecommendDao;
import com.panacealab.panacare.entity.RecommendInfo;
import com.panacealab.panacare.entity.RecommendReward;
import com.panacealab.panacare.entity.RecommendRewardRecord;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.RecommendService;
import com.panacealab.panacare.utils.StateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * @author loveloliii
 * @description
 * @date .
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendDao recommendDao;
    @Override
    public String addRecommendInfo(String user_uniq_id, String user_referee) {
        //通过特征码查询到推荐人的user_uniq_id
        String recommend_uniq_id = recommendDao.queryUniqId(user_referee);
        if(null==recommend_uniq_id || "".equals(recommend_uniq_id)){
            return StateCode.REFEREE_INVALID;
        }
        RecommendInfo recommendInfo = new RecommendInfo();
        recommendInfo.setRecommend_user_uniq_id(recommend_uniq_id);
        recommendInfo.setUser_uniq_id(user_uniq_id);
        int rs = recommendDao.insert(recommendInfo);
        //添加记录
        if(rs != 0){
            //获取人数
            int count  = recommendDao.queryRecommendRewardCount(user_uniq_id);
            //直接123会影响后续修改
            List<RecommendReward> countList = recommendDao.queryRecommendReward();
            //未测试
            for(RecommendReward r:countList){
                    int c = r.getRecommend_reward_count();
                if(count>= c){
                    int recommend_reward_id = recommendDao.queryRecommendRewardIdByCount(c);
                    RecommendRewardRecord recommendRewardRecord = new RecommendRewardRecord();
                    recommendRewardRecord.setUser_uniq_id(user_uniq_id);
                    recommendRewardRecord.setRecommend_reward_id(recommend_reward_id);
                    if(recommendDao.queryRecommendRewardRecordByRI(user_uniq_id,recommend_reward_id) ==0 ){
                        recommendDao.insertRecommendRewardRecord(recommendRewardRecord);
                    }
                }
            }
        }
        return rs==0?"669":"670";
    }

    @Override
    public Map getRecommendRewardInfo() {
        Map<String, Object> map = new HashMap<>(16);
        List<RecommendReward> rewardList = recommendDao.queryRecommendReward();
        map.put("data",rewardList);
        map.put("state",StateCode.DATA_RETURN_SUCCESS);
        return map;
    }

    @Override
    public Map getRecommendRewardRecord(String user_uniq_id) {
        Map<String, Object> map = new HashMap<>(2);
        List<RecommendRewardRecord> recordList = recommendDao.queryRecommendRewardRecord(user_uniq_id);
        map.put("data",recordList);
        map.put("state",StateCode.DATA_RETURN_SUCCESS);
        return map;
    }

    @Override
    public String addRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord) {

        //将领取状态 默认为1 user_uniq_id 前端token取出写入 ;)
        recommendRewardRecord.setRecommend_reward_record_getstate(1);
        int rs = recommendDao.insertRecommendRewardRecord(recommendRewardRecord);
        return rs==0?"669":"670";
    }

    @Override

    public String updateRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord) {
        // int rs = recommendDao.updateRecommendRewardRecord(recommendRewardRecord);rs==0?"":""
        return  null;
    }

    @Override
    public Map getUserReferee(String user_uniq_id) {
        Map<String, Object> map = new HashMap<>(16);
        UserInfo userInfo = recommendDao.queryByUserReferee(user_uniq_id);

        if(null==userInfo){
            map.put("data","");
            map.put("state", StateCode.USER_NOT_EXIST);
            return map;
        }
        //
        String user_referee = userInfo.getUser_referee();
        if(null==user_referee||"".equals(user_referee)){

            //生成新的特征码 来一种生成方式
           /*Calendar calendar =  Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
            String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH));*/
            //Long timestamp = System.currentTimeMillis()/1000;
            String str = "test";
            map.put("data",str);
        }else{
            map.put("data",user_referee);
        }
        map.put("state","1303");
        return map;
    }

    @Override
    public Map getRecommendRewardCount(String user_uniq_id) {
        Map<String, Object> map = new HashMap<>(16);
        int count = recommendDao.queryRecommendRewardCount(user_uniq_id);
        map.put("data",count);
        map.put("state","1303");
        return map;
    }

    @Override
    public int applyReward(String userUniqId, String recommendRewardRecordId) {
        int rs = recommendDao.updateState(userUniqId,recommendRewardRecordId,0);
        return rs;
    }


}

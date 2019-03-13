package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.AppUpdateDao;
import com.panacealab.panacare.entity.VersionInfo;
import com.panacealab.panacare.service.AppUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author loveloliii
 * @description AppUpdateService的实现类
 * @date 2019/3/11.
 */
@Service
public class AppUpdateServiceImpl implements AppUpdateService {
    @Autowired
    private AppUpdateDao appUpdateDao;

    @Override
    public String[] getAppVersion(String product, int platform,String channel) {
        VersionInfo  version= appUpdateDao.queryVersion(product,platform,channel);
        if (null == version ){
            return null;
        }
        // fixme 假设version.getVersioncode 或者 name为null 应该如何处理
        return new String[]{version.getVersioncode(),version.getVersionname(),version.getUpdateLog()};
    }

    @Override
    public int updateVersionInfo(VersionInfo versionInfo) {
        //首先判定表中是否存在该应用版本
        VersionInfo v = appUpdateDao.queryVersion(versionInfo.getProduct(),versionInfo.getPlatform(),versionInfo.getChannel());
        int rs = 0 ;
        if(null ==v){
            // do insert operation
            rs = appUpdateDao.insertVersion(versionInfo);
        }else{
            // do update operation
            rs = appUpdateDao.updateVersion(versionInfo);
        }
        return rs;
    }
}

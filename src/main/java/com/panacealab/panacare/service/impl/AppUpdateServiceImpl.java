package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.AppUpdateDao;
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
    public String[] getAppVersion(String product, int platform) {
        appUpdateDao.queryVersion(product,platform);

        return new String[0];
    }
}

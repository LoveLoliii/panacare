package com.panacealab.panacare.service;

/**
 * @author loveloliii
 * @date 2019/3/11.
 */
public interface AppUpdateService {

    /**
     * 查询某app在某个平台上的versionCode与versionName
     * @param product 产品 panacare
     * @param platform 1 android 2 ios 详见PAPI文档
     * @return String[] 返回versionCode与versionName
     */
    String[] getAppVersion(String product, int platform);
}

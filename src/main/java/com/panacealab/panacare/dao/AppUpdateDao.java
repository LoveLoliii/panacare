package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.VersionInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author loveloliii
 * @date 2019/3/11.
 */
@Mapper
public interface AppUpdateDao {

    /**
     * 查询最新版本
     * @param product panacare
     * @param platform 平台
     * @param channel 渠道
     * @return VersionInfo
     */
    VersionInfo queryVersion(String product, int platform,String channel);

    /**
     * 插入版本信息
     * @param versionInfo 版本信息
     * @return int
     */
    int insertVersion(VersionInfo versionInfo);

    /**
     * 更新版本信息
     * @param versionInfo 版本信息
     * @return int
     */
    int updateVersion(VersionInfo versionInfo);
}

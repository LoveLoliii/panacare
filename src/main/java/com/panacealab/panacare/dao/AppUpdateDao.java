package com.panacealab.panacare.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author loveloliii
 * @date 2019/3/11.
 */
@Mapper
public interface AppUpdateDao {

    /**
     * 
     * @param product
     * @param platform
     */
    void queryVersion(String product, int platform);
}

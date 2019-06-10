package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.DeviceData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author loveloliii
 * @date 2019/6/10.
 */
@Mapper
public interface DeviceDataDao {
    /**
     * 保存device data
     * @param data device data
     * @return int insert result
     * */
    int save(DeviceData data);
}

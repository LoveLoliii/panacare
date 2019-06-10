package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.DeviceData;

/**
 * @author loveloliii
 * @date 2019/6/10.
 */
public interface DeviceService {
    /**
     *  保存设备数据
     * @param data device data
     * @return int  insert result
     * */
    int save(DeviceData data);
}

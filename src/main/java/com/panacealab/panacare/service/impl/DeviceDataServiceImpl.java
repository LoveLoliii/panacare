package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.DeviceDataDao;
import com.panacealab.panacare.entity.DeviceData;
import com.panacealab.panacare.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author loveloliii
 * @description
 * @date 2019/6/10.
 */
@Service
public class DeviceDataServiceImpl implements DeviceService {
    @Autowired
    private DeviceDataDao deviceDataDao;
    @Override
    public int save(DeviceData data) {

        return deviceDataDao.save(data);
    }
}

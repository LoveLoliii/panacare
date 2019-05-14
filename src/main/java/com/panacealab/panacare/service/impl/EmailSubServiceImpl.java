package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.EmailSubDao;
import com.panacealab.panacare.service.EmailSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author loveloliii
 * @date 2019/5/14.
 */
@Service
public class EmailSubServiceImpl implements EmailSubService {
@Autowired
private EmailSubDao emailSubDao;
    @Override
    public void saveSubscribeEmail(String email) {
        emailSubDao.insert(email);
    }
}

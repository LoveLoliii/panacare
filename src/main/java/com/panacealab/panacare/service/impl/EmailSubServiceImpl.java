package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.EmailSubDao;
import com.panacealab.panacare.entity.EmailSubscribe;
import com.panacealab.panacare.service.EmailSubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author loveloliii
 * @date 2019/5/14.
 */
@Service
public class EmailSubServiceImpl implements EmailSubService {
@Autowired
private EmailSubDao emailSubDao;
private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Override
    public void saveSubscribeEmail(String email) {
        List<EmailSubscribe> emailSubscribeList = emailSubDao.query(email);
        if(emailSubscribeList.size()!=0 && emailSubscribeList.get(0).getEmail().toLowerCase().equals(email.toLowerCase())){
           logger.error("sub email is exist.");
        }else {
            emailSubDao.insert(email);
        }

    }
}

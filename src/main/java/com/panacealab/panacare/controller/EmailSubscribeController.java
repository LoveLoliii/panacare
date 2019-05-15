package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.EmailSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * email related
 *
 * @author loveloliii
 * @date 2019/5/13.
 */
@RestController
public class EmailSubscribeController {
    @Autowired
    private EmailSubService emailSubService;
    @RequestMapping("/email/subscribe/{email}")
    private String subscribeByEmail(@PathVariable(value = "email") String email) {
        // check repeat
        emailSubService.saveSubscribeEmail(email);
        return "success";
    }

}

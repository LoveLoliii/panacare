package com.panacealab.panacare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author loveloliii
 * eureka reference
 * @date 2019/5/7.
 */
@RestController
public class EurekaReferenceController {
@RequestMapping("actuator/info")
    private String  getActuatorInfo(){
    return "use for panacare service \r\n" +
            "By Loveloliii @Panacea";
}
}

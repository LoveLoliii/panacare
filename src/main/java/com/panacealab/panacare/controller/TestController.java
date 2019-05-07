package com.panacealab.panacare.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Loveloliii
 */
@RestController
public class TestController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/hello/{words}")
    private String sayHi(@PathVariable("words") String words){


    return words;
}

}

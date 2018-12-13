package com.panacealab.panacare.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

@RequestMapping("/hello/{words}")
    private String sayHi(@PathVariable("words") String words){


    return words;
}

}

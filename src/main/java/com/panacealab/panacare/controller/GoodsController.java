package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.service.GoodsService;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /***
     *
     * @describe 取得所有商品信息
     * @return 处理结果状态与商品信息
     * @autho loveloliii
     * */
    @RequestMapping(path = "getAllGoodsInfo",method = RequestMethod.POST)
    private Map getAllGoodsInfo(){
        Map rs;
        rs = goodsService.getAllGoodsInfo();
        return rs;


    }

    /****
     * @describe 添加商品信息
     *商品信息并非用户进行操作 web端后台管理
     *
     * */
    @RequestMapping(path = "addGoodsInfo",method = RequestMethod.POST)
    private String addGoodsInfo(@RequestParam GoodsInfo goodsInfo){
        //TODO 进行验证用户




        return goodsService.addGoodsInfo(goodsInfo);

    }




}

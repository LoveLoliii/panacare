package com.panacealab.panacare.controller;


import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @CrossOrigin
    @RequestMapping(path = "addGoodsInfo",method = RequestMethod.POST)
    private String addGoodsInfo(@RequestBody Map map){


        String token = (String) map.get("token");


        GoodsInfo goodsInfo = (GoodsInfo) map.get("data");




        return goodsService.addGoodsInfo(goodsInfo);

    }




}

package com.panacealab.panacare.controller;


import com.google.gson.Gson;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.service.GoodsService;
import com.panacealab.panacare.utils.PUtils;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Loveloliii
 */
@RestController
@Slf4j
public class GoodsController {
    public static final String UPDATE_GOODS_INFO = "updateGoodsInfo";
    @Autowired
    private GoodsService goodsService;
    /***
     * 是否需要进行权限认证 防止爬虫？
     * @describe 取得所有商品信息
     * @return 处理结果状态与商品信息
     * @autho loveloliii
     * */
    @CrossOrigin
    //@RequestMapping(path = "getAllGoodsInfo",method = RequestMethod.POST)
    @PostMapping("getAllGoodsInfo")
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
    //@RequestMapping(path = "addGoodsInfo",method = RequestMethod.POST)
    @PostMapping("addGoodsInfo")
    private String addGoodsInfo(@RequestBody GoodsInfo goodsInfo){
      //  String token = (String) map.get("token");
     /*   String x = (String) map.get("data");
        Gson g = PUtils.getGsonInstance();

        GoodsInfo goodsInfo = g.fromJson(x,GoodsInfo.class);*/
     // 生成一个uuid
        goodsInfo.setGoods_uniq_id(StringUtil.getUUID());
        return goodsService.addGoodsInfo(goodsInfo);
    }

    /**
     * 更新商品信息
     * */
    @CrossOrigin
    @PostMapping(UPDATE_GOODS_INFO)
    private String updateGoodsInfo(@RequestBody GoodsInfo goodsInfo){
        log.info(goodsInfo.toString());
        int rs;
        rs = goodsService.updateGoodsInfo(goodsInfo);

        return rs==1? StateCode.DATA_RETURN_SUCCESS:StateCode.DATA_NOT_CHANGE;

    }
}

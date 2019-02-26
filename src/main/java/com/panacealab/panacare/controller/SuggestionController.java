package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.Suggestion;
import com.panacealab.panacare.service.SuggestionService;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author loveloliii
 * @description 意见与建议
 * @date 2019/2/21.
 */
@RestController
public class SuggestionController {
    private Logger logger = LoggerFactory.getLogger(SuggestionController.class);

    @Autowired
    SuggestionService suggestionService;

    @RequestMapping(path = "saveSuggestInfo",method = RequestMethod.POST)
    private Map saveSuggestInfo(@RequestBody Map map){
        Map<String,Object> resultMap = new HashMap<>(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        String user_uniq_id = TokenUtil.getTokenValues(token);
        String suggestion_d = (String) map.get("suggest");
        logger.debug("获得意见或建议：?",suggestion_d);
        Suggestion suggestion = new Suggestion();
        suggestion.setSuggestion_description(suggestion_d);
        suggestion.setSuggestion_state(0);
        suggestion.setUser_uniq_id(user_uniq_id);
        suggestion.setSuggestion_time(String.valueOf(System.currentTimeMillis()));
        int insertRs = suggestionService.saveSuggestion(suggestion);
        if(insertRs>0){
            resultMap.put("state",StateCode.DATA_RETURN_SUCCESS);
        }else {
            resultMap.put("state",StateCode.DATABASE_INSERT_ERROR);
        }
        return resultMap;
    }

}

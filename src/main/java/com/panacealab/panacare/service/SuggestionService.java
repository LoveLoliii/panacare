package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.Suggestion;

/**
 * @author loveloliii
 * @date 2019/02/21
 * */
public interface SuggestionService {
    /**
     * 保存建议新消息并返回成功插入的条数
     * @param suggestion 意见或建议
     * @return int insert成功的次数
     */
    int saveSuggestion(Suggestion suggestion);
}

package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.SuggestionDao;
import com.panacealab.panacare.entity.Suggestion;
import com.panacealab.panacare.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author loveloliii
 * @description 意见与建议service
 * @date 2019/2/21.
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    SuggestionDao suggestionDao;

    @Override
    public int saveSuggestion(Suggestion suggestion) {

        return suggestionDao.insertSuggestion(suggestion);
    }
}

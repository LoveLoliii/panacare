package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.Suggestion;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author loveloliii
 * @date 2019/2/21.
 */
@Mapper
public interface SuggestionDao {
    /**
     * 向数据库插入意见或建议信息并返回成功插入条数
     * @param suggestion 意见
     * @return int
     */
    int insertSuggestion(Suggestion suggestion);
}

package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.TokenRedis;
import org.springframework.stereotype.Service;

@Service
public class IRedisServiceImpl extends IRedisService<TokenRedis>{
    private static final String REDIS_KEY = "TEST_REDIS_KEY";

    @Override
    protected String getRedisKey() {
        return this.REDIS_KEY;
    }
}

package com.ling.other.modules.lov.adapter.impl;

import com.ling.other.common.utils.JsonUtils;
import com.ling.other.common.utils.RedisUtils;
import com.ling.other.mapper.LovMapper;
import com.ling.other.modules.lov.adapter.LovAdapter;
import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling
 * @since 2020/7/27 10:27
 */
@Service
public class DefaultLovAdapter implements LovAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLovAdapter.class);

    @Autowired
    private LovMapper lovMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public LovDTO queryLovInfo(String lovCode) {
        Assert.notNull(lovCode, "参数不能为空");
        logger.debug("查询redis db [{}]",1);

        String key = "lov:lov:" +lovCode;
        LovDTO lovDTO = new LovDTO();
        String lovJson = redisUtils.get(key);
        if(StringUtils.isEmpty(lovJson)){
            logger.debug("查询数据库lov：{}",lovCode);
            lovDTO = lovMapper.queryLovInfo(lovCode);
            redisUtils.set(key, JsonUtils.toJson(lovDTO),604800);
        }else{
            lovDTO = JsonUtils.fromJson(lovJson,LovDTO.class);
        }
        return lovDTO;
    }

    @Override
    public List<LovValueDTO> queryLovValue(String lovCode) {
        List<LovValueDTO> result = new ArrayList<>();
        // data valid
        Assert.notNull(lovCode, "error.data_invalid");

        String valueKey = "lov:value:"+lovCode;
        // todoa : 使用Set存储不重复集合
        List<String> valueJsons = redisUtils.listGet(valueKey,0,-1);
        if(CollectionUtils.isEmpty(valueJsons)){
            result = lovMapper.queryLovValue(lovCode);
            logger.debug("将值集值缓存redis:{}",result);
            for (LovValueDTO lovValueDTO : result) {
                valueJsons.add(JsonUtils.toJson(lovValueDTO));
            }
            redisUtils.listSetArrayList(valueKey, (ArrayList<?>) valueJsons,604800);
        }else{
            for (String valueJson : valueJsons) {
                result.add(JsonUtils.fromJson(valueJson, LovValueDTO.class));
            }
        }
        return result;
    }

    @Override
    public LovValueDTO queryLovValueDTO(String lovType, String value) {
        LovValueDTO result = null;

        // data valid
        Assert.notNull(lovType, "error.data_invalid");
        Assert.notNull(value, "error.data_invalid");
        result = lovMapper.queryLovValueDTO(lovType,value);
        return result;
    }


}

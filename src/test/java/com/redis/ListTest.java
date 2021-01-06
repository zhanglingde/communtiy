package com.redis;

import com.ling.other.CommunityApplication;
import com.ling.other.common.utils.JsonUtils;
import com.ling.other.common.utils.RedisUtils;
import com.ling.other.modules.lov.dto.LovValueDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis测试
 *
 * @author zhangling 2021-01-06 11:51
 *
 */
@SpringBootTest(classes = CommunityApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ListTest {

    @Autowired
    RedisUtils redisUtils;


    /**
     * List 添加集合
     */
    @Test
    public void listAdd(){
        String key = "lov:value:status";
        List<String> valueJsons = new ArrayList<>();
        List<LovValueDTO> list = new ArrayList<>();
        list.add(LovValueDTO.builder().lovValueId(1).value("EXPIRED").meaning("过期").build());
        list.add(LovValueDTO.builder().lovValueId(2).value("DEFAULT").meaning("缺失").build());
        list.add(LovValueDTO.builder().lovValueId(3).value("EFFECTIVE").meaning("有效").build());
        for (LovValueDTO lovValueDTO : list) {
            valueJsons.add(JsonUtils.toJson(lovValueDTO));
        }
        redisUtils.listSetArrayList(key, (ArrayList<?>) valueJsons,604800);
    }


}

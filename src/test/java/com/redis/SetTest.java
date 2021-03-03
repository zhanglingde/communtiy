package com.redis;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.easyExcel.write.Department;
import com.easyExcel.write.Employee;
import com.easyExcel.write.ExcelMergeStrategy;
import com.ling.other.CommunityApplication;
import com.ling.other.common.utils.JsonUtils;
import com.ling.other.common.utils.RedisUtils;
import com.ling.other.modules.lov.dto.LovValueDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Set
 *
 * Set集合 sadd添加进Redis是无序的，和添加的顺序无关，
 *
 *
 * @author zhangling 2021-01-06 11:51
 *
 */
@SpringBootTest(classes = CommunityApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SetTest {

    @Autowired
    RedisUtils redisUtils;

    /**
     * Set 添加集合
     */
    @Test
    public void setadd(){
        String key = "lov:value:setstatus6";
        List<LovValueDTO> list = new ArrayList<>();
        list.add(LovValueDTO.builder().lovValueId(1).value("EXPIRED").meaning("过期").build());
        list.add(LovValueDTO.builder().lovValueId(2).value("DEFAULT").meaning("缺失").build());
        list.add(LovValueDTO.builder().lovValueId(3).value("EFFECTIVE").meaning("有效").build());

        list = list.stream().filter(Objects::nonNull).map((item)->{
            redisUtils.setAdd(key,new String[]{JsonUtils.toJson(item)});
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 添加一个元素
     */
    @Test
    public void setAddOne(){
        String key = "lov:value:setstatus3";
        LovValueDTO build = LovValueDTO.builder().lovValueId(2).value("EXPIRED").meaning("过期").build();
        //LovValueDTO build = LovValueDTO.builder().lovValueId(2).value("DEFAULT").meaning("缺失").build();
        //LovValueDTO build = LovValueDTO.builder().lovValueId(3).value("EFFECTIVE").meaning("有效").build();
        redisUtils.setAdd(key,JsonUtils.toJson(build));
    }

    /**
     * Set 查询Redis
     * 存进去和取出来的顺序不一致，
     * 但是同一个key每次取出来的顺序是相同的
     */
    @Test
    public void setMember(){
        //String key = "lov:value:setstatus2";
        String key = "k2";
        Set<Object> objects = redisUtils.setMembers(key);
        List<LovValueDTO> list = new ArrayList<>();
        //for (Object object : objects) {
        //    list.add(JsonUtils.fromJson((String) object,LovValueDTO.class));
        //}
        //list.forEach(System.out::println);
    }




}

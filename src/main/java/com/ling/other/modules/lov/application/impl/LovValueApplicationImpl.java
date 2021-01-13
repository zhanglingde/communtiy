package com.ling.other.modules.lov.application.impl;


import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.RedisUtils;
import com.ling.other.mapper.LovMapper;
import com.ling.other.mapper.LovValueMapper;
import com.ling.other.modules.lov.adapter.LovAdapter;
import com.ling.other.modules.lov.application.LovValueApplication;
import com.ling.other.modules.lov.convert.LovConvert;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/11/18 9:55
 */
@Service
public class LovValueApplicationImpl implements LovValueApplication {

    @Autowired
    private LovMapper lovMapper;

    @Autowired
    private LovValueMapper lovValueMapper;

    @Autowired
    private RedisUtils redisUtils;


    @Autowired
    private LovAdapter lovAdapter;



    @Override
    public List<LovValueVO> queryLovValue(String lovCode) {

        //List<LovValueDTO> lovValueDTOList = lovMapper.queryLovValue(lovCode);
        List<LovValueDTO> lovValueDTOList = lovAdapter.queryLovValue(lovCode);

        List<LovValueVO> list = LovConvert.INSTANCE.convertLovValueVO(lovValueDTOList);
        return list;
    }

    @Override
    public void createLovValue(LovValueDTO lovValueDTO) {
        this.checkLovValue(lovValueDTO);

        String valueKey = "lov:value:" + lovValueDTO.getLovCode();
        redisUtils.delete(valueKey);


        lovValueMapper.insert(lovValueDTO);
    }


    private void checkLovValue(LovValueDTO lovValueDTO) {
        LovValueDTO temp = new LovValueDTO();
        temp.setValue(lovValueDTO.getValue());
        temp.setLovId(lovValueDTO.getLovId());

        List<LovValueVO> list = lovValueMapper.selectByCondition(temp);
        if(!CollectionUtils.isEmpty(list)){
            throw new RrException("该value已存在");
        }
    }


    @Override
    public void deleteValue(Integer[] lovValueIds) {
        for (Integer lovValueId : lovValueIds) {
            LovValueDTO lovValueDTO = lovValueMapper.selectByPrimary(lovValueId);
            if (lovValueDTO == null) {
                return;
            }

            String valueKey = "lov:value:" + lovValueDTO.getLovCode();
            redisUtils.delete(valueKey);
            lovMapper.deleteById(lovValueId);
        }
    }

    @Override
    public LovValueDTO updateLovValue(LovValueDTO lovValueDTO) {
        LovValueDTO temp = lovValueMapper.selectByPrimary(lovValueDTO.getLovValueId());
        if (temp == null) {
            throw new RrException("值集不存在");
        }
        String valueKey = "lov:value:" + temp.getLovCode();
        redisUtils.delete(valueKey);
        lovValueMapper.updateByPrimaryKey(lovValueDTO);
        return lovValueDTO;
    }
}

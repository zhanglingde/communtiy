package com.ling.other.modules.lov.application.impl;


import com.ling.other.common.exception.RrException;
import com.ling.other.common.utils.RedisUtils;
import com.ling.other.mapper.LovMapper;
import com.ling.other.mapper.LovValueMapper;
import com.ling.other.modules.lov.adapter.LovAdapter;
import com.ling.other.modules.lov.application.LovApplication;
import com.ling.other.modules.lov.convert.LovConvert;
import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovSearchDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovVO;
import com.ling.other.modules.lov.vo.LovValueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * lov表(Lov)表服务实现类
 *
 * @author zhangling
 * @since 2020-07-27 15:51:50
 */
@Service("lovService")
public class LovApplicationImpl implements LovApplication {

    @Autowired
    private LovMapper lovMapper;

    @Autowired
    private LovValueMapper lovValueMapper;

    @Autowired
    private RedisUtils redisUtils;



    @Autowired
    private LovAdapter lovAdapter;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createLov(LovDTO lovDTO) {

        this.checkLovCode(lovDTO.getLovCode());
        // 创建值集头
        lovMapper.createLov(lovDTO);

        if (!CollectionUtils.isEmpty(lovDTO.getLovValueDTOList())) {
            lovDTO.getLovValueDTOList().forEach(lovValue -> {
                lovValue.setLovId(lovDTO.getLovId());
                lovValue.setLovCode(lovDTO.getLovCode());
            });
            lovMapper.batchInsertLovValue(lovDTO.getLovValueDTOList());
        }

    }

    /**
     * 校验lovCode是否存在
     * @param lovCode
     */
    private void checkLovCode(String lovCode) {
        LovDTO lovDTO = lovMapper.queryLovInfo(lovCode);
        if (lovDTO != null) {
            throw new RrException("该lovCode已存在！");
        }
    }

    @Override
    public List<LovValueVO> queryLovValue(String lovCode) {

        //List<LovValueDTO> lovValueDTOList = lovMapper.queryLovValue(lovCode);
        List<LovValueDTO> lovValueDTOList = lovAdapter.queryLovValue(lovCode);

        List<LovValueVO> list = LovConvert.INSTANCE.convertLovValueVO(lovValueDTOList);
        return list;
    }

    @Override
    public void createValue(List<LovValueDTO> lovValueDTOList) {
        lovMapper.batchInsertLovValue(lovValueDTOList);
    }

    @Override
    public void updateValue(LovValueDTO lovValueDTO) {
        LovValueDTO dbValue = lovMapper.queryLovValueById(lovValueDTO.getLovValueId());
        if (lovValueDTO == null) {
            return;
        }

        String valueKey = "lov:value:" + dbValue.getLovCode();
        redisUtils.delete(valueKey);


        lovMapper.updateSelective(lovValueDTO);
    }

    @Override
    public void deleteValue(Integer lovValueId) {
        LovValueDTO lovValueDTO = lovMapper.queryLovValueById(lovValueId);
        if (lovValueDTO == null) {
            return;
        }

        String valueKey = "lov:value:" + lovValueDTO.getLovCode();
        redisUtils.delete(valueKey);


        lovMapper.deleteById(lovValueId);
    }

    @Override
    public List<LovVO> list(LovSearchDTO lovSearchDTO) {
        return lovMapper.select(lovSearchDTO);
    }

    @Override
    public LovVO details(Integer lovId) {
        LovVO lovVO = lovMapper.selectByPrimaryKey(lovId);
        List<LovValueVO> list = lovValueMapper.selectByLovId(lovId);
        lovVO.setLovValueList(list);
        return lovVO;
    }

    @Override
    public void updateLov(LovVO lovVO) {

    }
}
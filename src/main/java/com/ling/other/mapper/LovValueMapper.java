package com.ling.other.mapper;

import com.ling.other.modules.lov.vo.LovValueVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (LovValue)表数据库访问层
 *
 * @author zhangling
 * @since 2020-09-14 13:41:50
 */
@Repository
public interface LovValueMapper {

    /**
     * 根据lovId查询
     * @param lovId
     * @return
     */
    List<LovValueVO> selectByLovId(Integer lovId);
}
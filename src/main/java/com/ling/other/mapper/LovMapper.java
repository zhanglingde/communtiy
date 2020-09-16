package com.ling.other.mapper;


import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovSearchDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * lov表(Lov)表数据库访问层
 *
 * @author zhangling
 * @since 2020-07-27 10:24:28
 */
@Repository
public interface LovMapper {

    /**
     * 根据值集代码查询值集头信息
     * @param lovCode
     * @return
     */
    LovDTO queryLovInfo(String lovCode);


    /**
     * 根据值集代码查询值集值
     * @param lovCode
     * @return
     */
    List<LovValueDTO> queryLovValue(String lovCode);

    /**
     * 创建值集
     * @param lovDTO
     * @return
     */
    int createLov(LovDTO lovDTO);

    /**
     * 批量插入值集值信息
     * @param lovValueDTOList
     * @return
     */
    int batchInsertLovValue(@Param("list") List<LovValueDTO> lovValueDTOList);

    LovValueDTO queryLovValueDTO(@Param("lovCode") String lovCode, @Param("value") String value);

    int updateSelective(@Param("lovValueDTO") LovValueDTO lovValueDTO);

    LovValueDTO queryLovValueById(@Param("lovValueId") Integer lovValueId);

    int deleteById(@Param("lovValueId") Integer lovValueId);

    /**
     * 查询值集列表
     * @return
     */
    List<LovVO> selectAll();

    /**
     * 根据主键查询详情
     * @param lovId
     * @return
     */
    LovVO selectByPrimaryKey(Integer lovId);

    /**
     * 按添加搜索
     * @param lovSearchDTO
     * @return
     */
    List<LovVO> select(LovSearchDTO lovSearchDTO);
}
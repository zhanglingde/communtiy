package com.ling.other.modules.lov.application;




import com.ling.other.common.constants.CommonParams;
import com.ling.other.common.utils.PageUtils;
import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovSearchDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovVO;
import com.ling.other.modules.lov.vo.LovValueVO;

import java.util.List;

/**
 * lov表(Lov)表服务接口
 *
 * @author zhangling
 * @since 2020-07-27 15:51:49
 */
public interface LovApplication {


    /**
     * 创建值集
     * @param lovDTO
     * @return
     */
    void createLov(LovDTO lovDTO);

    /**
     * 值集列表
     * @return
     */
    List<LovVO> list(LovSearchDTO lovSearchDTO);

    /**
     * 值集详情
     * @param lovId
     * @return
     */
    LovVO details(Integer lovId);

    /**
     * 编辑值集header
     * @param lovDTO
     */
    void updateLov(LovDTO lovDTO);
}
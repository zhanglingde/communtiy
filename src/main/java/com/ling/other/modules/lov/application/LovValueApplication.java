package com.ling.other.modules.lov.application;



import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.vo.LovValueVO;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/11/18 9:55
 */
public interface LovValueApplication {

    /**
     * 查询lov value
     * @param lovCode 值集代码
     * @return
     */
    List<LovValueVO> queryLovValue(String lovCode);

    /**
     * 创建value
     * @param lovValueDTO
     */
    void createLovValue(LovValueDTO lovValueDTO);

    /**
     * 编辑值集
     * @param lovValueDTO
     */
    void updateLovValue(LovValueDTO lovValueDTO);

    /**
     * 删除值集value
     * @param lovValueIds
     */
    void deleteValue(Integer[] lovValueIds);

}

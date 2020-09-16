package com.ling.other.modules.lov.adapter;


import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;

import java.util.List;


/**
 * 值集值接口适配器,用以支持值集值查询
 *
 * @author zhangling
 */
public interface LovAdapter {
    
    /**
     * <p>根据值集代码查询值集头信息</p>
    
     * @param lovCode 值集代码
     * @return
     */
    LovDTO queryLovInfo(String lovCode);
    
    /**
     * <p>根据值集代码查询值集值</p>
     * 
     * @param lovCode 值集代码
     * @return
     */
    List<LovValueDTO> queryLovValue(String lovCode);

    /**
     * 查询LOV值
     */
    LovValueDTO queryLovValueDTO(String lovType,String value);
}

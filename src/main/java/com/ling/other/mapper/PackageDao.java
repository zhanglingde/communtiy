package com.ling.other.mapper;

import com.ling.other.modules.excel.hutool.dto.PackageDTO;
import com.ling.other.modules.excel.hutool.vo.HuToolExportPackageVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单包裹
 */
@Repository
public interface PackageDao {

    /**
     * 查询导出Excel
     * @param packageDTO
     * @return
     */
    List<HuToolExportPackageVO> selectExportVO(PackageDTO packageDTO);
}

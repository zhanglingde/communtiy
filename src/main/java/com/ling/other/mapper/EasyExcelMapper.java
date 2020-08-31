package com.ling.other.mapper;

import com.ling.other.dto.ExcelExportLineDTO;
import com.ling.other.dto.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangling
 * @since 2020/8/31 13:12
 */
@Repository
public interface EasyExcelMapper {

    /**
     * 批量插入
     * @param userList
     * @return
     */
    int batchInsert(@Param("list") List<User> userList);

    List<User> selectAll();
}

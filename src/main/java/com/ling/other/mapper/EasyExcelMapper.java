package com.ling.other.mapper;

import com.ling.other.modules.excel.easyexcel.vo.UserVO;
import com.ling.other.modules.user.dto.User;
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

    /**
     * 查询用户User列表
     * @return
     */
    List<UserVO> list();
}

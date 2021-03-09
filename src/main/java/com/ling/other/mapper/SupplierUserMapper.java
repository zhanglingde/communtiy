package com.ling.other.mapper;

import com.ling.other.modules.user.dto.User;
import com.ling.other.modules.user.entity.SupplierUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 供应商用户表(SupplierUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-10 14:07:33
 */
@Repository
public interface SupplierUserMapper extends Mapper<SupplierUser> {

    int insertUser(User user);

    /**
     * 查询user
     * @param id
     * @return
     */
    User selectUser(int id);


    int updateById(Integer id);
}
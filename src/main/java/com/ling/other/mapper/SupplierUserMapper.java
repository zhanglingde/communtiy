package com.ling.other.mapper;

import com.ling.other.entity.SupplierUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 供应商用户表(SupplierUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-10 14:07:33
 */
@Repository
public interface SupplierUserMapper extends Mapper<SupplierUser> {



}
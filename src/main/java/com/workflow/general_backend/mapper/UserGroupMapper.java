package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Product;
import com.workflow.general_backend.entity.UserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface UserGroupMapper {
    List<UserGroup> findAll();

    List<UserGroup> findById(String gid);

    int insert(UserGroup userGroup) throws DataAccessException;

    int deleteById(String gid);

    int update(UserGroup userGroup) throws DataAccessException;
}

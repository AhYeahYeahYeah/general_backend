package com.workflow.general_backend.mapper;

import com.workflow.general_backend.dto.OrdersVo;
import com.workflow.general_backend.entity.Orders;
import com.workflow.general_backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface OrdersMapper {
    List<OrdersVo> findAll();

    List<Orders> findRecent();

    List<Orders> findById(String oid);

    List<Orders> findByCid(String cid);

    int insert(Orders orders) throws DataAccessException;

    int update(Orders orders) throws DataAccessException;

    int deleteById(String oid);
}

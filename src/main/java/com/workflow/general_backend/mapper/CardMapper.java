package com.workflow.general_backend.mapper;

import com.workflow.general_backend.entity.Card;
import com.workflow.general_backend.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CardMapper {
    Card findById(String cardNum);
    int insert(Card card);
    int update(Card card);
}

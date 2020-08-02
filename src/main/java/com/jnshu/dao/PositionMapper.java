package com.jnshu.dao;

import com.jnshu.pojo.Position;

import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKey(Position record);

    List<Position> selectPosition();
}
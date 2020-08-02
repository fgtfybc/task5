package com.jnshu.dao;

import com.jnshu.pojo.Student;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    List<Student> selectStudent();//查询优秀学员

    int selectByLearning();//查询累计在线学习人数

    int selectByWorking();//查询已经找到工作人数

}
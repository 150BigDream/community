package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select * from question limit #{offset},#{size}")
    List<Question> selectList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> selectListByUserId(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("size") Integer size);
}

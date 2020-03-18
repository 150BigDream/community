package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    //TODO 这里优化为根据时间创建时间倒序排列  ORDER BY gmt_create DESC
    @Select("select * from question limit #{offset},#{size}")
    List<Question> selectList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    List<Question> selectListByUserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    //根据正则的方式匹配 标签
    @Select("select * from question where id!=#{id} and tag REGEXP #{tag}")
    List<Question> selectRelated(Question question);

    @Select("select * from question limit #{offset},#{pageSize}")
    List<Question> list(@Param("offset")int offset,@Param("pageSize")int pageSize);
}

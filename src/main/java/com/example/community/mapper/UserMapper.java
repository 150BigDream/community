package com.example.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
//    @Select("select * from user where token=#{token}")
//    User findByToken(@Param("token") String token);//不是类的话需要加这个注解

//    @Insert("insert into user values(accountId=#{accountId},name=#{name},gmtModified=#{gmtModified},gmtCreate=#{gmtCreate}，bio=#{bio},token=#{token})")
//    int insert(User user);

//    @Select("select * from user")
//    List<User> selectAll();

//    @Select("select * from user where account_id=#{accountId}")
//    User selectByAccountId(@Param("accountId") String accountId);

//    @Update("update user set name=#{name},token=#{token},gmt_create=#{gmtCreate},gmt_modified=#{gmtModified}")
//    void update(User User);
}

package com.example.community.mapper;

import com.example.community.model.Notification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zz
 * @since 2020-03-16
 */
public interface NotificationMapper extends BaseMapper<Notification> {

    @Select("select * from notification where receiver=#{userId} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Notification> selectListByUserId(Long userId, Integer offset, Integer size);
}

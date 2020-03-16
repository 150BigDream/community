package com.example.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.lang.reflect.Type;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zz
 * @since 2020-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 这个人的动作引发了通知
     */
    private Long notifier;

    /**
     * 通知给这个人
     */
    private Long receiver;

    /**
     * 评论/问题的id
     */
    private Long outerId;

    /**
     * 回复了评论还是回复了问题
     */
    private Integer type;

    private Long gmtCreate;

    /**
     * 已读未读
     */
    private Integer status;

    private String notifierName;
    private String outerTitle;

}

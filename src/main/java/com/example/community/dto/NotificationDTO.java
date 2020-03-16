package com.example.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;

    /**
     * 已读还是未读
     */
    private Integer status;

    /**
     * 引发通知的那个人的id，评论人
     */
    private Long notifier;
    /**
     * 引发通知的那个人的名字，评论人
     */
    private String notifierName;
    /**
     * 评论对象的title
     */
    private String outerTitle;
    /**
     * 评论对象的id
     */
    private Long outerId;
    /**
     * 回复的类型，回复的是评论还是问题
     */
    private String typeName;
    /**
     * 回复的类型的key，回复的是评论还是问题
     */
    private Integer type;
}

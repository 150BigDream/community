package com.example.community.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {

    private Long id;

    private Long parentId;

    /**
     * 1级还是2级
     */
    private Integer type;

    /**
     * user id
     */
    private Long commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private Long commentCount;

    private String content;

    private User user;
}

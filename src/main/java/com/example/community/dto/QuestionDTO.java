package com.example.community.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Long viewCount;
    private Long likeCount;
    private Long commentCount;

    private User user;
}

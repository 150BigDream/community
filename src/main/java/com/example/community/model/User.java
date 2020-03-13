package com.example.community.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String accountId;
    private String name;
    private Long gmtCreate;
    private Long gmtModified;
    private String token;
    private String bio;
    private String avatarUrl;
}

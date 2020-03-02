package com.example.community.dto;

import lombok.Data;

/**
 * 方法里需要两个以上的参数，最好封装成一个对象
 */

@Data
public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}

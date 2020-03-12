package com.example.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    public String avatarUrl;
    private String id;
    private String name;
    private String bio;
}

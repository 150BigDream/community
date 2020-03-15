package com.example.community.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2020-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long parentId;

    /**
     * 评论的是问题还是评论
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

}

package com.ggicome.community.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @program: community
 * @description: blog
 * @author: ggicome
 * @date: 2021-11-07
 **/
@Data
@Builder
@TableName("COMMUNITY_BLOG")
public class Blog {
    @TableId
    private String id;
    private String title;
    private String author;
    private String content;
}

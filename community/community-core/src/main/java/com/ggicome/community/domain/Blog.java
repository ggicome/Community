package com.ggicome.community.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ggicome.community.annotation.BlogContentRule;
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
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String author;
    /**
     * 内容
     */
    @BlogContentRule
    private String content;
}

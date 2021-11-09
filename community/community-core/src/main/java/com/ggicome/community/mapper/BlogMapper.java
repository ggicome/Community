package com.ggicome.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggicome.community.domain.Blog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: community
 * @description: blogMapper
 * @author: ggicome
 * @date: 2021-11-08
 **/
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
}

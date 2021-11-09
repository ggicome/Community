package com.ggicome.community;

import com.ggicome.community.domain.Blog;
import com.ggicome.community.mapper.BlogMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RequiredArgsConstructor
class CommunityApplicationTests {

    private final BlogMapper blogMapper;

    @Test
    void contextLoads() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("id", "1");
        List<Blog> list = blogMapper.selectByMap(columnMap);
        System.out.println(list);
    }

}

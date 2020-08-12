package com.gh.blog;

import com.gh.blog.entity.Demo;
import com.gh.blog.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BlogApplication.class)
@AutoConfigureMockMvc
public class BlogApplicationTests {

    @Autowired
    private DemoService service;

    @Test
    public void serviceTest() {
        List<Demo> list = service.getAll();
        System.err.println(list.toString());
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void controllerTest() throws Exception {
        MvcResult m = mvc.perform(MockMvcRequestBuilders.get("/demo").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        System.err.println("Controller返回信息:" + m.getResponse().getContentAsString());
    }


}

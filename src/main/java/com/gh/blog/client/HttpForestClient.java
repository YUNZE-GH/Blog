package com.gh.blog.client;

import com.dtflys.forest.annotation.Request;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/18 8:49
 */
public interface HttpForestClient {
    // GET请求
    @Request(
            url = "http://localhost:8082/test",
            type = "get",
            headers = {
                "Content-Type: application/json;charset=UTF-8"
            }
    )
    String simpleGet();

    @Request(
            url = "http://localhost:8082/api/restful/${0}",
            type = "get"
    )
    String restfulGet(String id);
}

package com.yuwenyun.demos.unit_test.cases;

import com.yuwenyun.demos.unit_test.util.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @author lijing
 * @version 1.0
 * @date 2019-06-11 10:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    private RestTemplate template;
    private final String baseUrl = "http://10.25.191.78:8090/druid/indexer/v1/task";

    @Test
    public void testGetForEntity(){
        String druidTaskId = "index_parallel_ad_convert_bidword_2019-06-10T15:48:55.011Z";

        ResponseEntity<String> result =
            template.getForEntity(baseUrl + "/" + druidTaskId + "/status", String.class);
        String response = result.getBody();

        // 通过system.out.println()查看具体返回值，在此基础上做期望值验证
//        System.out.println(result.getBody());
        Assert.assertNotNull(response);
        Assert.assertEquals("FAILED",
            JsonUtil.getValueByFlattenedKey(response, "status.statusCode"));
    }
}

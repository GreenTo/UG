package com.wteam.ug;

import com.wteam.ug.util.TokenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UgApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void testToken(){
        String token = TokenUtils.createJwtToken("李白");
        System.out.println(token);
    }

}

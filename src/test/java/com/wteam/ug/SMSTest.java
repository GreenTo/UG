package com.wteam.ug;

import com.wteam.ug.util.SMSSendUtil;
import org.junit.Test;

public class SMSTest {

//    private String[] template = new String[]{"6532","3"};

    @Test
    public void test(){
        System.out.println(SMSSendUtil.createRandomVcode());
//        String[] template = new String[]{SMSSendUtil.createRandomVcode(),"3"};
//        boolean b = SMSSendUtil.sendSms("13590544325", true, template);
//        System.out.println(b);
    }
}

package com.wteam.ug.interceptor;

import com.wteam.ug.util.TokenUtils2;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null){
            Map<String, Object> valid = TokenUtils2.valid(token);
            int i = (int) valid.get("Result");
            if (i == 0) {
                System.out.println("token解析成功");
                JSONObject jsonObject = (JSONObject) valid.get("data");
                int id = (int)jsonObject.get("id");
                String username = (String)jsonObject.get("username");
                int symbol = (int)jsonObject.get("symbol");
                if (symbol == 0) {
                    System.out.println("该用户id:"+id);
                    System.out.println("该用户username:"+username);
                } if (symbol == 1){
                    System.out.println("该公司id:"+id);
                    System.out.println("该公司username:"+username);
                }
                System.out.println("exp是"+jsonObject.get("exp"));
            } else if (i == 2) {
                System.out.println("token已经过期");
                return false;
            }

        }


        return false;
    }
}

package com.wteam.ug.interceptor;

import com.nimbusds.jose.JOSEException;
import com.wteam.ug.entity.Log;
import com.wteam.ug.repository.LogRepository;
import com.wteam.ug.util.TokenUtils2;
import net.minidev.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: cookie
 * @Date: 2018/7/27 10:17
 * @Description: 使用AOP统一处理Web请求日志
 */
@Aspect
@Component
public class WebControllerAop {
      /**
      * 指定切点
      * 匹配 com.example.demo.controller包及其子包下的所有类的所有方法
      */

      @Autowired
      LogRepository logRepository;

       @Pointcut("execution(public * com.wteam.ug.controller.*.*(..))")
       public void webLog(){
        }

     /**
      * 前置通知，方法调用前被调用
      * @param joinPoint
      */
       @Before("webLog()")
       public void doBefore(JoinPoint joinPoint){
//           System.out.println("我是前置通知!!!");
//           //获取目标方法的参数信息
//           Object[] obj = joinPoint.getArgs();
//           Signature signature = joinPoint.getSignature();
//           //代理的是哪一个方法
//           System.out.println("方法："+signature.getName());
//           //AOP代理类的名字
//           System.out.println("方法所在包:"+signature.getDeclaringTypeName());
//           //AOP代理类的类（class）信息
//           signature.getDeclaringType();
//           MethodSignature methodSignature = (MethodSignature) signature;
//           String[] strings = methodSignature.getParameterNames();
//           System.out.println("参数名："+Arrays.toString(strings));
//           System.out.println("参数值ARGS : " + Arrays.toString(joinPoint.getArgs()));
//           // 接收到请求，记录请求内容
//           ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//           HttpServletRequest req = attributes.getRequest();
//           // 记录下请求内容
//           System.out.println("请求URL : " + req.getRequestURL().toString());
//           System.out.println("HTTP_METHOD : " + req.getMethod());
//           System.out.println("IP : " + req.getRemoteAddr());
//           System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
       }

    /**
     * 处理完请求返回内容
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
//        System.out.println("方法的返回值 : " + ret);
    }

    /**
     * 后置异常通知
     * @param jp
     */
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp){
        System.out.println("方法异常时执行.....");
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     * @param jp
     */
    @After("webLog()")
    public void after(JoinPoint jp) throws ParseException, JOSEException {
        //获取目标方法的参数信息
        Object[] obj = jp.getArgs();
        Signature signature = jp.getSignature();
        //代理的是哪一个方法
        System.out.println("方法："+signature.getName());
        //AOP代理类的名字
        System.out.println("方法所在包:"+signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] strings = methodSignature.getParameterNames();
        System.out.println("参数名："+Arrays.toString(strings));
        System.out.println("参数值ARGS : " + Arrays.toString(jp.getArgs()));
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        // 记录下请求内容
        String url = req.getRequestURL().toString();
        System.out.println("请求URL : " + url);
        System.out.println("HTTP_METHOD : " + req.getMethod());
        System.out.println("IP : " + req.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName());
//      -------------------------------------------------------
        Log log = new Log();
        String inter = url.substring(url.lastIndexOf("/",url.lastIndexOf("/")-1)+1);
        log.setDate(new Date());
        log.setInter(inter);
        String params = "";
        for (int i = 0;i < strings.length;i++){
            params += strings[i] + " ";
        }
        log.setParams(params);
        String token = req.getHeader("Authorization");
        if (token != null){
            Map<String, Object> valid = TokenUtils2.valid(token);
            int i = (int) valid.get("Result");
            if (i == 0) {
                JSONObject jsonObject = (JSONObject) valid.get("data");
                String username = (String)jsonObject.get("username");
                System.out.println("该用户username:"+username);
                log.setUsername(username);
            } else if (i == 2) {
                System.out.println("token已经过期");
            }
        }
        logRepository.save(log);
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     */
    @Around("webLog()")
    public Object arround(ProceedingJoinPoint pjp) {
        try {
            Object o =  pjp.proceed();
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
package com.yeming.site.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.yeming.site.aop.annotation.SysOperationLog;
import com.yeming.site.dao.entity.BackstageUserDO;
import com.yeming.site.dao.entity.SysOperationLogDO;
import com.yeming.site.dao.repository.SysOperationLogRepository;
import com.yeming.site.util.CommonUtils;
import com.yeming.site.util.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author yeming.gao
 * @Description: @SysOperationLog注解的AOP实现
 * @date 2019/11/26 9:48
 */
@Aspect
@Component
public class SysOperationLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysOperationLogAspect.class);
    /**
     * 最大返回值长度
     */
    private static final int MAX_LENGTH = 1000;

    @Resource
    private HttpServletRequest request;

    @Resource
    private SysOperationLogRepository sysOperationLogRepository;

    /**
     * 此处的切点是注解的方式，也可以用包名的方式达到相同的效果
     * '@Pointcut("execution(* com.yeming.site.controller.aop.*.*(..))")'
     */
    @Pointcut("@annotation(com.yeming.site.aop.annotation.SysOperationLog)")
    public void operationLog() {
    }


    /**
     * 进入切点之后,返回结果之后
     *
     * @param joinPoint 切点
     * @param returning 返回结果
     */
    @AfterReturning(pointcut = "operationLog()", returning = "returning")
    public void afterReturningPointcut(JoinPoint joinPoint, Object returning) {
        //获取登陆用户
        BackstageUserDO user = (BackstageUserDO) CommonUtils.getLoginUser(request);
        //获取ip
        String ip = IPUtils.getIpAddr(request);
        //获取请求的url
        String url = request.getServletPath();
        //获取标题
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysOperationLog sysOperationLog = method.getAnnotation(SysOperationLog.class);
        String title = sysOperationLog.value();
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = className + "." + signature.getName();
        //请求的参数
        String params = "";
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            params = args[0].toString();
            if (params.length() > MAX_LENGTH) {
                params = params.substring(0, MAX_LENGTH);
            }
        }

        String result = JSON.toJSONString(returning);
        if (result.length() > MAX_LENGTH) {
            result = result.substring(0, MAX_LENGTH);
        }
        //保存系统日志
        SysOperationLogDO sysOperationLogDO = new SysOperationLogDO();
        sysOperationLogDO.setOperUser(Objects.isNull(user) ? "-" : user.getLoginUser());
        sysOperationLogDO.setOperIp(ip);
        sysOperationLogDO.setOperUrl(url);
        sysOperationLogDO.setOperTitle(title);
        sysOperationLogDO.setOperMethod(methodName);
        sysOperationLogDO.setOperParams(params);
        sysOperationLogDO.setOperResult(result);
        sysOperationLogRepository.save(sysOperationLogDO);
        LOGGER.info("@SysOperationLogAspect注解进入切点之后并且在返回结果之后，记录系统操作日志信息：{}", JSON.toJSONString(sysOperationLogDO));
    }

//    @Before("operationLog()")
//    public void beforePointcut(JoinPoint joinPoint) {
//        System.out.println("进入切点之前before");
//    }

//    @After("operationLog()")
//    public void afterPointcut(JoinPoint joinPoint) {
//        System.out.println("进入切点之后,返回结果之前after");
//    }

//    @Around("operationLog()")
//    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//        Object retmsg = pjp.proceed();
//        System.out.println("进入环绕切点" + retmsg);
//        return retmsg;
//    }
}

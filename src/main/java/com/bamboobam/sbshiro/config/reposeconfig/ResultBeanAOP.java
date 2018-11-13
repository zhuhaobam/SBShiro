package com.bamboobam.sbshiro.config.reposeconfig;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 对controller层进行切入自动装箱返回resultbean
 * RestController
 */

@Aspect
@Component
public class ResultBeanAOP {

    private static final Logger logger = LoggerFactory.getLogger(ResultBeanAOP.class);

    //@Pointcut("execution(public * com.bamboobam.sbshiro.controller..*.*(..))")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
    public void logPointCut() {
    }

    /**
     * 环绕通知 @Around
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) {
        long startTime = System.currentTimeMillis();
       /* Annotation restCAnnotation = point.getTarget().getClass().getAnnotation(RestController.class);
        //获取真实对象的方法和注解
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        Method realMethod = point.getTarget().getClass().getDeclaredMethod(point.getSignature().getName(), method.getParameterTypes());
        Annotation restBAnnotation = method.getAnnotation(ResponseBody.class);*/
        ResultBean<?> result;
        try {
            result = (ResultBean<?>) point.proceed();
            logger.info(point.getSignature() + "   AOP-响应JSON(" + result.toString() + ")时间:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            if (e instanceof CheckException) {
                logger.info(point.getSignature() + "   AOP-响应JSON-CheckException异常(" + e.toString() + ")时间:" + (System.currentTimeMillis() - startTime));
                result = new ResultBean(e.getMessage());
            } else {
                logger.info(point.getSignature() + "AOP-响应-其它异常(" +  e.getMessage() + ")时间:" + (System.currentTimeMillis() - startTime));
                result = new ResultBean(e);

            }
        }
        return result;
    }


}

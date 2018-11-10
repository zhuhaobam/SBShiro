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
 */

@Aspect
@Component
public class ResultBeanAOP {

    private static final Logger logger = LoggerFactory.getLogger(ResultBeanAOP.class);

    @Pointcut("execution(public * com.bamboobam.sbshiro.controller..*.*(..))")
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
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        try {
            result = (ResultBean<?>) point.proceed();
            logger.info(point.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(point, e);
        }

        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean();

        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(((CheckException) e).getMsg());
            result.setCode(1);
        } else {
            logger.error(pjp.getSignature() + " error ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result.setMsg(e.toString());
            result.setCode(0);
        }
        return result;
    }

}

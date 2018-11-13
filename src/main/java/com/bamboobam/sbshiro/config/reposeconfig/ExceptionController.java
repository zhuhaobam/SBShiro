package com.bamboobam.sbshiro.config.reposeconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常统一处理
 */
@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    @ExceptionHandler(RuntimeException.class)
    public ResultBean handleException(RedirectAttributes redirectAttributes, Exception exception, HttpServletRequest request) {
        logger.info("ControllerAdvice-响应-其它异常(" +  exception.getMessage() +")");
        return new ResultBean(exception);
    }

}
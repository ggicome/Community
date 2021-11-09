package com.ggicome.community.aop;

import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.ggicome.community.annotation.ResponseWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.HttpConstraintElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import javax.xml.ws.spi.http.HttpContext;

/**
 * @program: community
 * @description: ResponseWrapperAop
 * @author: ggicome
 * @date: 2021-11-09
 **/
@RestControllerAdvice
@Configuration
@Aspect
@ConditionalOnClass({ProceedingJoinPoint.class})
@RequiredArgsConstructor
public class ResponseWrapperAop {

    private final HttpServletRequest request;

    public static final String RESPONSE_WRAPPER_ATTR = "com.ggicome.community.annotation.ResponseWrapper.attr";

    /**
     * 作用于类或者方法上的ResponseWrapper注解拦截
     *
     * @param joinPoint       joinPoint
     * @param responseWrapper responseWrapper
     * @return Object
     * @within和@target针对类的注解,@annotation是针对方法的注解
     */
    @Around("@within(responseWrapper)||@annotation(responseWrapper)")
    Object responseWrapperAop(ProceedingJoinPoint joinPoint, ResponseWrapper responseWrapper) throws Throwable {
        try {
            Object data = joinPoint.proceed();
            ResponseWrapperAop.ResponseWrapperVO wrapperVO = ResponseWrapperVO.builder().success(true).status(200).data(data).build();
            request.setAttribute(RESPONSE_WRAPPER_ATTR, wrapperVO);
            return data;
        } catch (Exception e) {
            ResponseWrapperAop.ResponseWrapperVO wrapperVO = ResponseWrapperVO.builder().success(false).status(500).data(e.getStackTrace()).build();
            request.setAttribute(RESPONSE_WRAPPER_ATTR, wrapperVO);
            // 此处有问题，应该是不同的类型返回相应类型的默认值
            return null;
        }
    }

    @Getter
    @Builder
    public static class ResponseWrapperVO {
        private boolean success;
        private int status;
        private Object data;
    }

}

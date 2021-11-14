package com.ggicome.community.aop;

import com.ggicome.community.annotation.ResponseWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
    /**
     * 基本数据类型默认值
     */
    private static final Map<Class<?>, Object> CLASS_DEFAULT = new HashMap<>();

    private final HttpServletRequest request;

    public static final String RESPONSE_WRAPPER_ATTR = "com.ggicome.community.annotation.ResponseWrapper.attr";

    static {
        CLASS_DEFAULT.put(byte.class, 0);
        CLASS_DEFAULT.put(int.class, 0);
        CLASS_DEFAULT.put(char.class, '\u0000');
        CLASS_DEFAULT.put(boolean.class, false);
        CLASS_DEFAULT.put(float.class, 0.0f);
        CLASS_DEFAULT.put(double.class, 0.0d);
        CLASS_DEFAULT.put(long.class, 0L);
        CLASS_DEFAULT.put(short.class, 0);
    }

    /**
     * 作用于类或者方法上的ResponseWrapper注解拦截
     *
     * @param joinPoint       joinPoint
     * @param responseWrapper responseWrapper
     * @return Object
     * @within和@target针对类的注解,@annotation是针对方法的注解
     */
    @Around("@within(responseWrapper)||@annotation(responseWrapper)")
    Object responseWrapperAop(final ProceedingJoinPoint joinPoint, ResponseWrapper responseWrapper) throws Throwable {
        try {
            Object data = joinPoint.proceed();
            ResponseWrapperAop.ResponseWrapperVO wrapperVO = ResponseWrapperVO.builder().success(true).status(HttpStatus.OK.value()).data(data).build();
            request.setAttribute(RESPONSE_WRAPPER_ATTR, wrapperVO);
            return data;
        } catch (Exception e) {
            ResponseWrapperAop.ResponseWrapperVO wrapperVO = ResponseWrapperVO.builder().success(false).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).data(e.getStackTrace()).build();
            request.setAttribute(RESPONSE_WRAPPER_ATTR, wrapperVO);
            return returnNull(joinPoint);
        }
    }

    private Object returnNull(final ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            // 被切的方法
            Method method = methodSignature.getMethod();
            // 返回类型
            Class<?> methodReturnType = method.getReturnType();
            return CLASS_DEFAULT.get(methodReturnType);
        }
        return null;
    }

    @Getter
    @Builder
    public static class ResponseWrapperVO {
        private boolean success;
        private int status;
        private Object data;
    }

}

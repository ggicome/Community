package com.ggicome.community.configration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggicome.community.aop.ResponseWrapperAop;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Method;

/**
 * @program: community
 * @description: ResponseWrapperConfigration
 * @author: ggicome
 * @date: 2021-11-09
 **/
@Configuration
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseWrapperConfigration implements ResponseBodyAdvice<Object> {
    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(@NotNull MethodParameter returnType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(final Object body, @NotNull final MethodParameter returnType,
                                  @NotNull final MediaType selectedContentType,
                                  @NotNull final Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NotNull final ServerHttpRequest request,
                                  @NotNull final ServerHttpResponse response) {
        HttpServletRequest httpServletRequest;
        if (request instanceof HttpServletRequest) {
            httpServletRequest = (HttpServletRequest) request;
        } else if (request instanceof ServletServerHttpRequest) {
            httpServletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        } else {
            return body;
        }
        Object obj = httpServletRequest.getAttribute(ResponseWrapperAop.RESPONSE_WRAPPER_ATTR);
        if (!(obj instanceof ResponseWrapperAop.ResponseWrapperVO)) {
            return body;
        }
        ResponseWrapperAop.ResponseWrapperVO wrapperVO = (ResponseWrapperAop.ResponseWrapperVO) obj;
        Method method = returnType.getMethod();
        if (method != null && method.getReturnType() == String.class) {
            return objectMapper.writeValueAsString(wrapperVO);
        }
        return wrapperVO;
    }
}

package com.ggicome.community.annotation;

import com.ggicome.community.validator.BlogContentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BlogContentRule
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BlogContentValidator.class)
public @interface BlogContentRule {
    String message() default "内容包含敏感字符";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

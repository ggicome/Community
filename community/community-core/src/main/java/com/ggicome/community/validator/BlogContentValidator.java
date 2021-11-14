package com.ggicome.community.validator;

import com.ggicome.community.annotation.BlogContentRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @program: community
 * @description: contentValidator
 * @author: ggicome
 * @date: 2021-11-14
 **/
public class BlogContentValidator implements ConstraintValidator<BlogContentRule, String> {

    /**
     * 校验（逻辑待完善）
     *
     * @param content                    内容
     * @param constraintValidatorContext constraintValidatorContext
     * @return boolean
     */
    @Override
    public boolean isValid(String content, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}

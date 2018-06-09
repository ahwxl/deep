/**
 * www.bplow.com
 */
package com.bplow.deep.base.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bplow.deep.base.validator.custom.CardNumberValidator;

/**
 * @desc 卡号验证
 * @author wangxiaolei
 * @date 2018年6月9日 下午4:36:33
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=CardNumberValidator.class)
public @interface CardNumberConstraint {
	
	String message() default "卡好不能为空";
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}

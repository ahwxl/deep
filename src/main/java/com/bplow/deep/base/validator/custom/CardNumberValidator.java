/**
 * www.bplow.com
 */
package com.bplow.deep.base.validator.custom;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.bplow.deep.base.validator.annotation.CardNumberConstraint;

/**
 * @desc 卡号验证规则
 * @author wangxiaolei
 * @date 2018年6月9日 下午4:37:57
 */
public class CardNumberValidator implements ConstraintValidator<CardNumberConstraint, String> {

	@Override
	public void initialize(CardNumberConstraint arg0) {

	}

	@Override
	public boolean isValid(String cardNumber, ConstraintValidatorContext constraintValidatorContext) {

		if (!StringUtils.isEmpty(cardNumber)) {
			return true;
		} else {
			//constraintValidatorContext.disableDefaultConstraintViolation();// 禁用默认的message的值
			// 重新添加错误提示语句
			//constraintValidatorContext.buildConstraintViolationWithTemplate("卡号不能为空").addConstraintViolation();

		}

		return false;
	}

}

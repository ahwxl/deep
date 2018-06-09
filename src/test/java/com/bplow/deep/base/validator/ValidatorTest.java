/**
 * www.bplow.com
 */
package com.bplow.deep.base.validator;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import com.bplow.deep.base.validator.annotation.CardNumberConstraint;

/**
 * @desc  对controller的form表单入参进行校验
 *        对service调用的入参进行校验，需要返回 错误码和错误描述字段  
 *        需要对框架提供的参数进行包装，提供注解，并在框架层校验
 * @author wangxiaolei
 * @date 2018年6月9日 下午4:20:36
 */
@ContextConfiguration(locations = {"/applicationContext.xml", "/spring/cxt-validator.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ActiveProfiles("development")
public class ValidatorTest {

	@Autowired
	private Validator ivalidator;

	@Test
	public void testValidator() {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Order entity = new Order();

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(entity);
		for (ConstraintViolation<Order> constraintViolation : constraintViolations) {
			System.out.println("对象属性:" + constraintViolation.getPropertyPath());
			System.out.println("国际化key:" + constraintViolation.getMessageTemplate());
			System.out.println("错误信息:" + constraintViolation.getMessage());
		}

	}

	@Test
	public void testCodeMapping() {

		Order entity = new Order();

		Set<ConstraintViolation<Order>> constraintViolations = ivalidator.validate(entity);

		for (ConstraintViolation<Order> constraintViolation : constraintViolations) {
			System.out.println("对象属性:" + constraintViolation.getPropertyPath());
			System.out.println("国际化key:" + constraintViolation.getMessageTemplate());
			System.out.println("错误信息:" + constraintViolation.getMessage());
		}

	}

	@Test
	public void testDataBinder() {
		Order target = new Order();
		DataBinder binder = new DataBinder(target);
		// binder.addValidators(ivalidator);
		binder.setValidator((org.springframework.validation.Validator) ivalidator);
		// bind to the target object
		// binder.bind(target);
		// validate the target object
		binder.validate();
		// get BindingResult that includes any validation errors
		BindingResult results = binder.getBindingResult();

		Assert.assertEquals(3, results.getErrorCount());

		for (ObjectError error : results.getAllErrors()) {
			System.out.println(error.getCode() + ":" + error.getDefaultMessage());
		}

	}

	class Order {

		@NotEmpty(message = "{1000}")
		private String orderId;

		@NotNull(message = "{EB001}")
		private String orderName;

		@CardNumberConstraint(message = "{EB001}")
		private String cardNumber;

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getOrderName() {
			return orderName;
		}

		public void setOrderName(String orderName) {
			this.orderName = orderName;
		}

	}

}

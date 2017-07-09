/**
 * www.bplow.com
 */
package com.bplow.deep.classload.web;

import java.beans.BeanInfo;
import java.beans.Introspector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bplow.deep.base.utils.CompileMemery;
import com.bplow.deep.base.ws.RefreshBeanService;

/**
 * @desc
 * @author wangxiaolei
 * @date 2017年6月30日 下午2:06:47
 */
@Controller
@RequestMapping("/class")
public class ClassLoadController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompileMemery compileMemery;

	@Autowired
	private RefreshBeanService refreshBeanService;

	@RequestMapping(value = "/fresh")
	@ResponseBody
	public String index(HttpServletRequest httpRequest, Model view) throws Exception {

		logger.info("刷新bean请求");
		
		Class beanClass = compileMemery.comppile("MyPrinter2");

		System.out.println(beanClass.getClassLoader());

		BeanInfo beanInfo = Introspector.getBeanInfo(beanClass,
				Introspector.IGNORE_ALL_BEANINFO);

		System.out.println(beanInfo);

		return "cms/article";
	}

}

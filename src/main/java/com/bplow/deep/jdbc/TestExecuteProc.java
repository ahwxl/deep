/**
 * www.bplow.com
 */
package com.bplow.deep.jdbc;

import java.sql.Types;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.BeanFactoryDataSourceLookup;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2019年1月12日 下午3:18:41
 */
public class TestExecuteProc {
	
	private static final String DATASOURCE_BEAN_NAME = "dataSource";
	
	@Test
	public void test(){
		
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		/*dataSource.setUsername("inf");
		dataSource.setPassword("inf");
		dataSource.setUrl("jdbc:oracle:thin:@192.168.89.200:1521:fftdb");
		dataSource.setDriverClass(oracle.jdbc.driver.OracleDriver.class);*/
		
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		dataSource.setUrl("jdbc:mysql://localhost:3306/deep?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		
		/*SimpleJdbcCall adder = new SimpleJdbcCall(dataSource).withProcedureName("add_invoice");
		adder.declareParameters(
				new SqlParameter("amount", Types.INTEGER),
				new SqlParameter("custid", Types.INTEGER),
				new SqlOutParameter("newid",
				Types.INTEGER));
		Number newId = adder.executeObject(Number.class, new MapSqlParameterSource().
				addValue("amount", 1103).
				addValue("custid", 3));*/
		
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		int count = jdbcTemplate.queryForInt("select count(1) from sk_schedule_task");
		
		System.out.println(count);
		
		BeanFactoryDataSourceLookup lookup = new BeanFactoryDataSourceLookup();
		lookup.getDataSource(DATASOURCE_BEAN_NAME);
		
	}

}

/**
 * www.bplow.com
 */
package com.bplow.deep.quartz.job;

import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * @desc
 * @author wangxiaolei
 * @date 2019年1月12日 上午10:42:45
 */
public class TestJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		try {

			dataSource.setUsername("root");
			dataSource.setPassword("123456");
			dataSource.setUrl("jdbc:mysql://localhost:3306/deep?useUnicode=true&characterEncoding=UTF-8");
			dataSource.setDriverClass(com.mysql.jdbc.Driver.class);

			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

			int count = jdbcTemplate.queryForInt("select count(1) from sk_schedule_task");

			System.out.println(count);

			System.out.println("测试被执行" + count);

		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {
			try {
				dataSource.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		

	}

}

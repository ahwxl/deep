package com.bplow.deep.base.groovy;

import java.io.IOException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scripting.ScriptSource;
import org.springframework.util.StringUtils;

public class DatabaseScriptSource implements ScriptSource {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String scriptName;
	private final JdbcTemplate jdbcTemplate;
	private Timestamp lastKnownUpdate;

	private final Object lastModifiedMonitor = new Object();

	public DatabaseScriptSource(String scriptName, DataSource dataSource) {
		this.scriptName = scriptName;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String getScriptAsString() throws IOException {
		synchronized (this.lastModifiedMonitor) {
			this.lastKnownUpdate = retrieveLastModifiedTime();
		}
		String sql = (String) jdbcTemplate
				.queryForObject(
						"select groovy_script from tb_parse_script where groovy_bean_name = ?",
						new Object[] { this.scriptName }, String.class);
		logger.info("查询数据库脚本:{}",sql);
		return sql;
	}

	public boolean isModified() {
		synchronized (this.lastModifiedMonitor) {
			Timestamp lastUpdated = retrieveLastModifiedTime();
			return lastUpdated.after(this.lastKnownUpdate);
		}
	}

	public String suggestedClassName() {
		return StringUtils.stripFilenameExtension(this.scriptName);
	}

	private Timestamp retrieveLastModifiedTime() {
		return (Timestamp) this.jdbcTemplate
				.queryForObject(
						"select gmt_modify from tb_parse_script where groovy_bean_name = ?",
						new Object[] { this.scriptName }, Timestamp.class);
	}

}

package com.trupti.eventplanner.eventplanner;

import com.trupti.eventplanner.eventplanner.task.JDBCConnector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class EventplannerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EventplannerApplication.class, args);
	}

	@Bean
	public JDBCConnector schedulerRunner(JdbcTemplate jdbcTemplate) {
		JDBCConnector jdbcConnector = new JDBCConnector();
		jdbcConnector.setJdbcTemplate(jdbcTemplate);
		return jdbcConnector;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EventplannerApplication.class);
	}

}

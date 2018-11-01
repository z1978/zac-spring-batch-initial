package com.zac.batch.job.first;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.PersonDto;

@Component(BatchConstants.FIRST_JOB_EXECUTION_LISTENER_ID)
public class FirstJobExecutionListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstJobExecutionListener.class);

	private final JdbcTemplate jdbcTemplate;

	public FirstJobExecutionListener(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("!!! JOB FINISHED! Time to verify the results");

			List<PersonDto> results = jdbcTemplate.query("SELECT first_name, last_name FROM person", new RowMapper<PersonDto>() {
				public PersonDto mapRow(ResultSet rs, int row) throws SQLException {
					String firstName = rs.getString(1);
					String lastName = rs.getString(2);
					return new PersonDto(firstName, lastName);
				}
			});

			int nbResults = ((results == null) ? 0 : results.size());
			LOGGER.info("Found {} entities in the database.", nbResults);
			if (results != null) {
				int index = 0;
				for (PersonDto person : results) {
					index++;
					LOGGER.info("{}/{}: Found <{}> in the database.", index, nbResults, person);
				}
			}
		}
	}
}

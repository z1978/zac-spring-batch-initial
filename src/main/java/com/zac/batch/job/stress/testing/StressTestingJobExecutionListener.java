package com.zac.batch.job.stress.testing;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;

@Component(BatchConstants.STRESS_TESTING_JOB_EXECUTION_LISTENER_ID)
public class StressTestingJobExecutionListener extends JobExecutionListenerSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(StressTestingJobExecutionListener.class);

	private final JdbcTemplate jdbcTemplate;

	public StressTestingJobExecutionListener(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("!!! JOB FINISHED! Time to verify the results");
		}
	}
}

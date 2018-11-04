package com.zac.batch.job.first;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
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
		ExecutionContext ec = jobExecution.getExecutionContext();
		System.out.println(ec.get("message"));
		
		String jobName = jobExecution.getJobInstance().getJobName();
		Date jobStartTime = jobExecution.getStartTime();
		Date jobEndTime = jobExecution.getEndTime();
		BatchStatus jobBatchStatus = jobExecution.getStatus();
		String jobExitCode = jobExecution.getExitStatus().getExitCode();
		// 日付をlong値に変換します。
	    long dateTimeTo = jobEndTime.getTime();
	    long dateTimeFrom = jobStartTime.getTime();
	 
	    // 差分の日数を算出します。
	    long dayDiff = dateTimeTo - dateTimeFrom;//(1000 * 60 * 60 * 24 );
	    System.out.println("Program running time = [" + dayDiff / 1000f + "]s");
	    LOGGER.info("Program running time = [" + dayDiff / 1000f + "]s");

	    System.out.println(jobExecution.getStepExecutions().size());
		jobExecution.getStepExecutions().forEach(s -> {
			String stepName = s.getStepName();
			System.out.println(stepName);
			Date stepStartTime = s.getStartTime();
			System.out.println(stepStartTime);
			Date stepEndTime = s.getEndTime();
			System.out.println(stepEndTime);
			long stepTimeDiff = stepEndTime.getTime() - stepStartTime.getTime();//(1000 * 60 * 60 * 24 );
		    System.out.println("Step running time = [" + stepTimeDiff / 1000f + "]s");
			BatchStatus stepStatus = s.getStatus();
			System.out.println(stepStatus);
			String stepExitCode = s.getExitStatus().getExitCode();
			System.out.println(stepExitCode);

			// omitted.
		});

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("!!! JOB FINISHED! Time to verify the results");

			List<PersonDto> results = jdbcTemplate.query("SELECT first_name, last_name FROM person",
					new RowMapper<PersonDto>() {
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

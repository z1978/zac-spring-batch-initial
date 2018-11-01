package com.zac.batch.configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.StressTestingDto;
import com.zac.batch.entity.StressTesting;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class StressTestingJobConfiguration {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(StressTestingJobConfiguration.class);
  
	@Value("${stress.testing.chunck.size}")
	protected Integer chunckSize;

	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	@Autowired
	protected EntityManagerFactory entityManagerFactory;

	@Autowired
	protected JobRepository jobRepository;

	@Resource(name=BatchConstants.STRESS_TESTING_JOB_ITEM_READER_ID)
	protected ItemReader<StressTestingDto> reader;

	@Resource(name=BatchConstants.STRESS_TESTING_JOB_ITEM_PROCESSOR_ID)
	protected ItemProcessor<StressTestingDto, StressTesting> processor;

	@Resource(name=BatchConstants.STRESS_TESTING_JOB_ITEM_WRITER_ID)
	protected ItemWriter<StressTesting> writer;

	@Resource(name=BatchConstants.STRESS_TESTING_JOB_EXECUTION_LISTENER_ID)
	protected JobExecutionListener listener;

	// TODO
	@Bean
	public Job stressTestingJob(@Qualifier("stressTestingStep") Step stressTestingStep) throws Exception {
		return jobBuilderFactory.get(BatchConstants.STRESS_TESTING_JOB_ID)
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(stressTestingStep)
				.end()
				.build();
	}

	@Bean
	public Step stressTestingStep() throws Exception {
		return stepBuilderFactory.get(BatchConstants.STRESS_TESTING_JOB_STEP_ID)
				.repository(jobRepository)
				.<StressTestingDto, StressTesting> chunk(chunckSize)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

}

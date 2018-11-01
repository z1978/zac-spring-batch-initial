package com.zac.batch.job.stress.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.StressTestingDto;

@Component(BatchConstants.STRESS_TESTING_JOB_ITEM_READER_ID)
public class StressTestingReader extends FlatFileItemReader<StressTestingDto> implements StepExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(StressTestingReader.class);

	@Value("${csv.input.path}")
	protected String csvInputPath;

	public StressTestingReader() {
		super();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "testId", "testPoint", "testDate" });

		BeanWrapperFieldSetMapper<StressTestingDto> fieldSetMapper = new BeanWrapperFieldSetMapper<StressTestingDto>();
		fieldSetMapper.setTargetType(StressTestingDto.class);

		DefaultLineMapper<StressTestingDto> lineMapper = new DefaultLineMapper<StressTestingDto>();
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		setLineMapper(lineMapper);
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {

		JobParameters jobParameters = stepExecution.getJobParameters();
		String filePath = jobParameters.getString("filePath");
		LOGGER.info("filePath = [{}].", filePath);
		
		FileSystemResource resource = new FileSystemResource(filePath);
		setResource(resource);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		return null;
	}
}

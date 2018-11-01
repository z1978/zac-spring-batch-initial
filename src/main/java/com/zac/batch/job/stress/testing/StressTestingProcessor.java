package com.zac.batch.job.stress.testing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.StressTestingDto;
import com.zac.batch.entity.StressTesting;

@Component(BatchConstants.STRESS_TESTING_JOB_ITEM_PROCESSOR_ID)
public class StressTestingProcessor implements ItemProcessor<StressTestingDto, StressTesting> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StressTestingProcessor.class);

	public StressTesting process(StressTestingDto item) throws Exception {

		final String testPoint = item.getTestPoint().toUpperCase();
		final String testDate = item.getTestDate().toUpperCase();

		final StressTesting transformed = new StressTesting(testPoint, testDate);

		LOGGER.info("Converting (" + item + ") into (" + transformed + ")");

		return transformed;
	}
}
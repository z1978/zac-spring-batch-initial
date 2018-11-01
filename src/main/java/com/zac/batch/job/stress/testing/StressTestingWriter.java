package com.zac.batch.job.stress.testing;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.entity.StressTesting;
import com.zac.batch.service.StressTestingService;

@Component(BatchConstants.STRESS_TESTING_JOB_ITEM_WRITER_ID)
public class StressTestingWriter implements ItemWriter<StressTesting> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StressTestingWriter.class);

	@Autowired
	private StressTestingService stressTestingService;

	public void write(List<? extends StressTesting> items) throws Exception {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Writing to JPA with {} items.", items.size());
		}

		if (!items.isEmpty()) {
			List<StressTesting> list = this.stressTestingService.getAll();
			System.out.println(list.size());
			System.out.println("==========");
			
			for (StressTesting item : list) {
				System.out.println(item.getTestPoint());
				System.out.println(item.getTestDate());
				stressTestingService.createOne(item);
//				item.setFirstName("UUU");
//				stressTestingService.saveAndFlush(item);
			}
			
//			this.stressTestingService.deleteAll();
			List<StressTesting> list2 = this.stressTestingService.getAll();
			System.out.println(list2.size());
			System.out.println("==========");
//			long persistCount = 0;
//			long mergeCount = 0;
//
//			for (StressTesting item : items) {
//				if (item.getId() == null) {
//					entityManager.persist(item);
//					persistCount++;
//				} else {
//					entityManager.merge(item);
//					mergeCount++;
//				}
//			}
//			entityManager.flush();
//			entityManager.clear();
//
//			if (LOGGER.isInfoEnabled()) {
//				LOGGER.info("{} entities persisted.", persistCount);
//				LOGGER.info("{} entities merged.", mergeCount);
//			}
		}
	}
}

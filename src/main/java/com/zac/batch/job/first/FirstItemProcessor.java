package com.zac.batch.job.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.PersonDto;
import com.zac.batch.entity.Person;

@Component(BatchConstants.FIRST_JOB_ITEM_PROCESSOR_ID)
public class FirstItemProcessor implements ItemProcessor<PersonDto, Person> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstItemProcessor.class);

	public Person process(PersonDto item) throws Exception {

		final String firstName = item.getFirstName().toUpperCase();
		final String lastName = item.getLastName().toUpperCase();

		final Person transformed = new Person(firstName, lastName);

		LOGGER.info("Converting (" + item + ") into (" + transformed + ")");

		return transformed;
	}
}
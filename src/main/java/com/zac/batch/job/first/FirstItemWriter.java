package com.zac.batch.job.first;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;
import com.zac.batch.entity.Person;
import com.zac.batch.repository.PersonRepository;
import com.zac.batch.service.PersonService;

@Component(BatchConstants.FIRST_JOB_ITEM_WRITER_ID)
public class FirstItemWriter implements ItemWriter<Person> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstItemWriter.class);

	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonRepository personRepository;

	public void write(List<? extends Person> items) throws Exception {

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Writing to JPA with {} items.", items.size());
		}

		if (!items.isEmpty()) {
			personRepository.deleteAll();
			personRepository.saveAll(items);
			
			List<Person> list = this.personService.getAll();
			System.out.println(list.size());
			System.out.println("==========");			
			for (Person item : list) {
				System.out.println(item.getId());
				System.out.println(item.getFirstName());
				System.out.println(item.getLastName());
			}
			System.out.println("==========");
		}
	}
}

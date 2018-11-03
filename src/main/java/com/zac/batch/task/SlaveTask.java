package com.zac.batch.task;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zac.batch.ZacApplication;
import com.zac.batch.entity.Person;
import com.zac.batch.repository.PersonRepository;

@Component
public class SlaveTask implements Tasklet {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZacApplication.class);

	@Autowired
	private PersonRepository personRepository;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();
		// ジョブ起動パラメータの取得
		LOGGER.info("FirstTasklet has been executed. job param is {}", jobParameters);

		List<Person> list = personRepository.findAll();

		for (Person person : list) {
			System.out.println(person.getFirstName());
			System.out.println(person.getLastName());
		}

		String name = (String) chunkContext.getStepContext().getStepExecution().getExecutionContext().get("name");
		int fromId = (int) chunkContext.getStepContext().getStepExecution().getExecutionContext().get("from");
		int toId = (int) chunkContext.getStepContext().getStepExecution().getExecutionContext().get("to");

		System.out.println(name + ":" + fromId + "~" + toId);

		ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getJobExecution()
				.getExecutionContext();
		// ステップ間の情報引き継ぎはJobExecutionのExecutionContextを取得する。（StepExecutionのExecutionContextではダメ）
		executionContext.put("message", "foobar");

		return RepeatStatus.FINISHED;
	}
}

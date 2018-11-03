package com.zac.batch.task;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class Task1 implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		if (isCheckOK()) {
			// 成功
			contribution.setExitStatus(ExitStatus.COMPLETED);
		} else {
			// 失敗
			contribution.setExitStatus(ExitStatus.FAILED);
		}

		return RepeatStatus.FINISHED;
	}

	public boolean isCheckOK() {
		return true;
	}

}

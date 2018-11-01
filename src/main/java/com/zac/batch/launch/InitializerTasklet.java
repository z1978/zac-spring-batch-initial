package com.zac.batch.launch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.zac.batch.BatchConstants;

@Component
public class InitializerTasklet implements Tasklet, StepExecutionListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(InitializerTasklet.class);

  @Override
  public void beforeStep(StepExecution stepExecution) {
    ExecutionContext jobContext = stepExecution.getJobExecution()
        .getExecutionContext();

    String flowControl = stepExecution.getJobParameters().getString("param1");
    LOGGER.info("flowControl = " + flowControl);
    jobContext.put(BatchConstants.FLOW_CONTROL, flowControl);

  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution,
      ChunkContext chunkContext) throws Exception {
    return null;
  }
}

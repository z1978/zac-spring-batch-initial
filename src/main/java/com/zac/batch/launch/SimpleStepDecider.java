package com.zac.batch.launch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.item.ExecutionContext;

import com.zac.batch.BatchConstants;

public class SimpleStepDecider implements JobExecutionDecider {
  @Override
  public FlowExecutionStatus decide(JobExecution jobExecution, org.springframework.batch.core.StepExecution stepExecution) {
      ExecutionContext jobContext = jobExecution.getExecutionContext();
      String flowControl = jobContext.get(BatchConstants.FLOW_CONTROL).toString();
      return new FlowExecutionStatus(flowControl);
  }
} 

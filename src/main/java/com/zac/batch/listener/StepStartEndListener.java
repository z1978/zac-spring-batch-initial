package com.zac.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepStartEndListener implements StepExecutionListener {

  @Override
  public void beforeStep(StepExecution stepExecution) {
    // TODO Auto-generated method stub
    System.out.println("---------- Spring Batch Setp Start");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    // TODO Auto-generated method stub
    System.out.println("---------- Spring Batch Step End");
    return null;
  }

}
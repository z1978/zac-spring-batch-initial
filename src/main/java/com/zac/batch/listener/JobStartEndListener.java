package com.zac.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/*
 * バッチ処理のStart・Endを表示
 */
@Component
public class JobStartEndListener extends JobExecutionListenerSupport {

//  private final JdbcTemplate jdbcTemplate;
//
//  @Autowired
//  public JobStartEndListener(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
//  }

  // Job開始前に実行
  @Override
  public void beforeJob(JobExecution jobExecution) {
    super.beforeJob(jobExecution);
    System.out.println("---------- Spring Batch Job Start");
  }

  // Job終了後に実行
  @Override
  public void afterJob(JobExecution jobExecution) {
    super.afterJob(jobExecution);
    System.out.println("---------- Spring Batch Job End");
  }

}

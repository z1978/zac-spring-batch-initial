package com.zac.batch.configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zac.batch.BatchConstants;
import com.zac.batch.dto.PersonDto;
import com.zac.batch.entity.Person;
import com.zac.batch.task.DemoPartitioner;
import com.zac.batch.task.FirstTasklet;
import com.zac.batch.task.NextTasklet;
import com.zac.batch.task.SlaveTask;
import com.zac.batch.task.Task1;
import com.zac.batch.task.Task2;

@Configuration
@EnableBatchProcessing
@EnableTransactionManagement
public class FirstJobConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirstJobConfiguration.class);

	@Value("${first.chunck.size}")
	protected Integer chunckSize;

	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	@Autowired
	protected EntityManagerFactory entityManagerFactory;

	@Autowired
	protected JobRepository jobRepository;

	@Resource(name = BatchConstants.FIRST_JOB_ITEM_READER_ID)
	protected ItemReader<PersonDto> reader;

	@Resource(name = BatchConstants.FIRST_JOB_ITEM_PROCESSOR_ID)
	protected ItemProcessor<PersonDto, Person> processor;

	@Resource(name = BatchConstants.FIRST_JOB_ITEM_WRITER_ID)
	protected ItemWriter<Person> writer;

	@Resource(name = BatchConstants.FIRST_JOB_EXECUTION_LISTENER_ID)
	protected JobExecutionListener jobListener;

	@Autowired
	private Task1 task1;

	@Autowired
	private Task2 task2;

	@Autowired
	private DemoPartitioner demoPartitioner;

	@Autowired
	private SlaveTask slaveTask;
	
    @Autowired
    private StepBuilderFactory stepBuilders;

	// TODO
	@Bean
	public Job firstJob(Step taskletFirstStep, Step taskletNextStep, @Qualifier("firstStep") Step firstStep, Step masterStep, Step step1, Step step2)
			throws Exception {
	    
	    //　taskletFirstStepをflow1に登録
	    Flow flow1 = new FlowBuilder<SimpleFlow>("flow1").start(taskletFirstStep).next(taskletNextStep).build();
	    
	    //　並行処理のfirstStep、masterStepをflow2に登録
	    Flow flow2 = new FlowBuilder<SimpleFlow>("flow2").start(new FlowBuilder<Flow>("masterStep").from(masterStep).on(ExitStatus.COMPLETED.getExitCode()).to(step1).end())
	            .split(new SimpleAsyncTaskExecutor()).add(new FlowBuilder<Flow>("firstStep").from(firstStep).end()).build();
	    
		return jobBuilderFactory.get(BatchConstants.FIRST_JOB_ID)
				.repository(jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(jobListener)
				.start(flow1)
                .next(flow2)
//				.listener(jobListener)
//				.start(taskletFirstStep)
//				.next(taskletNextStep)
//				.next(masterStep)
//				.on(ExitStatus.COMPLETED.getExitCode())
//				.to(firstStep)
//				.from(masterStep)
//				.on(ExitStatus.FAILED.getExitCode())
//				.to(step2)
				.end()
				.build();
	}

	@Bean
	public Step firstStep() throws Exception {
		return stepBuilderFactory.get(BatchConstants.FIRST_JOB_STEP_ID)
//				.partitioner(slaveStep().getName(), demoPartitioner)
//				.partitionHandler(handler())
				.repository(jobRepository)
				.<PersonDto, Person>chunk(chunckSize)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}

	@Bean
	public Step step1() {
		LOGGER.info("step1");
		return stepBuilderFactory
				.get("step1")
				.tasklet(task1)
				.build();
	}

	@Bean
	public Step step2() {
		LOGGER.info("step2");
		return stepBuilderFactory
				.get("step2")
				.tasklet(task2)
				.build();
	}

	@Bean
	public Step slaveStep() {
		return stepBuilderFactory
				.get("slaveStep")
				.tasklet(slaveTask)
				.build();
	}

	@Bean
	public Step masterStep() {
		// masterにslave、handler、partitionerを設定
		return stepBuilderFactory
				.get("masterStep")
				.partitioner(slaveStep().getName(), demoPartitioner)
				.partitionHandler(handler())
				.build();
	}

	@Bean
	public PartitionHandler handler() {
		TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
		handler.setGridSize(3);
		handler.setTaskExecutor(taskExecutor());
		handler.setStep(slaveStep());
		try {
			handler.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return handler;
	}

	@Bean
	public SimpleAsyncTaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
	
	/**
     * Taskletの定義.
     *
     * @return FirstTasklet
     */
    @Bean
    public Tasklet firstTasklet() {
        return new FirstTasklet();
    }


    /**
     * 後続Taskletの定義
     *
     * @return NextTasklet
     */
    @Bean
    public Tasklet nextTasklet() {
        return new NextTasklet();
    }

    /**
     * ジョブステップの定義.
     *
     * @return firstStep
     */
    @Bean
    protected Step taskletFirstStep() {
        return stepBuilders.get("firstStep")
                .tasklet(firstTasklet()) // 上のTaskletをステップに登録
                .build();
    }

    @Bean
    protected  Step taskletNextStep() {
        return stepBuilders.get("nextStep")
                .tasklet(nextTasklet())
                .build();
    }

}

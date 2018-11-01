//package com.zac.batch.configuration;
//
//import java.sql.ResultSet;
//
//import javax.sql.DataSource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.ExitStatus;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.job.builder.FlowBuilder;
//import org.springframework.batch.core.job.flow.Flow;
//import org.springframework.batch.core.job.flow.support.SimpleFlow;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
//import org.springframework.batch.item.database.ItemPreparedStatementSetter;
//import org.springframework.batch.item.database.JdbcBatchItemWriter;
//import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import com.zac.batch.BatchConstants;
//import com.zac.batch.dto.PersonDto;
//import com.zac.batch.jpa.RecordProcessor;
//import com.zac.batch.jpa.RecordSO;
//import com.zac.batch.jpa.WriterSO;
//import com.zac.batch.launch.InitializerTasklet;
//import com.zac.batch.launch.SimpleStepDecider;
//import com.zac.batch.listener.JobStartEndListener;
//import com.zac.batch.listener.StepStartEndListener;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfigurationFlow {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfigurationFlow.class);
//
//	@Autowired
//	private JobStartEndListener jobStartEndListener;
//
//	@Autowired
//	private StepStartEndListener stepStartEndListener;
//
//	@Autowired
//	public JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	public StepBuilderFactory stepBuilderFactory;
//
//	@Autowired
//	private InitializerTasklet initializerTasklet;
//
//	@Autowired
//	private DataSource dataSource;
//
//	// tag::jobstep[]
//	// @Bean
//	// public Job importUserJob(JobCompletionNotificationListener listener, Step
//	// step1) {
//	// return jobBuilderFactory.get("importUserJob")
//	// .incrementer(new RunIdIncrementer())
//	// .listener(listener)
//	// .flow(step1)
//	// .end()
//	// .build();
//	// }
//
//	@Bean(name = "importJob")
//	public Job importJob(JobBuilderFactory jobBuilderFactory, @Qualifier("init") Step init,
//			@Qualifier("step1") Step step1, @Qualifier("step2") Step step2, @Qualifier("step3") Step step3,
//			@Qualifier("step4") Step step4) throws Exception {
//
//		Flow flow1 = new FlowBuilder<SimpleFlow>("flow1").start(step1).build();
//
//		Flow flow2 = new FlowBuilder<SimpleFlow>("flow2").start(step2).on(ExitStatus.COMPLETED.getExitCode()).to(step4)
////				.from(step4)
////				.on(ExitStatus.COMPLETED.getExitCode())
////				.to(step1)
//				.build();
//
//		return jobBuilderFactory.get("ach-exporter").incrementer(new RunIdIncrementer()).listener(jobStartEndListener)
//				.flow(init).next(decider()).on(BatchConstants.FLOW_NAME_2).to(flow2)
//				// .from(decider()).on(BatchConstants.FLOW_NAME_3).to(flow3)
//				.from(decider()).on("*").to(flow1).end().build();
//
//	}
//
//	@Bean
//	public SimpleStepDecider decider() {
//		SimpleStepDecider decider = new SimpleStepDecider();
//		return decider;
//	}
//
//	@Bean(name = "init")
//	public Step init() throws Exception {
//		return stepBuilderFactory.get("init").tasklet(initializerTasklet).build();
//	}
//
//	@Bean
//	public Step step1(JdbcBatchItemWriter<PersonDto> writer) {
//		return stepBuilderFactory.get("step1").listener(stepStartEndListener).<PersonDto, PersonDto>chunk(10)
//				.reader(csvSourceItemReader()).processor(processor()).writer(writer).build();
//	}
//
//	@Bean
//	public Step step2() {
//		System.out.println("[This is step2]");
//		Step step = stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
//			System.out.println("step2 process...");
//			return RepeatStatus.FINISHED;
//		}).build();
//		return step;
//	}
//
//	@Bean
//	public Step step3() {
//		System.out.println("[This is step3]");
//		Step step = stepBuilderFactory.get("step3").tasklet((contribution, chunkContext) -> {
//			System.out.println("step3 process...");
//			return RepeatStatus.FINISHED;
//		}).build();
//		return step;
//	}
//
//	@Bean
//	public Step step4() {
//		System.out.println("[This is step4]");
//		Step step = stepBuilderFactory.get("step4").tasklet((contribution, chunkContext) -> {
//			System.out.println("step4 process...");
//			return RepeatStatus.FINISHED;
//		}).build();
//		return step;
//	}
//
////	@Bean
////	@StepScope
////	public CsvSourceItemReader csvSourceItemReader(
////	        @Value("#{jobParameters['filename']}") String filename) {
////	    return new CsvSourceItemReader(filename);
////	}
//
//	@Bean
//	public CsvSourceItemReader csvSourceItemReader() {
//		return new CsvSourceItemReader("sample-data.csv");
//
//	}
//
//	@Bean
//	public FlatFileItemReader<PersonDto> reader() {
//		return new FlatFileItemReaderBuilder<PersonDto>().name("personItemReader")
//				.resource(new ClassPathResource("sample-data.csv")).delimited()
//				.names(new String[] { "firstName", "lastName" })
//				.fieldSetMapper(new BeanWrapperFieldSetMapper<PersonDto>() {
//					{
//						setTargetType(PersonDto.class);
//					}
//				}).build();
//	}
//
//	@Bean
//	public PersonItemProcessor processor() {
//		return new PersonItemProcessor();
//	}
//
//	// JPA hsqldb ==================================================
//	@Bean
//	public JdbcBatchItemWriter<PersonDto> writer(DataSource dataSource) {
//		return new JdbcBatchItemWriterBuilder<PersonDto>()
//				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//				.sql("INSERT INTO person (first_name, last_name) VALUES (:firstName, :lastName)").dataSource(dataSource)
//				.build();
//	}
//	// JPA hsqldb ==================================================
//
//	// JPA Mysql ==================================================
////	@Bean
////	public Job importUserJob5(JobCompletionNotificationListener listener5, Step step5) {
////		return jobBuilderFactory.get("importUserJob5")
////				.incrementer(new RunIdIncrementer())
////				.listener(listener5)
////				.flow(step5)
////				.end()
////				.build();
////	}
//
//	@Bean
//	public Step step5(JdbcBatchItemWriter<WriterSO> writer5, ItemReader<RecordSO> reader5) {
//		return stepBuilderFactory.get("step5").<RecordSO, WriterSO>chunk(5).reader(reader5).processor(processor5())
//				.writer(writer5).build();
//	}
//
//	@Bean
//	public ItemReader<RecordSO> reader5() {
//		return new JdbcCursorItemReaderBuilder<RecordSO>().name("the-reader")
//				.sql("select id, firstName, lastname, random_num from reader").dataSource(dataSource)
//				.rowMapper((ResultSet resultSet, int rowNum) -> {
//					if (!(resultSet.isAfterLast()) && !(resultSet.isBeforeFirst())) {
//						RecordSO recordSO = new RecordSO();
//						recordSO.setFirstName(resultSet.getString("firstName"));
//						recordSO.setLastName(resultSet.getString("lastname"));
//						recordSO.setId(resultSet.getInt("Id"));
//						recordSO.setRandomNum(resultSet.getString("random_num"));
//
//						LOGGER.info("RowMapper record : {}", recordSO);
//						return recordSO;
//					} else {
//						LOGGER.info("Returning null from rowMapper");
//						return null;
//					}
//				}).build();
//	}
//
//	@Bean
//	public ItemProcessor<RecordSO, WriterSO> processor5() {
//		return new RecordProcessor();
//	}
//
//	@Bean
//	public JdbcBatchItemWriter<WriterSO> writer5(DataSource dataSource, ItemPreparedStatementSetter<WriterSO> setter) {
//		return new JdbcBatchItemWriterBuilder<WriterSO>()
//				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//				.itemPreparedStatementSetter(setter)
//				.sql("insert into writer (id, full_name, random_num) values (?,?,?)").dataSource(dataSource).build();
//	}
//
//	@Bean
//	public ItemPreparedStatementSetter<WriterSO> setter() {
//		return (item, ps) -> {
//			ps.setLong(1, item.getId());
//			ps.setString(2, item.getFullName());
//			ps.setString(3, item.getRandomNum());
//		};
//	}
//
//}
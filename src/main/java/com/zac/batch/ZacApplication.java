package com.zac.batch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZacApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZacApplication.class);

	private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	@Value("${csv.input.path}")
	protected String csvInputPath;

	public static void main(String[] args) {
		LOGGER.info("Application main START==================================================");
		long startTime = System.currentTimeMillis();
		LOGGER.info("Start time = [" + DF.format(new Date()) + "]");
		LOGGER.info("Application main START==================================================");
		try {
			SpringApplication.exit(SpringApplication.run(ZacApplication.class, args));
		} catch (Exception ex) {
			LOGGER.error("critical error!!", ex);
			System.out.println("致命的なエラーが発生しました。詳細はログを確認して下さい。");
			System.exit(1);
		}
		System.out.println("Application main END==================================================");
		LOGGER.info("End time = [" + DF.format(new Date()) + "]");
		long endTime = System.currentTimeMillis();
		LOGGER.info("Program running time = [" + (endTime - startTime) + "]ms");
		System.out.println("Program running time = [" + (endTime - startTime) / 1000f + "]s");
		LOGGER.info("Program running time = [" + (endTime - startTime) / 1000f + "]s");
		System.out.println("Application main END==================================================");
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context) {
		return args -> {

			// コマンドライン引数を定義する
			Options options = new Options();
			options.addOption("j", "jobNmae", true, "Run job name.");
			options.addOption("i", "inputPath", true, "Input directory.");
			options.addOption("o", "outputPath", true, "Output directory.");

			// コマンドライン引数をパースする
			CommandLine cl = null;
			try {
				cl = new DefaultParser().parse(options, args);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// オプションが指定されているかどうかを確認する
			if (cl.hasOption("j")) {
				// オプション付きパラメタの受け取り
				String jobName = cl.getOptionValue("j");
				System.out.println(jobName);
			}

			if (cl.hasOption("i")) {
				// オプション付きパラメタの受け取り
				String intput = cl.getOptionValue("i");
				System.out.println(intput);
			}

			if (cl.hasOption("o")) {
				// オプション付きパラメタの受け取り
				String output = cl.getOptionValue("o");
				System.out.println(output);
			}

			if (cl.hasOption("z")) {
				// オプション付きパラメタの受け取り
				String output = cl.getOptionValue("z");
				System.out.println(output);
			}

			// オプション無しパラメタの受け取り
			// どちらもパラメタの内容自体は同じ
			String[] params = cl.getArgs();
			System.out.println(params[0]);
			List<String> paramList = cl.getArgList();
			System.out.println(paramList.size());

			// ==================================================
			if ((cl != null) && (cl.hasOption("j") && cl.hasOption("i"))) {
				// オプション付きパラメタの受け取り
				String jobName = cl.getOptionValue("j");
				System.out.println(jobName);
				String intputFileNmae = cl.getOptionValue("i");
				System.out.println(intputFileNmae);

				JobLauncher jobLauncher = (JobLauncher) context.getBean(JobLauncher.class);

				if (jobName.equals(BatchConstants.FIRST_JOB_ID)
						|| jobName.equals(BatchConstants.STRESS_TESTING_JOB_ID)) {
					System.out.println("Job name is ok.");
				} else {
					System.out.println("########## Parameter ERROR !!!!!");
					return;
				}

				Job runJobName = null;
				try {
					runJobName = (Job) context.getBean(jobName);
				} catch (BeansException ex) {
					LOGGER.error("A BeansException has occurred", ex);
				}
				JobParametersBuilder jpBuilder = new JobParametersBuilder();
				jpBuilder.addString("time", DF.format(new Date()));

				String intputFilePath = csvInputPath + intputFileNmae;
				jpBuilder.addString("filePath", intputFilePath);

				// TODO
				jpBuilder.addString("param1", "GO_TO_FLOW_2");

				JobParameters jobParameters = jpBuilder.toJobParameters();

				try {
					LOGGER.info("-- Run {} - Debut -----------------------------------------------");
					jobLauncher.run(runJobName, jobParameters);
					LOGGER.info("-- Run {} - Fin -------------------------------------------------");
				} catch (JobExecutionAlreadyRunningException ex) {
					LOGGER.error("A JobExecutionAlreadyRunningException has occurred", ex);
				} catch (JobRestartException ex) {
					LOGGER.error("A JobRestartException has occurred", ex);
				} catch (JobInstanceAlreadyCompleteException ex) {
					LOGGER.error("A JobInstanceAlreadyCompleteException has occurred", ex);
				} catch (JobParametersInvalidException ex) {
					LOGGER.error("A JobParametersInvalidException has occurred", ex);
				} catch (Exception ex) {
					LOGGER.error("An Exception has occurred", ex);
				}
			}
		};
	}

}

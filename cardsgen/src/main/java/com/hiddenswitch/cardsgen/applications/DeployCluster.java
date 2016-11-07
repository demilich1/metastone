package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeployCluster {
	private static final String JAR = "jar";
	private static final String MAIN_CLASS = "mainclass";
	private static final String CODE_BUCKET_NAME = "bucketname";
	private static final String JOB_ID = "jobid";
	private static final String APP_ARGS = "appargs";

	public static void main(String[] args) throws ParseException, SdkClientException {
		Logger logger = Logger.getLogger(DeployCluster.class);

		// Parse all the options
		Options options = new Options()
				.addOption(JAR, true, "The jar built with shadow:shadowJar containing the code to execute.")
				.addOption(MAIN_CLASS, true, "The main class to execute with Spark.")
				.addOption(CODE_BUCKET_NAME, true, "The bucket name to upload code to.")
				.addOption(JOB_ID, true, "An ID for the specific cluster job.")
				.addOption(APP_ARGS, true, "Application arguments to pass.");

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args, false);

		String jar = "cardsgen/build/libs/cardsgen-1.2.0-all.jar";
		String mainClass = "ControlApplication.class.getName()";
		String bucketName = "cardsgen-code";
		String jobId = RandomStringUtils.randomAlphanumeric(8);
		String appArgs = "";

		if (cmd.hasOption(JAR)) {
			jar = cmd.getOptionValue(JAR);
		}

		if (cmd.hasOption(MAIN_CLASS)) {
			mainClass = cmd.getOptionValue(MAIN_CLASS);
		}

		if (cmd.hasOption(CODE_BUCKET_NAME)) {
			bucketName = cmd.getOptionValue(CODE_BUCKET_NAME);
		}

		if (cmd.hasOption(JOB_ID)) {
			jobId = cmd.getOptionValue(JOB_ID);
		}

		if (cmd.hasOption(APP_ARGS)) {
			appArgs = cmd.getOptionValue(APP_ARGS);
		}


		AWSCredentials credentials = Common.getAwsCredentials();
		AmazonElasticMapReduce emr = new AmazonElasticMapReduceClient(credentials);
		AmazonS3Client s3 = new AmazonS3Client(credentials);

		// Upload jar to s3
		if (!s3.doesBucketExist(bucketName)) {
			s3.createBucket(bucketName);
		}

		String fileName = jobId + ".jar";
		String key = "deploy-script/job-jars/" + fileName;
		s3.putObject(bucketName, key, jar);
		String jarS3Path = "s3://" + bucketName + "/" + key;

		// Configure the spark application
		Application sparkApp = new Application()
				.withName("Spark");

		List<StepConfig> stepConfigs = new ArrayList<>();

		stepConfigs.add(new StepConfig()
				.withName("Install application binary")
				.withActionOnFailure(ActionOnFailure.TERMINATE_CLUSTER)
				.withHadoopJarStep(new HadoopJarStepConfig()
						.withJar("s3://elasticmapreduce/libs/script-runner/script-runner.jar")
						.withArgs(String.format("/home/hadoop/bin/hdfs dfs -get %s /mnt/", jarS3Path))));


		// Add the application arguments here

		stepConfigs.add(new StepConfig()
				.withName("Execute Spark")
				.withActionOnFailure(ActionOnFailure.TERMINATE_CLUSTER)
				.withHadoopJarStep(new HadoopJarStepConfig()
						.withJar("command-runner.jar")
						.withArgs(Arrays.asList(new String[]{
								"spark-submit", "--executor-memory", "1g", "--class", mainClass, "/mnt/" + fileName,
								appArgs}))));

		RunJobFlowRequest request = new RunJobFlowRequest()
				.withName(jobId)
				.withApplications(sparkApp)
				.withReleaseLabel("emr-5.1.0")
				.withSteps(stepConfigs)
				.withJobFlowRole("cluster")
				.withInstances(new JobFlowInstancesConfig()
						.withInstanceCount(3)
						.withMasterInstanceType("m3.xlarge")
						.withSlaveInstanceType("m3.xlarge")
						.withKeepJobFlowAliveWhenNoSteps(false)
				);

		try {
			RunJobFlowResult result = emr.runJobFlow(request);
			logger.info(String.format("JobFlowId: %s", result.getJobFlowId()));
		} catch (InternalServerErrorException e) {
			logger.error("Running the job flow threw an exception.", e);
		}
	}

}

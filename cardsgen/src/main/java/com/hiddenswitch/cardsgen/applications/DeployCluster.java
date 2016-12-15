package com.hiddenswitch.cardsgen.applications;

import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

import static com.hiddenswitch.cardsgen.applications.Common.defaultsTo;

public class DeployCluster {
	private static final String JAR = "jar";
	private static final String MAIN_CLASS = "mainclass";
	private static final String CODE_BUCKET_NAME = "bucketname";
	private static final String JOB_ID = "jobid";
	private static final String APP_ARGS = "appargs";
	private static final String SUBNET_ID = "subnetid";
	private static final String LOG_URI = "loguri";
	private static final String SERVICE_ROLE = "servicerole";
	private static final String JOB_FLOW_ROLE = "jobrole";
	private static final String BID_PRICE = "bidprice";
	private static final String INSTANCE_COUNT = "instancecount";
	private static final String KEY_PAIR = "keypair";
	private static final String INSTANCE_TYPE = "instancetype";

	public static void main(String[] args) throws ParseException, SdkClientException {
		Logger logger = Common.getLogger(DeployCluster.class);

		String appArgs = "";
		String jar = "cardsgen/build/libs/cardsgen-1.2.0-all.jar";
		String mainClass = ControlApplication.class.getName();
		String bucketName = "clustercode";
		String jobId = RandomStringUtils.randomAlphanumeric(8);
		String subnetId = "subnet-c2e425ab";
		// For us-east-2 only
		String logUri = "s3n://aws-logs-hiddenswitch-emr/elasticmapreduce/";
		String serviceRole = "cluster";
		String jobFlowRole = "clustermachines";
		String bidPrice = "0.05";
		String ec2KeyName = "clusterpair-us-east-2";
		int spotInstanceCount = 0;
		String instanceType = "c4.4xlarge";

		// Parse all the options
		Options options = new Options()
				.addOption(APP_ARGS, true, "Application arguments to pass.")
				.addOption(JAR, true, defaultsTo("The jar built with shadow:shadowJar containing the code to execute.", jar))
				.addOption(MAIN_CLASS, true, defaultsTo("The main class to execute with Spark.", mainClass))
				.addOption(CODE_BUCKET_NAME, true, defaultsTo("The bucket name to upload code to.", bucketName))
				.addOption(JOB_ID, true, defaultsTo("An ID for the specific cluster job.", jobId))
				.addOption(SUBNET_ID, true, defaultsTo("The ID of the subnet to create the EC2 instances inside of.", subnetId))
				.addOption(LOG_URI, true, defaultsTo("The URI (S3 typically) to store logs in.", logUri))
				.addOption(SERVICE_ROLE, true, defaultsTo("The name of the role to use to execute the EMR cluster creation commands.", serviceRole))
				.addOption(JOB_FLOW_ROLE, true, defaultsTo("The name of the role to use to execute the actual jobs on the job machines.", jobFlowRole))
				.addOption(BID_PRICE, true, defaultsTo("The bid price for c4.xlarge instances used for the tasks.", bidPrice))
				.addOption(INSTANCE_COUNT, true, defaultsTo("The number of c4.xlarge instances, in addition to the 1 reserved instance, to execute the job on.", Integer.toString(spotInstanceCount)))
				.addOption(KEY_PAIR, true, defaultsTo("The name of the keypair to use for the master node.", "clusterpair"))
				.addOption(INSTANCE_TYPE, true, defaultsTo("The type of the instance to use for spot pricing.", instanceType));

		CommandLineParser parser = new GnuParser();
		CommandLine cmd = parser.parse(options, args, false);

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

		if (cmd.hasOption(SUBNET_ID)) {
			subnetId = cmd.getOptionValue(SUBNET_ID);
		}

		if (cmd.hasOption(LOG_URI)) {
			logUri = cmd.getOptionValue(LOG_URI);
		}

		if (cmd.hasOption(SERVICE_ROLE)) {
			serviceRole = cmd.getOptionValue(SERVICE_ROLE);
		}

		if (cmd.hasOption(JOB_FLOW_ROLE)) {
			jobFlowRole = cmd.getOptionValue(JOB_FLOW_ROLE);
		}

		if (cmd.hasOption(BID_PRICE)) {
			bidPrice = cmd.getOptionValue(BID_PRICE);
		}

		if (cmd.hasOption(INSTANCE_COUNT)) {
			spotInstanceCount = Integer.parseInt(cmd.getOptionValue(INSTANCE_COUNT));
		}

		if (cmd.hasOption(KEY_PAIR)) {
			ec2KeyName = cmd.getOptionValue(KEY_PAIR);
		}

		if (cmd.hasOption(INSTANCE_TYPE)) {
			instanceType = cmd.getOptionValue(INSTANCE_TYPE);
		}

		// Strip appArgs of trailing punctuation
		appArgs = appArgs.replaceAll("^[\"\']", "").replaceAll("[\"\']$", "");
		List<String> appArgsList = Arrays.asList(appArgs.split(" "));

		AmazonElasticMapReduce emr = AmazonElasticMapReduceClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
		AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

		// Upload jar to s3
		if (!s3.doesBucketExist(bucketName)) {
			s3.createBucket(bucketName);
		}

		String fileName = jobId + ".jar";
		String key = "deploy-script/job-jars/" + fileName;

		try {
			s3.putObject(bucketName, key, new File(jar));
		} catch (Exception e) {
			logger.error("Could not upload the jar file to the s3 bucket", e);
			System.exit(1);
		}

		String jarS3Path = "s3://" + bucketName + "/" + key;

		// Configure the spark application
		Application sparkApp = new Application()
				.withName("Spark");

		List<StepConfig> stepConfigs = new ArrayList<>();

		StepConfig debugEnabled = new StepConfig()
				.withName("Enable debugging")
				.withActionOnFailure("TERMINATE_JOB_FLOW")
				.withHadoopJarStep(new HadoopJarStepConfig()
						.withJar("command-runner.jar")
						.withArgs("publicState-pusher-script"));
		stepConfigs.add(debugEnabled);

		// Add the application arguments here
		List<String> hadoopJarStepArgs = new ArrayList<>();
		for (String arg : new String[]{
				"spark-submit", "--master", "yarn", "--deploy-mode", "cluster", "--class", mainClass, jarS3Path}) {
			hadoopJarStepArgs.add(arg);
		}

		for (String arg : appArgsList) {
			hadoopJarStepArgs.add(arg);
		}

		stepConfigs.add(new StepConfig()
				.withName("Execute Spark")
				.withActionOnFailure(ActionOnFailure.TERMINATE_CLUSTER)
				.withHadoopJarStep(new HadoopJarStepConfig()
						.withJar("command-runner.jar")
						.withArgs(hadoopJarStepArgs)));

		Map<String, String> javaHome = new HashMap<>();
		javaHome.put("JAVA_HOME", "/usr/lib/jvm/java-1.8.0");

		ArrayList<InstanceGroupConfig> instanceGroupConfigs = new ArrayList<>(Arrays.asList(
				new InstanceGroupConfig()
						.withInstanceRole(InstanceRoleType.MASTER)
						.withInstanceCount(1)
						.withMarket(MarketType.ON_DEMAND)
						.withInstanceType("m4.large"),
				new InstanceGroupConfig()
						.withInstanceCount(1)
						.withInstanceRole(InstanceRoleType.CORE)
						.withMarket(MarketType.ON_DEMAND)
						.withInstanceType("m4.large")));

		if (spotInstanceCount > 0) {
			instanceGroupConfigs.add(new InstanceGroupConfig()
					.withInstanceCount(spotInstanceCount)
					.withInstanceRole(InstanceRoleType.TASK)
					.withMarket(MarketType.SPOT)
					.withInstanceType(instanceType)
					.withBidPrice(bidPrice));
		}

		RunJobFlowRequest request = new RunJobFlowRequest()
				.withName(jobId)
				.withLogUri(logUri)
				.withConfigurations(
						new Configuration()
								.withClassification("spark")
								.addPropertiesEntry("maximizeResourceAllocation", "true"),
						new Configuration()
								.withClassification("spark-log4j")
								.addPropertiesEntry("log4j.rootCategory", "ERROR, console"),
						new Configuration()
								.withClassification("hadoop-env")
								.withConfigurations(new Configuration()
										.withClassification("export")
										.withProperties(javaHome)),
						new Configuration()
								.withClassification("spark-env")
								.withConfigurations(new Configuration()
										.withClassification("export")
										.withProperties(javaHome))
				)
				.withServiceRole(serviceRole)
				.withApplications(sparkApp)
				.withReleaseLabel("emr-5.2.0")
				.withSteps(stepConfigs)
				.withJobFlowRole(jobFlowRole)
				.withInstances(new JobFlowInstancesConfig()
						.withEc2KeyName(ec2KeyName)
						.withKeepJobFlowAliveWhenNoSteps(false)
						.withEc2SubnetId(subnetId)
						.withInstanceGroups(instanceGroupConfigs)
				);

		try {
			RunJobFlowResult result = emr.runJobFlow(request);
			logger.info(String.format("JobFlowId: %s", result.getJobFlowId()));
		} catch (InternalServerErrorException e) {
			logger.error("Running the job flow threw an exception.", e);
		}
	}

}

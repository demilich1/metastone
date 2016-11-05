package com.hiddenswitch.proto3.net.channels;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.model.*;
import com.hiddenswitch.proto3.net.messages.NetworkMessage;
import com.hiddenswitch.proto3.net.util.Serialization;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class AWSCredentialedChannel extends Channel {
	private final int visibilityTimeout = 3;
	private AmazonSQSBufferedAsyncClient client;
	private String queueUrl;
	private AWSCredentials credentials;

	private PriorityBlockingQueue<NetworkMessage> queue = new PriorityBlockingQueue<>(8, new NetworkComparator());
	private final int sleepDelay = 1000;

	private Region getAWSRegion() {
		return Region.getRegion(Regions.US_EAST_1);
	}

	public AWSCredentialedChannel(String queue, AWSCredentials credentials) {
		super(queue);

		// TODO: Debug credentials
		if (credentials == null) {
			credentials = new ProfileCredentialsProvider("metastone_net").getCredentials();
		}

		setCredentials(credentials);
	}

	@Override
	public void run() {
		ReceiveMessageRequest request = new ReceiveMessageRequest(getChannelId()).withQueueUrl(getQueueUrl());
		request.setVisibilityTimeout(visibilityTimeout);
		int tries = 0;
		int maxTries = 90;
		while (tries < maxTries) {
			ReceiveMessageResult result = client.receiveMessage(request);
			if (result.getMessages().isEmpty()) {
				try {
					Thread.sleep(sleepDelay);
					tries++;
				} catch (InterruptedException e1) {
					return;
				}
			}

			ArrayList<DeleteMessageBatchRequestEntry> messagesToDelete = new ArrayList<>(result.getMessages().size());
			for (Message message : result.getMessages()) {
				NetworkMessage networkMessage = Serialization.deserialize(message.getBody(), NetworkMessage.class);
				queue.offer(networkMessage);
				messagesToDelete.add(new DeleteMessageBatchRequestEntry(message.getMessageId(), message.getReceiptHandle()));
			}

			client.deleteMessageBatch(queueUrl, messagesToDelete);

			tries = 0;
		}
	}

	@Override
	public boolean connect() {
		AWSCredentials credentials = null;
		try {
			credentials = getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Failed to load valid credentials to connect to Amazon SQS.");
		}

		client = new AmazonSQSBufferedAsyncClient(new AmazonSQSAsyncClient(credentials));
		client.setRegion(getAWSRegion());
		int tries = 0;
		int maxTries = 10;
		while (getQueueUrl() == null && tries < maxTries) {
			try {
				setQueueUrl(client.getQueueUrl(getChannelId()).getQueueUrl());
			} catch (QueueDoesNotExistException e) {
				try {
					Thread.sleep(sleepDelay);
				} catch (InterruptedException e1) {
					return false;
				}
			}
			tries++;
		}

		return (getQueueUrl() != null);
	}

	@Override
	public NetworkMessage receiveMessage() {
		return queue.poll();
	}

	@Override
	public void sendMessage(NetworkMessage message) {
		// TODO: This should actually use the HTTP API to post a message.
		client.sendMessageAsync(getQueueUrl(), Serialization.serialize(message));
	}

	@Override
	public int getMessageCount() {
		return queue.size();
	}

	private void setQueueUrl(String queueUrl) {
		this.queueUrl = queueUrl;
	}

	private String getQueueUrl() {
		return queueUrl;
	}

	public AWSCredentials getCredentials() {
		return credentials;
	}

	private void setCredentials(AWSCredentials credentials) {
		this.credentials = credentials;
	}
}

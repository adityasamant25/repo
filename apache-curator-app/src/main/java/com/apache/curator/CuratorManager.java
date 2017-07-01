package com.apache.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorManager {

	private static RetryPolicy retryPolicy;
	private static CuratorFramework client;
	
	public CuratorFramework startCuratorClient() {
		retryPolicy = new ExponentialBackoffRetry(1000, 3);
		client = CuratorFrameworkFactory.newClient("localhost", retryPolicy);
		client.start();
		return client;
	}
	
	public void stopCuratorClient() {
		client.close();
	}
	
}

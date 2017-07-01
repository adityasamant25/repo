package com.apache.curator;

import org.apache.curator.framework.CuratorFramework;

public class CuratorService {

	private static CuratorManager cm;
	private static CuratorFramework client;
	
	public static void createZnode(String path, byte[] data) throws Exception {
		client.create().forPath(path, data);
	}
	
	public static void main(String[] args) throws Exception {
		
		cm = new CuratorManager();
		client = cm.startCuratorClient();
		String path = "/sampleCuratorZNode1";
		byte[] data = "ZNode through Curator 1".getBytes();
		client.create().forPath(path, data);
		
		cm.startCuratorClient();
	}
}

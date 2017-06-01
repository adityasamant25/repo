package com.demo.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.couchbase.client.java.query.consistency.ScanConsistency;

public class CouchbaseProject {

	public static void main(String[] args) {
		Cluster cluster = CouchbaseCluster.create("couchbase://localhost");
		Bucket bucket = cluster.openBucket("example", "");

		JsonObject person = JsonObject.create();
		person.put("firstname", "Aditya");
		person.put("lastname", "Samant");
		JsonArray socialMedia = JsonArray.create();
		socialMedia
				.add(JsonObject.create().put("title", "Twitter").put("link", "https://www.twitter.com/adityasamant"));
		socialMedia.add(JsonObject.create().put("title", "GitHub").put("link", "https://github.com/adityasamant25"));
		person.put("socialMedia", socialMedia);
		JsonDocument document = JsonDocument.create("aditya", person);
		bucket.upsert(document);

		System.out.println(bucket.get("aditya").content());

		bucket.query(N1qlQuery.parameterized(
				"UPSERT INTO example (KEY, VALUE) VALUES ($id, {'firstname': $firstname, 'lastname': $lastname})",
				JsonObject.create().put("id", "isha").put("firstname", "Isha").put("lastname", "Samant")));

		N1qlQueryResult result = bucket.query(N1qlQuery.parameterized(
				"SELECT example.* FROM example where META().id = $id", JsonObject.create().put("id", "isha"),
				N1qlParams.build().consistency(ScanConsistency.REQUEST_PLUS)));

		for (N1qlQueryRow row : result) {
			System.out.println(row);
		}
	}
}

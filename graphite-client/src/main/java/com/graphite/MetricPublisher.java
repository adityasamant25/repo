package com.graphite;

public class MetricPublisher {

	public static void main(String[] args) throws Exception {
		GraphiteClient graphiteClient = new GraphiteClient("localhost", 2003);

		// send single value with current timestamp
		graphiteClient.sendMetric("universal.answer", 100);

		// send single value with custom timestamp
		//graphiteClient.sendMetric("universal.answer", 42, 1360848777l);

		// send multiple values
		/*Map<String, Integer> allAnswers = new HashMap<String, Integer>() {{
			put("where.is.my.towel", 42);
			put("where.are.the.dolphins", 42);
		}};
		graphiteClient.sendMetrics(allAnswers);*/
	}
}

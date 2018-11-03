package com.zac.batch.task;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class DemoPartitioner implements Partitioner {

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> map = new HashMap<String, ExecutionContext>();

		int range = 10;
		int from = 1;
		int to = range;

		for (int i = 1; i <= gridSize; i++) {
			ExecutionContext context = new ExecutionContext();

			context.putString("name", "thread" + i);
			context.putInt("from", from);
			context.putInt("to", to);

			map.put("partition" + i, context);

			from = to + 1;
			to += range;

		}
		return map;
	}
}

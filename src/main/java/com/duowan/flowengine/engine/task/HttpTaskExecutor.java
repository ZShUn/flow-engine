package com.duowan.flowengine.engine.task;

import java.util.Map;

import com.duowan.flowengine.engine.FlowEngine;
import com.duowan.flowengine.engine.TaskExecutor;
import com.duowan.flowengine.model.FlowTask;

public class HttpTaskExecutor implements TaskExecutor {

	@Override
	public void exec(FlowTask task, Map params, FlowEngine engine)
			throws Exception {
		String url = task.getProgram();
		
	}

}
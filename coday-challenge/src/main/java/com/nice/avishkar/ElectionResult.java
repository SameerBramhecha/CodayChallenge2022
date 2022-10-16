package com.nice.avishkar;

import java.util.Map;

public class ElectionResult {

	private Map<String, ConstituencyResult> resultData;

	public ElectionResult() {
		super();
	}

	public ElectionResult(Map<String, ConstituencyResult> resultData) {
		super();
		this.resultData = resultData;
	}

	public Map<String, ConstituencyResult> getResultData() {
		return resultData;
	}

	public void setResultData(Map<String, ConstituencyResult> resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ElectionResult [resultData=");
		builder.append(resultData);
		builder.append("]");
		return builder.toString();
	}

}

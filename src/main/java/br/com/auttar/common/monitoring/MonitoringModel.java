package br.com.auttar.common.monitoring;

public class MonitoringModel {
	private Long duration;
	private String timestamp;
	
	public MonitoringModel(Long duration, String timestamp) {
		this.duration = duration;
		this.timestamp = timestamp;
	}

	public Long getDuration() {
		return duration;
	}
	
	public String getTimestamp() {
		return timestamp;
	}	
}

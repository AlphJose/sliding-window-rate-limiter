package com.example.demo.model;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestData {
	private int id; 
	private long epochTimestamp;
	private AtomicInteger counter;

	public RequestData() {
		super();
		this.epochTimestamp = Instant.now().getEpochSecond();
	}

	public long getTimestamp() {
		return epochTimestamp;
	}

	public void setTimestamp(long timestamp) {
		this.epochTimestamp = timestamp;
	}

	public AtomicInteger getCounter() {
		return counter;
	}

	public void setCounter(AtomicInteger counter) {
		this.counter = counter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "RequestData [id=" + id + ", timestamp=" + epochTimestamp + ", counter=" + counter + "]";
	}

}

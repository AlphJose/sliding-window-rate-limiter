package com.example.demo.model;

import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestData {
	private int id;
	private Timestamp timestamp;
	private AtomicInteger counter;

	public RequestData() {
		super();
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	// store during interrupt
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
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
		return "RequestData [id=" + id + ", timestamp=" + timestamp + ", counter=" + counter + "]";
	}

}

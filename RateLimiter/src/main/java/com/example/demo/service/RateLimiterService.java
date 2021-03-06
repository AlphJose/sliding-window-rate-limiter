package com.example.demo.service;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.model.RequestData;
import com.example.demo.persistence.Dao;

@Scope(value = "session")
@Component(value = "rateLimiterService")
public class RateLimiterService {
	@Autowired
	private Dao<RequestData> requestDataDao;
	private static AtomicInteger counter = new AtomicInteger();
	public final int limit = 20;
	public final long timeWindowInSec = 60;

	public Collection<RequestData> getAllRequestData() {
		return requestDataDao.getAll();
	}

	@Async
	public CompletableFuture<Boolean> isAllowed(RequestData data) {
		int count = requestDataDao.getRequestCountInWindow(timeWindowInSec, data.getTimestamp());
		counter.set(count);
		if (counter.incrementAndGet() > limit) {
			return CompletableFuture.completedFuture(Boolean.FALSE);
		}
		requestDataDao.save(data);
		return CompletableFuture.completedFuture(Boolean.TRUE);
	}
}
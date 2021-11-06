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
    private RequestData requestData = new RequestData();
    private static AtomicInteger counter = new AtomicInteger();
    private final int limit = 10;
    private final long timeWindowInSec = 60;

    public void save() {
        requestDataDao.save(requestData);
        requestData = new RequestData();
    }

    public Collection<RequestData> getAllRequestData() {
        return requestDataDao.getAll();
    }

    public void saveRequestData(RequestData data) {
        validate(data);
//        return requestDataDao.save(data);
        requestDataDao.save(data);
    }

    private void validate(RequestData data) {
        // Details omitted
    }

    public RequestData getRequestData() {
        return requestData;
    }
    
    public boolean isAllowed(RequestData data) {
    	
    	int count = requestDataDao.getRequestCountInWindow(timeWindowInSec, data.getTimestamp());
    	counter.set(count);
    	if(counter.incrementAndGet() > limit) {
    		System.out.println("Counter = " + counter);
    		return false;
    	}
    	System.out.println("Counter = " + counter);
    	data.setCounter(counter);
    	requestDataDao.save(data);
    	return true;
    }
    
//    @Async
//    public CompletableFuture<Boolean>  isAllowed(RequestData data) {
//    	if(counter.incrementAndGet() > limit) {
//    		System.out.println("Counter = " + counter);
//    		return CompletableFuture.completedFuture(Boolean.FALSE);
//    	}
//    	System.out.println("Counter = " + counter);
//    	data.setCounter(counter);
//    	requestDataDao.save(data);
//    	return CompletableFuture.completedFuture(Boolean.TRUE);
//    }
	
}

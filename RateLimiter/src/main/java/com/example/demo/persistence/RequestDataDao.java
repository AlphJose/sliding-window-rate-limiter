package com.example.demo.persistence;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.RequestData;

@Component
public class RequestDataDao implements Dao<RequestData> {

	private List<RequestData> requestDataList = new CopyOnWriteArrayList<>();

	@Override
	public Collection<RequestData> getAll() {
		return requestDataList.stream().filter(Objects::nonNull)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}

	@Override
	public int save(RequestData data) {
		requestDataList.add(data);
		int index = requestDataList.size() - 1;
		data.setId(index);
		return index;
	}

	@Override
	public int getRequestCountInWindow(long window, Timestamp timestamp) {
		int count = 0;
		Timestamp windowStartTime = Timestamp.from(timestamp.toInstant().minusSeconds(window));
		evictOldRequests(windowStartTime);
		count = requestDataList.size();
//		if(count >= 10) {
//			System.out.println(count);
//			requestDataList
//			.forEach(data -> System.out.println(data 
//					+ " windowStartTime = " + windowStartTime 
//					+ " new timestamp = " + timestamp));
//		}
		

//		count = (int) requestDataList.stream().filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
//				.count();
//		if(count > 10) {
//			System.out.println(count);
//			requestDataList.stream()
//			.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
//			.forEach(data -> System.out.println(data + " windowStartTime = " + windowStartTime + " new timestamp = " + timestamp));
//		}

		return count;
	}

	/*
	 * Removes requests before the window's start time. Ignoring the case where
	 * requests are still processing as of now.
	 */
	private void evictOldRequests(Timestamp windowStartTime) {
		requestDataList.removeIf(data -> (data.getTimestamp().compareTo(windowStartTime) < 0));
	}

	/*
	 * In case of a server interrupt/unexpected shut down, the counter and requestDataList needs to be stored and retrieved upon server restart*/
	@Override
	public void saveToFile() {
		
	}
	
}

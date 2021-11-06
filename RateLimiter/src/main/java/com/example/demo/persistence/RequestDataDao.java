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
public class RequestDataDao implements Dao<RequestData>{
	
//	private List<RequestData> requestDataList = new ArrayList<>();
//	@Autowired
//	private List<RequestData> requestDataList = new ArrayList<>();
	private List<RequestData> requestDataList = new CopyOnWriteArrayList<>();


	@Override
	public Optional<RequestData> get(int id) {
		return Optional.ofNullable(requestDataList.get(id));
	}

	@Override
	public Collection<RequestData> getAll() {
		return requestDataList.stream().filter(Objects::nonNull)
				.collect(Collectors.collectingAndThen(Collectors.toList(), 
						Collections::unmodifiableList));
	}

	@Override
	public int save(RequestData data) {
		requestDataList.add(data);
		int index = requestDataList.size() - 1;
		data.setId(index);
		return index;
	}

	@Override
	public void update(RequestData data) {
		requestDataList.set(data.getId(), data);
	}

	@Override
	public void delete(RequestData data) {
		requestDataList.set(data.getId(), null);
	}

	@Override
	public int getRequestCountInWindow(long window, Timestamp timestamp) {
		int count = 0;
		Timestamp windowStartTime = Timestamp.from(timestamp.toInstant().minusSeconds(window));
//		Integer[] arr = requestDataList.stream()
//				.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
//				.toArray(Integer[] :: new);
//		System.out.println(arr);
//		count = arr.length;
//		synchronized(requestDataList) {
		count = (int) requestDataList.stream()
				.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0).count();
		System.out.println(count);
		requestDataList.stream()
				.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
				.forEach(data -> System.out.println(data + " windowStartTime = " + windowStartTime + " new timestamp = " + timestamp));
		
//		System.out.println(requestDataList.stream()
//				.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
//				.collect(Collectors.toList()));
//		}
//		count = (int) requestDataList.stream()
//				.filter(data -> data.getTimestamp().compareTo(windowStartTime) >= 0)
//				.collect(Collectors.toSet()).size();
		
		return count;
	}
}

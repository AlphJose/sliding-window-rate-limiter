package com.example.demo.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.model.RequestData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class RequestDataDao implements Dao<RequestData> {

	private List<RequestData> requestDataList = new CopyOnWriteArrayList<>();
	private static Path path = Paths.get("requestDataStore.txt");

	@Override
	public Collection<RequestData> getAll() {
		return requestDataList.stream().filter(Objects::nonNull)
				.collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
	}

	@Override
	public int save(RequestData data) {
		int index = requestDataList.size() - 1;
		if (index >= 0 && requestDataList.get(index).getTimestamp() == data.getTimestamp()) {
			requestDataList.get(index).getCounter().incrementAndGet();
		} else {
			data.setCounter(new AtomicInteger(1));
			requestDataList.add(data);
			index++;
			data.setId(index);
		}
		return index;
	}

	@Override
	public int getRequestCountInWindow(long window, long timestamp) {
		int count = 0;
		long windowStartTime = timestamp - window;
		evictOldRequests(windowStartTime);
		int temp = requestDataList.size();

		for (int i = 0; i < requestDataList.size(); i++) {
			count += requestDataList.get(i).getCounter().get();
		}
		return count;
	}

	/*
	 * Removes requests before the window's start time. Ignoring the case where
	 * requests are still processing as of now.
	 */
	private void evictOldRequests(long windowStartTime) {
		requestDataList.removeIf(data -> (data.getTimestamp() < windowStartTime));
	}

	/*
	 * In case of a server interrupt/unexpected shut down, the counter and
	 * requestDataList needs to be stored until server restart.
	 */
	@Override
	public void saveToFile() {
		String json = new Gson().toJson(requestDataList);
		try {
			Files.writeString(path, json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * In case of a server interrupt/unexpected shut down, the counter and
	 * requestDataList needs to be retrieved upon server restart.
	 */
	private void getListFromFile() {
		String json = null;
		try {
			json = Files.readString(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		requestDataList = new Gson().fromJson(json, new TypeToken<CopyOnWriteArrayList<RequestData>>() {
		}.getType());
	}

}

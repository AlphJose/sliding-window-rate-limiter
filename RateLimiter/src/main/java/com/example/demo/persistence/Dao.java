package com.example.demo.persistence;

import java.util.Collection;

public interface Dao<T> {
	Collection<T> getAll();

	int save(T t);

	int getRequestCountInWindow(long window, long time);
	
	void saveToFile();
}

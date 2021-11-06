package com.example.demo.persistence;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

	Collection<T> getAll();

	int save(T t);

	int getRequestCountInWindow(long window, Timestamp timestamp);
	
	void saveToFile();
}

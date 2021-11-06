package com.example.demo.persistence;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

//    Optional<T> get(int id);
    Collection<T> getAll();
    int save(T t);
//    void update(T t);
//    void delete(T t);
    int getRequestCountInWindow(long window, Timestamp timestamp);
}

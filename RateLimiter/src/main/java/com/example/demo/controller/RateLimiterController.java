package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RequestData;
import com.example.demo.service.RateLimiterService;

@Scope(value = "session")
@Component(value = "rateLimiterController")
@RestController
public class RateLimiterController {
	@Autowired
	RateLimiterService service;
	@GetMapping("/")
	public ResponseEntity<String> index() {
		RequestData data = new RequestData();
		data.setTimestamp(new Timestamp(System.currentTimeMillis()));
		try {
			if(service.isAllowed(data).get()) {
//			if(service.isAllowed(data)) {
				
//				System.out.println(service.getAllRequestData());
				return new ResponseEntity<>("Hello World!", HttpStatus.OK);
			}
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(service.getAllRequestData());
		return new ResponseEntity<>("Rate limit exceeded!", HttpStatus.TOO_MANY_REQUESTS);
	}
	
//	@GetMapping("/byThread")
//	public ResponseEntity<String> index2() {
////		CompletableFuture <Boolean> thread1 = service.isAllowed(new RequestData());
////		CompletableFuture <Boolean> thread2 = service.isAllowed(new RequestData());
////		CompletableFuture <Boolean> thread3 = service.isAllowed(new RequestData());
////		CompletableFuture.allOf(thread1,thread2,thread3).join();
////		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
//		
////		RequestData data = new RequestData();
////		data.setTimestamp(new Timestamp(System.currentTimeMillis()));
////		try {
////			if(service.isAllowed(data).get()) {
////				return new ResponseEntity<>("Hello World!", HttpStatus.OK);
////			}
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (ExecutionException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		return new ResponseEntity<>("Rate limit exceeded!", HttpStatus.TOO_MANY_REQUESTS);
//	}
}

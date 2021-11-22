package com.example.demo.controller;

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
		String test1 = "123";
		String test2 = "122.16.32.23";
		RequestData data = new RequestData();
		try {
			if (service.isAllowed(data).get()) {
//				System.out.println(service.getAllRequestData());
				return new ResponseEntity<>("Hello World!", HttpStatus.OK);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(service.getAllRequestData());
		return new ResponseEntity<>("Rate limit exceeded!", HttpStatus.TOO_MANY_REQUESTS);
	}
}

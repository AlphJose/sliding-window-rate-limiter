package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RateLimiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RateLimiterApplication.class, args);
	}

}



/**
 * Get "http://localhost:8080/": dial tcp 0.0.0.0:0->[::1]:8080: socket: too many open files
 * Get "http://localhost:8080/": dial tcp: lookup localhost: no such host
 * on first call
 * 
 * 
 * echo "GET http://localhost:8080/" | vegeta attack -duration=5s -rate=2000 | tee results.bin | vegeta report
	Requests      [total, rate, throughput]         10000, 2000.30, 6.00
	Duration      [total, attack, wait]             4.999s, 4.999s, 236.346µs
	Latencies     [min, mean, 50, 90, 95, 99, max]  192.778µs, 288.532µs, 250.913µs, 363.449µs, 427.954µs, 788.184µs, 6.676ms
	Bytes In      [total, mean]                     199760, 19.98
	Bytes Out     [total, mean]                     0, 0.00
	Success       [ratio]                           0.30%
	Status Codes  [code:count]                      200:30  429:9970  
	Error Set:
	429 
 *
 *
 * */

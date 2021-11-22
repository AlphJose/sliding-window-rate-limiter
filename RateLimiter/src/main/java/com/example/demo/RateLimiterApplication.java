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
 *TODO
 *1. convert to epoch - done
 *2. store in seconds - track counter for each second, - done
 *		justification - what if limit is a million per minute? it can be brought down to 60 entries
 *3. integration test and load testing 
 *4. figure out interrupts
 *5. savefile causing NPE.
 *6. readme
 *7. arraylist -> queue, can pop 1st request as long as outside window, you have info on incoming request's time as well
 *		keep a var aside to track counter
 *8. use case where user data is needed
 *9. use case to scale to like a million requests limit per month
 *10. go thru justification for completeable future
 *
 *
 *
 *
 *
 *
 *docker compose
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * */

package com.wahyusudrajat.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public ResponseEntity<String> helloWorld() {
		return new ResponseEntity<>("Hello World!", HttpStatus.OK);
	}
	
	@GetMapping("/hello/{name}")
	public ResponseEntity<String> helloUser(@PathVariable(value = "name") String name) {
		return new ResponseEntity<>("Hello " + name + "!", HttpStatus.OK);
	}
	
}

package rs.ac.uns.ftn.findaroommate.FindARoommateServer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/test/{id}")
	public String testId(@PathVariable("id") String idTest) {
		return "Hello Jersey: " + idTest;
	}

}

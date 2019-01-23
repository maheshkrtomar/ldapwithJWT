package com.mkt.corevalue.corevalueservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/corevalues")
public class CoreValueController {
	
	@GetMapping("/getCoreValues")
	public List<String> getCoreValues(){
		List<String> corevalues=Arrays.asList("grow Together");
		return corevalues;
		
	}

}

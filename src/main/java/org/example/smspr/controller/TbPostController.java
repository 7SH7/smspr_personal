package org.example.smspr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tbPost")
public class TbPostController {

	@GetMapping("/{page}")
	public String page(@PathVariable String page){
		return "tbPost/" + page;
	}

}

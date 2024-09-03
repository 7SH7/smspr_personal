package org.example.smspr.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tbUser")
public class TbUserController {

	@GetMapping("/{page}")
	public String page(@PathVariable String page){
		return "tbUser/" + page;
	}

	@GetMapping("/{page}/{id}")
	public String page(@PathVariable String page, @PathVariable String id){
		return "tbUser/" + page;
	}

}

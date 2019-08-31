package com.meisw.systemChek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class IndexController {

	@RequestMapping("/index")
	public String index(){
		return "setting/index";
	}
}

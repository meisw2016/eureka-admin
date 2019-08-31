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
	
	@RequestMapping("/cpu")
	public String cpu() {
		return "setting/cpu";
	}
	
	@RequestMapping("/mem")
	public String mem() {
		return "setting/mem";
	}
	
	@RequestMapping("/test")
	public String echarts() {
		return "setting/demo";
	}
}

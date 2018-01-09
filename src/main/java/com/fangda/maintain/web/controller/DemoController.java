package com.fangda.maintain.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController extends RestBaseController {
    private static final Logger LOGGER = LogManager.getLogger(DemoController.class);

    @RequestMapping("/test")
	String index(){
		return "hello";
	}
}

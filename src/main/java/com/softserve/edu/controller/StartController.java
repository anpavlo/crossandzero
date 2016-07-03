package com.softserve.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.softserve.edu.service.UserService;


@Controller
public class StartController {

	@Autowired
    UserService userService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String start() {
        System.out.println(userService.getUser());
        return "startPage";
	}
}

package tn.esprit.spring.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;


@Controller

public class ChatController {
	 @MessageMapping("/resume")
	    @SendTo("/start/initial")
	    public String chat(String msg) {
	        System.out.println(msg);
	        return msg;
	    }
	 }

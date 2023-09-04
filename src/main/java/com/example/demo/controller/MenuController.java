package com.example.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/switch")
public class MenuController {
private static final Logger LOG = Logger.getLogger(MenuController.class);

    @GetMapping("/")
    public String menu(){
        LOG.info("Inicio de a aplicacion Budget rent a car");
        return "vistaMenu";
    }
    
}

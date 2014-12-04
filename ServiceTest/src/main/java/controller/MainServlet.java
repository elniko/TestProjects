package controller;

import entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.ProcessService;

/**
 * Created by nike on 30/11/14.
 */

@RestController
public class MainServlet {

    @Autowired
    ProcessService ts;

    @RequestMapping("/")
    public String hello(){


        ProcessEntity process = new ProcessEntity();
        process.setState("started");
        process.setType("xct");

        ts.add(process);
        return "hello";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/start")
    public String startProcess() {
       return "HELLO";
    }


    @RequestMapping(value = "test/{name}", method = RequestMethod.GET)
    public String testRest(@PathVariable String name){
        String res = "Hello " + name;
        return res;
    }
}

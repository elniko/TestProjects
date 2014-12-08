package controller;

import entities.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import service.ProcessService;

import java.awt.*;

/**
 * Created by nike on 30/11/14.
 */

@RestController
public class MainServlet {

    @Autowired
    ProcessService ts;

    @RequestMapping("/")
    public String hello(){
        return "hello";
    }


    @RequestMapping(method = RequestMethod.GET, value = "ProcessAdd/{type}")
    public  ProcessEntity processAdd(@PathVariable String type) {
        ProcessEntity process = new ProcessEntity();
        process.setState("scheduled");
        process.setType(type);
        process = ts.edit(process);
        return process;
    }


    @RequestMapping(value = "test/{name}", method = RequestMethod.GET)
    public String testRest(@PathVariable String name){
        String res = "Hello " + name;
        return res;
    }


}

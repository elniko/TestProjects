package controller;

import entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import service.MessageGenerator;
import service.ProcessService;

import java.awt.*;

/**
 * Created by nike on 30/11/14.
 */

@RestController
public class MainServlet {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProcessService ts;

    @Autowired
    MessageGenerator generator;


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

    @RequestMapping(method = RequestMethod.GET, value = "ProcessStart/{id}")
    public String startProcess(@PathVariable int id) {
        try {
            generator.setId(id);
            generator.run();
        }
        catch(Exception ex) {
            log.error("Error", ex);
        }
        return "Ok";

    }

    @RequestMapping(method = RequestMethod.GET, value = "GetLog/{id}")
    public void getLog(@PathVariable int id) {

    }

    @RequestMapping(value = "test/{name}", method = RequestMethod.GET)
    public String testRest(@PathVariable String name){
        String res = "Hello " + name;
        return res;
    }


}

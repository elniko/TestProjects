package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by nike on 30/11/14.
 */

@Controller

public class MainServlet {

    @RequestMapping("/")
    public String hello(){
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

package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import service.TestService;

/**
 * Created by nike on 30/11/14.
 */

@RestController
public class MainServlet {

    @RequestMapping("/")
    public String hello(){

        AnnotationConfigWebApplicationContext context =new AnnotationConfigWebApplicationContext();
        context.refresh();
        TestService ps = context.getBean(TestService.class);
        return ps.sayHello();
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

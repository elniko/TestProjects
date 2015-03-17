package controller;

import entity.ProcessStatusEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nick on 05/03/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/process")
public class ProcessController {

    @RequestMapping("/start/{id}")
    public int startProcess(){
        return 0;
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public int scheduleProcess(@RequestParam String type) {
        return 0;
    }

    @RequestMapping(value = "/schedule/{type}", method = RequestMethod.GET)
    public int scheduleProcess2(@PathVariable String type) {
        return 0;
    }


    @RequestMapping(value = "/schedule", method=RequestMethod.POST)
    public int scheduleProcess(@RequestBody ProcessStatusEntity pe) {
        return 0;
    }
}

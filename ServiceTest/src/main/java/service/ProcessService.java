package service;

import entities.*;
import entities.Process;
import org.springframework.stereotype.*;

/**
 * Created by nike on 27/11/14.
 */
@org.springframework.stereotype.Service
public class ProcessService extends GenericService<entities.Process> implements  TestService{

    @Override
    public String sayHello() {
        return "Hello bean";
    }
}

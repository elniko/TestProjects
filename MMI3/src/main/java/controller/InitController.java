package controller;

import exceptions.EntityNotExistsException;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.interfaces.InitializerService;

import java.util.Optional;

/**
 * Created by Nick on 26/03/2015.
 */
@org.springframework.web.bind.annotation.RestController
public class InitController {
    @Autowired
    InitializerService initializerService;
    @RequestMapping(value = {"/init", "/init/{clear}"})
    public String init(@PathVariable Optional<String> clear) throws EntityNotExistsException, UserAlreadyExistException, RoleNotExistException {
        initializerService.initTypes(Boolean.valueOf(clear.orElse("true")));
        return "Initialized";
    }
}

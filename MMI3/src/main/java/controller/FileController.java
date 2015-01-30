package controller;

import dao.interfaces.ResourceDao;
import dao.interfaces.ResourceTypeDao;
import dao.interfaces.UserDao;
import entity.ResourceEntity;
import entity.ResourceTypeEntity;
import entity.UserEntity;
import exceptions.BadResourceTypeException;
import exceptions.UserNotExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.interfaces.ResourceService;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by stagiaire on 09/01/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    Logger logger;

    @Autowired
    ResourceService resourceService;

   // @Autowired
    SecurityContext securityContext;


    @RequestMapping(value ="/hello", method = RequestMethod.POST)
    public String sayHello() {
        return "Hello FileController";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file,
                             @RequestParam String type) throws IOException, BadResourceTypeException, UserNotExistsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        return resourceService.addResource(file.getBytes(), type, name) + "";
    }


    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file,
                             @RequestParam int typeId,
                             @RequestParam int userId) throws IOException, BadResourceTypeException, UserNotExistsException {

        return resourceService.addResource(file.getBytes(), typeId, userId) + "";
    }



    @ExceptionHandler
    public Exception exceptionHandler(Exception ex) {
        logger.error("Error ", ex);
        return ex;
    }

    @RequestMapping(value = {"/", "all"}, method = RequestMethod.GET)
    public List<ResourceEntity> getFiles(@MatrixVariable Map<String, String> params) {
        int start = 0;
        int count = 0;
        String order = "";

        if(params != null) {
            if (params.containsKey("order")) {
                order = params.get("order");
            }
            if (params.containsKey("start")) {
                start = Integer.parseInt(params.get("start"));
            }
            if (params.containsKey("count")) {
                count = Integer.parseInt(params.get("count"));
            }
        }

          List<ResourceEntity> resources = resourceService.getAllResources(start, count, order);

        return resources;
    }


}

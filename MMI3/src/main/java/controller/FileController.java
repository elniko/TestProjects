package controller;

import entity.ResourceEntity;
import exceptions.BadRequestParameters;
import exceptions.BadResourceTypeException;
import exceptions.UserNotExistsException;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import service.interfaces.ResourceService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 09/01/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    Logger logger;

    @Autowired
    ResourceService resourceService;


    @RequestMapping(value ="/hello", method = RequestMethod.POST)
    public String sayHello() {
        return "Hello FileController";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file,
                             @RequestParam String type) throws IOException, BadResourceTypeException, UserNotExistsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return resourceService.addResource(file.getBytes(), ext, type, name) + "";
    }


    @RequestMapping(value = "/upload2", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file,
                             @RequestParam int typeId) throws IOException, BadResourceTypeException, UserNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        String ext = FilenameUtils.getExtension(file.getName());
        return resourceService.addResource(file.getBytes(), ext, typeId, name) + "";
    }


    @Secured(value = "ROLE_ADMIN")
    @RequestMapping("/ex")
    public String testException() throws BadRequestParameters {
        throw new BadRequestParameters();

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

package controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
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
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import service.interfaces.ResourceService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@RequestMapping("/ex")
public String getException(HttpServletRequest req) throws NoSuchRequestHandlingMethodException {
    throw new NoSuchRequestHandlingMethodException(req);
}

    @RequestMapping(value = {"/", "all"}, method = RequestMethod.GET)
    public List<ResourceEntity> getFiles(@MatrixVariable Optional<String> st) throws UserNotExistsException {
        int start = 0;
        int count = 0;
        String order = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        st.ifPresent(value -> logger.info(value));
//        if(params != null) {
//            if (params.containsKey("order")) {
//                order = params.get("order");
//            }
//            if (params.containsKey("start")) {
//                start = Integer.parseInt(params.get("start"));
//            }
//            if (params.containsKey("count")) {
//                count = Integer.parseInt(params.get("count"));
//            }
//        }

          List<ResourceEntity> resources = resourceService.getAllResources(name, start, count, order);

        return resources;
    }




}

package controller;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import entity.ResourceEntity;
import exceptions.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Integer> uploadFile(@RequestParam MultipartFile file) throws IOException, UserNotExistsException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        int res = resourceService.addResource(file.getBytes(), filename, ext, name);
        return  new ResponseEntity<>(res, HttpStatus.OK);
    }



    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<ResourceEntity> getFiles(@RequestParam(value = "start") Optional <Integer> st,
                                         @RequestParam(value = "count") Optional <Integer> co,
                                         @RequestParam(value = "order") Optional<String> or) throws UserNotExistsException {

      Integer start =  st.orElse(0);
      Integer count = co.orElse(0);
      String order = or.orElse("");

      if(!order.equals("")) {
         order = order.replace("_", " ");
      }

      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      String name = auth.getName();
      List<ResourceEntity> resources = resourceService.getAllResources(name, start, count, order);

      return resources;
    }

    @RequestMapping(value = "/remove/{ids}", method = RequestMethod.GET)
    public String removeResources(@PathVariable int[] ids) throws EntityNotExistsException, UserNotExistsException, BadOwnerException {
        int count = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        if(auth.getAuthorities().stream().anyMatch((authtority) -> authtority.getAuthority().equals("ROLE_ADMIN"))) {
            for ( int id : ids) {
                if (resourceService.removeResource(id)) {
                    count++;
                }
            }
        } else {
            for (int id : ids) {
                if (resourceService.removeResource(id, name)) {
                    count++;
                }
            }
        }

        return String.format("Deleted: %s items", count);
    }





}

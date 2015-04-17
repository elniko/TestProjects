package controller;

import entity.ProfileEntity;
import entity.UserEntity;
import exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import service.interfaces.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nick on 18/02/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    UserService userService;


    @RequestMapping("/hello")
    public String helloGavno(){
        return "Gavno";
    }


    @RequestMapping(value = "/add")
    public int addUser(@RequestParam String role,
                                            @RequestParam String name,
                                            @RequestParam String email,
                                            @RequestParam String password
                                           ) throws RoleNotExistException, UserAlreadyExistException {

        if (role.equals("ROLE_SUPERADMIN")) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
        if (role.equals("ROLE_ADMIN")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!auth.getAuthorities().stream().anyMatch((authtority) -> authtority.getAuthority().equals("ROLE_SUPERADMIN"))) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
        }
        int result = userService.addUserWithRole(name, password, email, role);
      // int result = 0;
        //return new ResponseEntity<>(result, HttpStatus.OK);
        return result;
    }

    @RequestMapping(value="/add", consumes = "application/json")
    public ResponseEntity<User> addUser2(@RequestBody @Valid User user) {

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/all")
    public ResponseEntity<List<UserEntity>> getAll(@RequestParam(value = "start") Optional<Integer> st,
                                                   @RequestParam(value = "count") Optional <Integer> co,
                                                   @RequestParam(value = "order") Optional<String> or) {
        List<UserEntity> resources = getUsers(st, co, or, "");
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/allUsers")
    public  ResponseEntity<List<UserEntity>> getAllUsers(@RequestParam(value = "start") Optional<Integer> st,
                                                         @RequestParam(value = "count") Optional <Integer> co,
                                                         @RequestParam(value = "order") Optional<String> or) {
        List<UserEntity> resources = getUsers(st, co, or, "USER");
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/allGuests")
    public  ResponseEntity<List<UserEntity>> getAllGuests(@RequestParam(value = "start") Optional<Integer> st,
                                                         @RequestParam(value = "count") Optional <Integer> co,
                                                         @RequestParam(value = "order") Optional<String> or) {
        List<UserEntity> resources = getUsers(st, co, or, "GUEST");
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/allAdmins")
    public  ResponseEntity<List<UserEntity>> getAllAdmins(@RequestParam(value = "start") Optional<Integer> st,
                                                         @RequestParam(value = "count") Optional <Integer> co,
                                                         @RequestParam(value = "order") Optional<String> or) {
        List<UserEntity> resources = getUsers(st, co, or, "ADMIN");
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(value = "/allByRole/{role}")
    public  ResponseEntity<List<UserEntity>> getAllByRole(@PathVariable String role,
                                                          @RequestParam(value = "start") Optional<Integer> st,
                                                          @RequestParam(value = "count") Optional <Integer> co,
                                                          @RequestParam(value = "order") Optional<String> or) {
        List<UserEntity> resources = getUsers(st, co, or, role.toUpperCase());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    private List<UserEntity> getUsers(Optional<Integer> st, Optional<Integer> co, Optional<String> or, String role) {
        Integer start =  st.orElse(0);
        Integer count = co.orElse(0);
        String order = or.orElse("");
        if(!order.equals("")) {
            order = order.replace("_", " ");
        }
        return userService.getAllUsers(start, count, order, role);
    }

    @RequestMapping(value="/remove/{ids}", method = RequestMethod.GET)
    public String removeUser(@PathVariable int[] ids) {
        int count = 0;
        int errors = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role =  auth.getAuthorities().toArray()[0].toString();

        switch (role) {
            case "ROLE_USER":
            case "ROLE_GUEST":
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            case "ROLE_SUPERADMIN":
                for(int id : ids) {
                    try {
                        if (userService.removeUsers(id))
                            count++;
                        else
                            errors++;
                    } catch (EntityNotExistsException e) {
                        errors++;
                    }
                }
                break;
            case "ROLE_ADMIN" :
                for(int id : ids) {
                    try {
                        if (userService.removeNotAdmins(id))
                            count++;
                        else
                            errors++;
                    } catch (EntityNotExistsException e) {
                        errors++;
                    }
                }
                break;
        }

        return String.format("Deleted: %s items, failed: %s items", count, errors);
    }


    @RequestMapping(value = "/profile/add", method = RequestMethod.POST)
    public int addProfile(@RequestParam String params) throws UserNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.addProfile(userName, params);
    }

    @RequestMapping(value="/profile/{id}")
    public ResponseEntity<ProfileEntity> getUserProfile(@PathVariable int id) throws BadProfileException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        ProfileEntity entity = userService.getProfile(userName, id);
        entity.setUser(null);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    @RequestMapping(value="/profile/edit/{id}", method = RequestMethod.POST)
    public String editProfile(@PathVariable int id, @RequestParam String params) throws BadProfileException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.editUserProfile(userName, id, params);
        return "Ok";
    }

    @RequestMapping(value="/profile/edit", method = RequestMethod.POST)
    public void editProfile2(@RequestBody ProfileEntity profile) throws BadProfileException, UserNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.editUserProfile(userName, profile);
    }


    @RequestMapping(value= "/profileById/{id}")
    public ProfileEntity getProfile(@PathVariable int id) {
        ProfileEntity entity = userService.getProfile(id);
        entity.setUser(null);
        return entity;
    }

    @RequestMapping(value="/profile/remove/{ids}", method = RequestMethod.GET)
    public String removeProfile(@PathVariable int[] ids) {
        int count = 0;
        int errors = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toArray()[0].toString();

        switch (role) {
            case "ROLE_ADMIN":
            case "ROLE_SUPERADMIN":
                for(int id: ids) {
                    try {
                        if(userService.removeProfile(id)){
                            count++;
                        } else {
                            errors++;
                        }
                    } catch (EntityNotExistsException e) {
                        errors++;
                    }
                }
                break;
            default:
                String name = auth.getName();
                for(int id : ids) {
                    try {
                        if(userService.removeProfile(name, id)) {
                            count++;
                        } else {
                            errors++;
                        }
                    } catch (BadProfileException e) {
                        errors++;
                    } catch (EntityNotExistsException e) {
                        errors++;
                    }
                }
                break;
        }
        return String.format("Deleted: %s items, failed: %s items", count, errors);
    }

    @RequestMapping(value="/profile/all")
    public List<ProfileEntity> getProfiles(@RequestParam(required = false) String userName) throws UserNotExistsException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role =  auth.getAuthorities().toArray()[0].toString();
        if((role.equals("ROLE_ADMIN") || role.equals("ROLE_SUPERADMIN")) && userName != null) {
            List<ProfileEntity> profiles = userService.getProfiles(userName);
            profiles.stream().forEach(value-> value.setUser(null));
            return profiles;
        }
        String name = auth.getName();
        List<ProfileEntity> profiles = userService.getProfiles(name);
        profiles.stream().forEach(value-> value.setUser(null));
        return profiles;
    }

    @RequestMapping(value = "profile/active/{id}", method = RequestMethod.GET)
    public void setActive(@PathVariable int id) throws UserNotExistsException, BadProfileException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.setActiveUserProfile(userName, id);
    }







}

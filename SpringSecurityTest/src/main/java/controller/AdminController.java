package controller;

import entity.User;
import entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.*;

/**
 * Created by stagiaire on 05/01/2015.
 */
@Controller
@RequestMapping(value = "/admin**")
public class AdminController
{

    @Autowired
    IUserService userService;

    @RequestMapping("/")
    public String getHomeAdmin() {
        return "admin/index";
    }

    @RequestMapping("users")
    public String getUserList(Model model) {
        List<User> users = userService.loadUsers();
        model.addAttribute("users", users);
        return "admin/user/users";
    }

    @RequestMapping("roles")
    public String getRoleList() {

        return "admin/roles";
    }


    @RequestMapping(value = "user", method = RequestMethod.GET, params = "new")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        Map<String, String> roles = new HashMap<>();
        roles.put("ROLE_ADMIN", "Admin");
        roles.put("ROLE_USER", "User");
        roles.put("ROLE_GUEST", "Guest");
        model.addAttribute("roles", roles);
        return "admin/user/edit";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public    String saveUserFromForm(@Valid User user, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/user";
        }
        userService.addUser(user);
        return "redirect:users";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Set.class, "role", new CustomCollectionEditor(Set.class) {
            protected Object convertElement(Object element) {
                if (element != null) {
                    String ur = (String)element;
                    UserRole role = new UserRole();
                    role.setRole(ur);
                    return role;
                }
                return null;
            }
        });
    }



}

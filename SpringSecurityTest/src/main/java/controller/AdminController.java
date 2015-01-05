package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by stagiaire on 05/01/2015.
 */
@Controller
@RequestMapping(value = "/admin**")
public class AdminController
{

    @RequestMapping("/")
    public String getHomeAdmin() {
        return "admin/index";
    }


}

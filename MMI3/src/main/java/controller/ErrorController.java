package controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nick on 09/02/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/errors")
public class ErrorController {

    @RequestMapping("/401")
    public void handleError401() {
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping("/404")
    public void handleError404() {
        throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/400")
    public void handleError400() {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/405")
    public void handleError405() {
        throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
    }



    @RequestMapping("/403")
    public void handleError403(HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        req.setAttribute("url", url);
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
    }
}

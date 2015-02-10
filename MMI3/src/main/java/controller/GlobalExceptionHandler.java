package controller;

import exceptions.ExceptionJSONInfo;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nick on 02/02/2015.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    Logger logger;

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ExceptionJSONInfo exHandler(HttpServletRequest request, Exception ex) {

        String url = (String) request.getAttribute("url");



        ExceptionJSONInfo exJson = new ExceptionJSONInfo();
        if(url == "")
         exJson.setUrl(request.getRequestURL().toString());
        else
            exJson.setUrl(url);
        exJson.setMessage(ex.getMessage());
        exJson.setException(ex.getClass().getSimpleName());
        logger.error("Exception: ", ex);
        return exJson;
    }

   // @ExceptionHandler(Throwable.class)
    @ResponseBody
    public void handleException(final Exception e, final HttpServletResponse response )
    {
        try {
            response.getWriter().write(String.format(
                    "{\"error\":{\"java.class\":\"%s\", \"message\":\"%s\"}}",
                    e.getClass(), e.getMessage()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}

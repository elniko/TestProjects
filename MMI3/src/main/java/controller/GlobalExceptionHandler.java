package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ExceptionJSONInfo;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import tools.annotations.LoggerConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Nick on 02/02/2015.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

   // @Autowired
    @LoggerConfig
    Logger logger;


    @Override
    protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionJSONInfo exJson = new ExceptionJSONInfo();
        //exJson.setUrl(request.get);
        exJson.setMessage(ex.getMessage());
        exJson.setException(ex.getClass().getSimpleName());
        logger.error("Exception: ", ex);
        ResponseEntity re = new ResponseEntity(String.format(
                "{\"error\":{\"java.class\":\"%s\", \"message\":\"%s\"}}", ex.getClass(), ex.getMessage()), headers, HttpStatus.OK);
        return re;
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionJSONInfo exJson = new ExceptionJSONInfo();
        //exJson.setUrl(request.get);
        exJson.setMessage(ex.getMessage());
        exJson.setException(ex.getClass().getSimpleName());
        logger.error("Exception: ", ex);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(exJson);
        } catch (JsonProcessingException e) {

            return new ResponseEntity(e.getMessage(), headers, HttpStatus.OK);
        }
        ResponseEntity re = new ResponseEntity(jsonString, headers, HttpStatus.OK);

        return re;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ExceptionJSONInfo exHandler(HttpServletRequest request, Exception ex) {
        ExceptionJSONInfo exJson = new ExceptionJSONInfo();
        exJson.setUrl(request.getRequestURL().toString());
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

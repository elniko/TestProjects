package service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import exceptions.ExceptionJSONInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Nick on 12/02/2015.
 */
@Component
public class ExceptionSerializer extends JsonSerializer<ExceptionJSONInfo> {
    @Override
    public void serialize(ExceptionJSONInfo exceptionJSONInfo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

    }
}

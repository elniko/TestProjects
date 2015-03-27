package client;

import entities.ProcessEntity;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by stagiaire on 08/12/2014.
 */
public class Main {
    public static void main(String[] args) throws URISyntaxException {


        RestTemplate template = new RestTemplate();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        String authString = "Test" + ":" + "test";
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
      // headers.add("Authorization", "Basic " + authStringEnc);
       // parts.add("hello", "1");
        parts.add("file", new FileSystemResource("d:/test.xml"));
        parts.add("type", "TAXO");
        //parts.add("userId", 3);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, headers);
        //System.out.println(entity);

       //HttpEntity res = template.exchange("http://127.0.0.1:8080/file/remove/5",HttpMethod.GET, entity, String.class);


        //String res =template.getForObject("http://127.0.0.1:8080/mmi/file/hello", String.class);

       // System.out.println(res);
        //String result = template.getForObject("http://127.0.0.1:8080/mmi/file/all", String.class);
        //System.out.println(result);
        //parts.add("file", new FileSystemResource("d:/config.ini"));
        String res = template.postForObject("http://127.0.0.1:8080/file/upload", parts, String.class);


    }
}

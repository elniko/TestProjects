package integration;

import entity.ResourceEntity;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Nick on 18/02/2015.
 */
public class TestResourceController {

    final static String PASSWORD = "test";
    final static String USER = "Test";
    final String URL = "http://127.0.0.1:8080/";
    final String TEST_FILE = "d:/test.xml";

    static RestTemplate restTemplate;
    static HttpHeaders headers;
    static int id1 = 0;
    static int id2 = 0;

    @BeforeClass
    public static void initialize() {
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        String authString = USER + ":" + PASSWORD;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        headers.add("Authorization", "Basic " + authStringEnc);
    }

    @Test
    public void testAuth() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "file/all", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("UNAUTHORIZED"));
    }

    @Test
    public void testAdd() {
        //System.out.println("Test Add");
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        parts.add("file", new FileSystemResource(TEST_FILE));
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(parts, headers);

        HttpEntity res1 = restTemplate.exchange(URL + "file/upload", HttpMethod.POST, entity, String.class);
        Assert.assertFalse("Exception while adding", res1.getBody().toString().contains("exception"));

        HttpEntity res2 = restTemplate.exchange(URL + "file/upload", HttpMethod.POST, entity, String.class);
        Assert.assertFalse("Exception while adding", res2.getBody().toString().contains("exception"));

        try {
            id1 = Integer.valueOf(res1.getBody().toString());
            id2 = Integer.valueOf(res2.getBody().toString());
        } catch (NumberFormatException ex) {
            Assert.fail("Not valid ID");
        }
    }


    @Test
    public void testGetAll() {
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "file/all", HttpMethod.GET, entity, ResourceEntity[].class);
        ResourceEntity[] r = (ResourceEntity[]) res.getBody();
        List<ResourceEntity> list = Arrays.asList(r);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id1));
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id2));
    }

    @Test
    public void testRemove() {
        HttpEntity res = restTemplate.exchange(URL + "file/remove/" + id1 + "," + id2, HttpMethod.GET, new HttpEntity<>(null, headers) , String.class);
        Assert.assertTrue(res.getBody().toString().contains("Deleted: 2"));
    }


}

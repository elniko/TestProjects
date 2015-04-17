package integration;

import entity.ResourceEntity;
import entity.UserEntity;
import org.apache.commons.codec.binary.Base64;

import org.junit.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nick on 18/02/2015.
 */
public class TestAdminController {

    final static String USER = "superadmin";
    final static String PASSWORD = "superadmin";
    final static String URL = "http://127.0.0.1:8080/";

    static RestTemplate restTemplate;
    static HttpHeaders headers;
    static int id1 = 0;
    static int id2 = 0;
    static int id3 = 0;

    @BeforeClass
    public static void initialize() throws Exception {

//        String webappDirLocation = "MMI3/src/main/webapp/";
//        Server server = new Server(8080);
//        WebAppContext root = new WebAppContext();
//        root.setContextPath("/");
//
//        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
//        root.setResourceBase(webappDirLocation);
//        root.setParentLoaderPriority(true);
//        server.setHandler(root);
//        server.start();



        restTemplate = new RestTemplate();
        headers = new HttpHeaders();

        String authString = USER + ":" + PASSWORD;
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        headers.add("Authorization", "Basic " + authStringEnc);

        //destroy();
    }

    @Test
    public void testAuth() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/all", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("UNAUTHORIZED"));
    }

    @Test
    public void testAddAdmin(){
        String authString = "user1" + ":" + "password";
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authStringEnc);
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/add?role=ROLE_ADMIN&name=useradmin&password=password&email=mail@mail.com", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("exception"));
        res = restTemplate.exchange(URL + "user/add?role=ROLE_SUPERADMIN&name=useradmin&password=password&email=mail@mail.com", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("exception"));
    }


    @Test
    public void testAdd() {
        HttpEntity entity = new HttpEntity(null, headers);

        HttpEntity res1 = restTemplate.exchange(URL + "user/add?role=ROLE_USER&name=user1&password=password&email=mail@mail.com", HttpMethod.POST, entity, String.class);
        Assert.assertFalse("Exception while adding",res1.getBody().toString().contains("exception"));

        HttpEntity res2 = restTemplate.exchange(URL + "user/add?role=ROLE_USER&name=user2&password=password&email=mail@mail.com", HttpMethod.GET, entity, String.class);
        Assert.assertFalse("Exception while adding", res2.getBody().toString().contains("exception"));

        try {
            id1 = Integer.valueOf(res1.getBody().toString());
            id2 = Integer.valueOf(res2.getBody().toString());
        } catch (Exception e) {
            Assert.fail("Not valid ID");
        }
    }

    @Test
    public void testGetAllUsers() {
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/allUsers", HttpMethod.GET, entity, UserEntity[].class);
        UserEntity[] r = (UserEntity[]) res.getBody();
        List<UserEntity> list = Arrays.asList(r);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id1));
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id2));
    }
    @Test
    public void testGetAll() {
        String authString = "user1" + ":" + "password";
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authStringEnc);
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/all", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("exception"));
    }

    @Test
    public void testAllByRole() {
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/allByRole/user", HttpMethod.GET, entity, UserEntity[].class);
        UserEntity[] r = (UserEntity[]) res.getBody();
        List<UserEntity> list = Arrays.asList(r);
        Assert.assertTrue(list.size() > 0);
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id1));
        Assert.assertTrue(list.stream().anyMatch(item -> item.getId() == id2));
    }

    @Test
    public void testAllByRoleAuth() {
        String authString = "user1" + ":" + "password";
        String authStringEnc = new String(Base64.encodeBase64(authString.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + authStringEnc);
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/allByRole/ADMIN", HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("exception"));
    }


    @Test
    public void tesRemove() {
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/remove/" + id1 +"," + id2, HttpMethod.GET, entity, String.class);
        Assert.assertTrue(res.getBody().toString().contains("Deleted: 2"));
    }


    //@AfterClass
    public static void destroy() {
        HttpEntity entity = new HttpEntity(null, headers);
        HttpEntity res = restTemplate.exchange(URL + "user/allUsers", HttpMethod.GET, entity, UserEntity[].class);
        UserEntity[] r = (UserEntity[]) res.getBody();
        if (r.length == 0)
            return;

        String ids = r[0].getId() + "";
        for(int i= 1; i < r.length; i++ ) {
            ids += "," + r[i].getId();
        }

        res = restTemplate.exchange(URL + "user/remove/" + ids, HttpMethod.GET, entity, String.class);
        System.out.println(res.getBody().toString());
    }
}

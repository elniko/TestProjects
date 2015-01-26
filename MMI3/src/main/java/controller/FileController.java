package controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by stagiaire on 09/01/2015.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestParam MultipartFile file, HttpRequest rerq) {

        try {
            File f = new File("/resources/saved.xml");
            FileUtils.writeByteArrayToFile(f, file.getBytes());
            return f.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }

    }

    public String getFiles() {
return "";
    }


}

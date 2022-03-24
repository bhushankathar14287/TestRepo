package com.saama;

import com.saama.service.WordPocService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WordPocServiceTest {

    @Autowired
    WordPocService wordPocService;

    @Test
    public void readTextTest() throws IOException {
        String resourceName = "mydoc.docx";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        InputStream ip = new FileInputStream(file);

        MultipartFile mockMultipartFile = new MockMultipartFile("inputFile",ip);
        System.out.println("file size : "+mockMultipartFile.getSize());
        String text = wordPocService.readText(mockMultipartFile);
        System.out.println(text);
        //assertEquals("abc",text);
        assertNotNull(text);

    }



}

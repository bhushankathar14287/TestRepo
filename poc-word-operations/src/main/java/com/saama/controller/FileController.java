package com.saama.controller;

import com.saama.service.WordPocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
/**
 * @author bhushan.kathar
 */
@RestController
public class FileController {

    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    WordPocService wordPocService;

    @GetMapping("/getWordFile")
    public ResponseEntity<InputStreamResource> getFileDemo() {
        logger.info("FileController :: getFileDemo :: start");
        ByteArrayInputStream byteArrayInputStream = wordPocService.writeFile();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=mydoc.docx");
        logger.info("FileController :: getFileDemo :: end");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(byteArrayInputStream));
    }

    @PostMapping("/readWordFile")
    public ResponseEntity<String> readWordFile(@RequestParam("inputFile") MultipartFile file) {
        logger.info("FileController :: readWordFile :: start");
        String resp = wordPocService.readText(file);
        // String resp = wordPocService.readByParagraph(file);
        //String resp = wordPocService.readByTable(file);
        //String resp = wordPocService.readByHeader(file);
        //String resp = wordPocService.readByFooter(file);
        logger.info("FileController :: readWordFile :: end");
        return ResponseEntity.ok().body(resp);

    }

    @PostMapping("/readDocFile")
    public ResponseEntity<String> readDocFile(@RequestParam("inputFile") MultipartFile file) {
        logger.info("FileController :: readDocFile :: start");
        String resp = wordPocService.readDocFile(file);
        logger.info("FileController :: readDocFile :: end");
        return ResponseEntity.ok().body(resp);

    }
}

package com.saama.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * @author bhushan.kathar
 */
public interface WordPocService {

    String readText(MultipartFile file);

    String readDocFile(MultipartFile file);

    String readByTable(MultipartFile file);

    String readByParagraph(MultipartFile file);

    String readByHeader(MultipartFile file);

    String readByFooter(MultipartFile file);

    ByteArrayInputStream writeFile();

    String getString();
}
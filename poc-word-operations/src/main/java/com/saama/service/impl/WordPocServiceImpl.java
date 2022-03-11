package com.saama.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.saama.service.WordPocService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author bhushan.kathar
 */
@Service
public class WordPocServiceImpl implements WordPocService {

    private Logger logger = LoggerFactory.getLogger(WordPocServiceImpl.class);

    @Override
    public String readText(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readText :: start");

        try (XWPFDocument document = new XWPFDocument(file.getInputStream());
             XWPFWordExtractor wordExtractor = new XWPFWordExtractor(document)) {
            String resp = wordExtractor.getText();
            logger.info("Getting text from the file : {}", wordExtractor.getText());
            logger.info("WordPocServiceImpl :: readText :: end");
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WordPocServiceImpl :: readText :: error :: {}",e.getMessage());
            return e.getMessage();
        }

    }

    @Override
    public String readDocFile(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readDocFile :: start");
        try {
            HWPFDocument doc = new HWPFDocument(file.getInputStream());
            logger.info("WordPocServiceImpl :: readDocFile :: end");
            return doc.getDocumentText();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("WordPocServiceImpl :: readDocFile :: error :: {}",e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }


    }

    @Override
    public String readByTable(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readByTable :: start");
        StringBuilder buildString = new StringBuilder();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {

            List<XWPFTable> xwpfTablesList = document.getTables();
            int tbNo = 0;
            for (XWPFTable table : xwpfTablesList) {
                logger.info("===============Table No : {}", tbNo);
                for (int i = 0; i < table.getRows().size(); i++) {

                    for (int j = 0; j < table.getRow(i).getTableCells().size(); j++) {
                        buildString.append(table.getRow(i).getCell(j).getText()).append("\n");
                        logger.info("Parsing document table wise : {} ", table.getRow(i).getCell(j).getText());
                    }
                }
                logger.info("===============Table end =============");
                tbNo++;
            }
            logger.info("WordPocServiceImpl :: readByTable :: end");
            return buildString.toString();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("WordPocServiceImpl :: readByTable :: error :: {}",e.getMessage());
            return e.getMessage();
        }



    }

    @Override
    public String readByParagraph(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readByParagraph :: start");
        StringBuilder buildString = new StringBuilder();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {

            List<XWPFParagraph> list = document.getParagraphs();
            for (XWPFParagraph paragraph : list) {
                logger.info("Getting paragraphs text : {}", paragraph.getText());
                buildString.append(paragraph.getText());
            }
            logger.info("WordPocServiceImpl :: readByParagraph :: end");
            return buildString.toString();
        } catch (Exception e) {
            logger.error("WordPocServiceImpl :: readByParagraph :: error :: {}",e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }


    }

    @Override
    public String readByHeader(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readByHeader :: start");
        StringBuilder buildString = new StringBuilder();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {

            List<XWPFHeader> list = document.getHeaderList();
            for (XWPFHeader xwpfHeader : list) {
                logger.info("Getting header text : {}", xwpfHeader.getText());
                buildString.append(xwpfHeader.getText());
            }
            logger.info("WordPocServiceImpl :: readByHeader :: end");
            return buildString.toString();
        } catch (Exception e) {
            logger.error("WordPocServiceImpl :: readByHeader :: error :: {}",e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @Override
    public String readByFooter(MultipartFile file) {
        logger.info("WordPocServiceImpl :: readByFooter :: start");
        StringBuilder buildString = new StringBuilder();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {

            List<XWPFFooter> list = document.getFooterList();
            for (XWPFFooter xwpfFooter : list) {
                logger.info("Getting footer text : {}", xwpfFooter.getText());
                buildString.append(xwpfFooter.getText());
            }
            logger.info("WordPocServiceImpl :: readByFooter :: end");
            return buildString.toString();
        } catch (Exception e) {
            logger.error("WordPocServiceImpl :: readByFooter :: error :: {}",e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }

    }


    @Override
    public ByteArrayInputStream writeFile() {
        logger.info("WordPocServiceImpl :: writeFile :: start");
        ByteArrayInputStream byteArrayInputStream = null;
        String para1Text = "For all reporting, program names are meaningful descriptions of the program function.  The program names are limited to 32 characters. All programs use the standard CDARS SAS programming header and footer information, in addition to the study specific-titles and footnotes.";

        String imgPath = "C:\\Users\\bhushan.kathar\\Downloads\\sama.png";
        try (XWPFDocument document = new XWPFDocument()) {

            XWPFHeader head = document.createHeader(HeaderFooterType.DEFAULT);
            head.createParagraph()
                    .createRun()
                    .setText("This is the header");

            XWPFFooter foot = document.createFooter(HeaderFooterType.DEFAULT);
            foot.createParagraph()
                    .createRun()
                    .setText("This is document footer");


            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun titleRun = title.createRun();
            titleRun.setText("This is the Demo POC.");
            titleRun.setColor("009933");
            titleRun.setBold(true);
            titleRun.setFontFamily("Courier");
            titleRun.setFontSize(20);

            XWPFParagraph p1 = document.createParagraph();
            p1.setAlignment(ParagraphAlignment.NUM_TAB);

            // Set Text to Bold and font size to 22 for first paragraph
            XWPFRun region = p1.createRun();
            region.setBold(true);
            region.setItalic(true);
            region.setUnderline(UnderlinePatterns.DASH_DOT_DOT_HEAVY);
            region.setFontSize(12);
            region.setText(para1Text);
            region.setFontFamily("Courier");
            region.addBreak();

            XWPFParagraph p2 = document.createParagraph();
            p1.setAlignment(ParagraphAlignment.NUM_TAB);

            // Set Text to Bold and font size to 22 for first paragraph
            XWPFRun region2 = p2.createRun();
            region2.setBold(true);
            region2.setItalic(true);
            region2.setUnderline(UnderlinePatterns.DASH_DOT_DOT_HEAVY);
            region2.setFontSize(12);
            region2.setText(para1Text);
            region2.setFontFamily("Courier");
            region2.addBreak();


            XWPFRun region3 = document.createParagraph().createRun();
            region3.addPicture(new FileInputStream(imgPath)
                    , Document.PICTURE_TYPE_PNG
                    , "sama"
                    , Units.toEMU(400)
                    , Units.toEMU(100));
            region3.addBreak();


            XWPFTable table = document.createTable();

            // Creating first Row

            XWPFTableRow row1 = table.getRow(0);
            row1.getCell(0).setText("First Row, First Column");
            row1.addNewTableCell().setText("First Row, Second Column");
            row1.addNewTableCell().setText("First Row, Third Column");

            // Creating second Row
            XWPFTableRow row2 = table.createRow();
            row2.getCell(0).setText("Second Row, First Column");
            row2.getCell(1).setText("Second Row, Second Column");
            row2.getCell(2).setText("Second Row, Third Column");

            // create third row
            XWPFTableRow row3 = table.createRow();
            row3.getCell(0).setText("Third Row, First Column");
            row3.getCell(1).setText("Third Row, Second Column");
            row3.getCell(2).setText("Third Row, Third Column");

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            document.write(b);
            byteArrayInputStream = new ByteArrayInputStream(b.toByteArray());

        } catch (IOException e) {
            logger.error("WordPocServiceImpl :: writeFile :: error :: {}",e.getMessage());
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            logger.info("invalid format exception while added image to file");
            e.printStackTrace();
        }

        return byteArrayInputStream;
    }

    @Override
    public String getString() {
        return "abc";
    }

}
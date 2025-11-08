package com.example.gandy.service;//package com.example.gandymobile.service;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
//import org.apache.poi.xwpf.usermodel.*;
//import org.springframework.stereotype.Service;
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigInteger;
//
//@Service
//public class DocumentService {
//
//    public ByteArrayOutputStream generateWordWithTable() throws IOException {
//        XWPFDocument document = new XWPFDocument();
//        XWPFParagraph paragraph = document.createParagraph();
//        XWPFRun run = paragraph.createRun();
//        run.setText("فاکتور فروش");
//
//
//        XWPFTable table = document.createTable();
//
//        XWPFTableRow tableRowOne = table.getRow(0);
//        tableRowOne.getCell(0).setText("ستون 1، ردیف 1");
//        tableRowOne.addNewTableCell().setText("ستون 2، ردیف 1");
//
//        XWPFTableRow tableRowTwo = table.createRow();
//        tableRowTwo.getCell(0).setText("ستون 1، ردیف 2");
//        tableRowTwo.getCell(1).setText("ستون 2، ردیف 2");
//
//        mergeCellsHorizontal(table, 1, 0, 1);
//
//
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        document.write(out);
//        return out;
//    }
//
//    public BufferedImage convertWordToImage(ByteArrayOutputStream wordOutputStream) throws IOException {
//        InputStream inputStream = new ByteArrayInputStream(wordOutputStream.toByteArray());
//        XWPFDocument document = new XWPFDocument(inputStream);
//
//        BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics = bufferedImage.createGraphics();
//        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(0, 0, 800, 600);
//
//        // Example drawing (for simplicity)
//        graphics.setColor(Color.BLACK);
//        graphics.drawString("جدول به زبان فارسی", 50, 50);
//
//        for (int i = 0; i < document.getTables().size(); i++) {
//            XWPFTable table = document.getTables().get(i);
//            int y = 100 + i * 100;
//            for (XWPFTableRow row : table.getRows()) {
//                int x = 50;
//                for (XWPFTableCell cell : row.getTableCells()) {
//                    graphics.drawRect(x, y, 100, 30);
//                    graphics.drawString(cell.getText(), x + 10, y + 20);
//                    x += 100;
//                }
//                y += 30;
//            }
//        }
//        graphics.dispose();
//        return bufferedImage;
//    }
//
//    public ByteArrayOutputStream convertImageToPDF(BufferedImage image) throws IOException {
//        ByteArrayOutputStream imageOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "png", imageOutputStream);
//        byte[] imageBytes = imageOutputStream.toByteArray();
//
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage();
//            document.addPage(page);
//
//            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageBytes, "temp");
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                contentStream.drawImage(pdImage, 20, 20);
//            }
//
//            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//            document.save(pdfOutputStream);
//            return pdfOutputStream;
//        }
//    }
//
//
//    public ByteArrayOutputStream generatePDF() throws IOException {
//        ByteArrayOutputStream wordOutputStream = generateWordWithTable();
//        BufferedImage image = convertWordToImage(wordOutputStream);
//        return convertImageToPDF(image);
//    }
//
//
//    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
//        XWPFTableRow tableRow = table.getRow(row);
//        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
//            XWPFTableCell cell = tableRow.getCell(cellIndex);
//            if (cellIndex == fromCell) {
//                // Set the grid span for the first cell
//                cell.getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(toCell - fromCell + 1));
//            } else {
//                // Remove the cell from the table row
//                tableRow.removeCell(cellIndex);
//            }
//        }
//    }
//}

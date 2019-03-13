/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
 
public class ExcelGenerator {
 
 public static ByteArrayInputStream courseworksToExcel(List<CourseworkEntry> courseworks) throws IOException {
        String[] COLUMNs = {"Class",
                            "Id",
                            "Seat",
                            "Task Fulfilment 40%",
                            "Language use 20%",
                            "Organisation 40%",
                            "MMR Overall"};
        try(
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        )
        {
           CreationHelper createHelper = workbook.getCreationHelper();

           Sheet sheet = workbook.createSheet("MMR");
           sheet.setDefaultColumnWidth(20);

           Font headerFont = workbook.createFont();
           headerFont.setBold(true);
           headerFont.setColor(IndexedColors.BLACK.getIndex());

           CellStyle headerCellStyle = workbook.createCellStyle();
           headerCellStyle.setFont(headerFont);

           // Row for Header
           Row headerRow = sheet.createRow(0);

           // Header
           for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
           }

           // CellStyle
           CellStyle cellStyle = workbook.createCellStyle();
           cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
           
           int rowIdx = 1;
           for (CourseworkEntry courseworkEntry : courseworks) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(courseworkEntry.getStudent().getClass().toString());
                row.createCell(1).setCellValue(courseworkEntry.getStudent().getId());
                row.createCell(2).setCellValue(courseworkEntry.getStudent().getSeat());
                row.createCell(3).setCellValue(courseworkEntry.getCategoryAverage().get(0));
                row.createCell(4).setCellValue(courseworkEntry.getCategoryAverage().get(1));
                row.createCell(5).setCellValue(courseworkEntry.getCategoryAverage().get(2));
                row.createCell(6).setCellValue(courseworkEntry.getOverallScore());

                /*Cell ageCell = row.createCell(3);
                ageCell.setCellValue(customer.getAge());
                ageCell.setCellStyle(ageCellStyle);*/
           }

           workbook.write(out);
           return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
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
import uk.ac.bris.celfs.database.Student;
 
public class ExcelGenerator {
 
 public static ByteArrayInputStream studentsToExcel(List<Student> customers) throws IOException {
        String[] COLUMNs = {"Id", "Name", "Address", "Age"};
        try(
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        )
        {
           CreationHelper createHelper = workbook.getCreationHelper();

           Sheet sheet = workbook.createSheet("Students");

           Font headerFont = workbook.createFont();
           headerFont.setBold(true);
           headerFont.setColor(IndexedColors.BLUE.getIndex());

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

           // CellStyle for Age
           CellStyle ageCellStyle = workbook.createCellStyle();
           ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
           /*
           int rowIdx = 1;
           for (Customer customer : customers) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(customer.getId());
                row.createCell(1).setCellValue(customer.getName());
                row.createCell(2).setCellValue(customer.getAddress());

                Cell ageCell = row.createCell(3);
                ageCell.setCellValue(customer.getAge());
                ageCell.setCellStyle(ageCellStyle);
           }*/

           workbook.write(out);
           return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
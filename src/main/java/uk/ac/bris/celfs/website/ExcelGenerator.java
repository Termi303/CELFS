/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.bris.celfs.website;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
        List<String> COLUMNs = new ArrayList();
        COLUMNs.add("Class");
        COLUMNs.add("Id");
        COLUMNs.add("Seat");
        if (!courseworks.isEmpty())
            for(int a=0;a<courseworks.get(0).getCategoryAverage().size();a++)
            {
                COLUMNs.add("Partial Mark "+(a+1));
            }
        
        COLUMNs.add("Overall");
        try(
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        )
        {
           CreationHelper createHelper = workbook.getCreationHelper();

           Sheet sheet = workbook.createSheet("Coursework");
           sheet.setDefaultColumnWidth(20);

           Font headerFont = workbook.createFont();
           headerFont.setBold(true);
           headerFont.setColor(IndexedColors.BLACK.getIndex());

           CellStyle headerCellStyle = workbook.createCellStyle();
           headerCellStyle.setFont(headerFont);

           // Row for Header
           Row headerRow = sheet.createRow(0);

           // Header
           for (int col = 0; col < COLUMNs.size(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs.get(col));
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
                
                for(int a=0;a<courseworks.get(0).getCategoryAverage().size();a++)
                {
                    row.createCell(a+3).setCellValue(courseworkEntry.getCategoryAverage().get(a));
                }
                
                row.createCell(COLUMNs.size()-1).setCellValue(courseworkEntry.getOverallScore());
           }

           workbook.write(out);
           return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
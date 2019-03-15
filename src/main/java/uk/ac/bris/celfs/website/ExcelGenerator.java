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
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.services.TablesService;
 
public class ExcelGenerator {
 
 public static ByteArrayInputStream courseworksToExcel(List<CourseworkEntry> courseworkEntries, TablesService tablesService) throws IOException {
        List<String> COLUMNs = new ArrayList();
        COLUMNs.add("Class");
        COLUMNs.add("Id");
        COLUMNs.add("Seat");
        List<Category> categories = new ArrayList();
        if (!courseworkEntries.isEmpty())
        {
            categories = tablesService.getCategories(courseworkEntries.get(0).getCoursework().getId());
            for(int a=0; a<categories.size(); a++)
            {
                COLUMNs.add(categories.get(a).getName()+" "+categories.get(a).getWeight()*100+"%");
            }
        }
        
        COLUMNs.add("Overall");
        try(
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        )
        {
           CreationHelper createHelper = workbook.getCreationHelper();

           Sheet sheet = workbook.createSheet("Coursework");
           sheet.setColumnWidth(0, 40*256);
           sheet.setColumnWidth(1, 10*256);
           sheet.setColumnWidth(2, 10*256);
           for(int a=3;a<COLUMNs.size()-1; a++)
           {
               sheet.setColumnWidth(a, (categories.get(a-3).getName().length()+7)*256);
           }
           sheet.setColumnWidth(COLUMNs.size()-1, 15*256);

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
           for (CourseworkEntry courseworkEntry : courseworkEntries) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(courseworkEntry.getStudent().getClass().toString());
                row.createCell(1).setCellValue(courseworkEntry.getStudent().getId());
                row.createCell(2).setCellValue(courseworkEntry.getStudent().getSeat());
                
                for(int a=0;a<courseworkEntries.get(0).getCategoryAverage().size();a++)
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
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import uk.ac.bris.celfs.coursework.Coursework;
import uk.ac.bris.celfs.coursework.CourseworkEntry;
import uk.ac.bris.celfs.database.Category;
import uk.ac.bris.celfs.database.Student;
import uk.ac.bris.celfs.services.CourseworkEntryService;
import uk.ac.bris.celfs.services.TablesService;

public class ExcelGenerator {

    public static ByteArrayInputStream courseworksToExcel(List<CourseworkEntry> courseworkEntries, TablesService tablesService, CourseworkEntryService courseworkEntryService) throws IOException {
        // System.out.println("The courseworkEntries are: ");
        // System.out.println(courseworkEntries.toString());
         
        
        // Adding Columns ----------------------
        List<String> COLUMNs = new ArrayList<>();
        COLUMNs.add("Class");
        COLUMNs.add("Id");
        COLUMNs.add("Seat");
        List<Category> categories;
        for (Coursework c : tablesService.getAllCourseworks()) {
            categories = tablesService.getCategories(c.getId());
            for (int a = 0; a < categories.size(); a++) {
                COLUMNs.add(categories.get(a).getName() + " " + categories.get(a).getWeight() * 100 + "%");
            }
            COLUMNs.add(c.getName() + " Overall Mark");
        }
        COLUMNs.add("Overall");
        // ----------------------------------------

        Set<Student> students = new LinkedHashSet<>();
        for (CourseworkEntry courseworkEntry : courseworkEntries) {
            students.add(courseworkEntry.getStudent());
        }

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Coursework");
            sheet.setColumnWidth(0, 40 * 256);
            sheet.setColumnWidth(1, 10 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            int column = 3;
            for (Coursework c : tablesService.getAllCourseworks()) {
                categories = tablesService.getCategories(c.getId());
                for (int a = 0; a < categories.size(); a++) {
                    sheet.setColumnWidth(column, (categories.get(a).getName().length() + 7) * 256);
                    column++;
                }
                sheet.setColumnWidth(column, ((c.getName() + " Overall Mark").length() + 7) * 256);
                column++;
            }
            sheet.setColumnWidth(COLUMNs.size() - 1, 15 * 256);

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
            int overallMark;
            int overallNumber;

            for (Student student : students) {

                overallMark = 0;
                overallNumber = 0;

                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(student.getClass().toString());
                row.createCell(1).setCellValue(student.getId());
                row.createCell(2).setCellValue(student.getSeat());

                column = 3;
                CourseworkEntry currentCourseworkEntry;

                for (Coursework c : tablesService.getAllCourseworks()) {
                    currentCourseworkEntry = null;
                    for (CourseworkEntry ce : courseworkEntries) {
                        if (ce.getCoursework().getId().equals(c.getId()) && ce.getStudent().getId().equals(student.getId())) {
                            currentCourseworkEntry = ce;
                        }
                    }

                    if (currentCourseworkEntry != null) {
                        overallMark   += currentCourseworkEntry.getOverallScore();
                        overallNumber ++;
                    }
                    
                    categories = tablesService.getCategories(c.getId());
                    for (int a = 0; a < categories.size(); a++) {
                        if (currentCourseworkEntry != null) {
                            row.createCell(column).setCellValue(currentCourseworkEntry.getCategoryAverage().get(a));
                        } else {
                            row.createCell(column).setCellValue("NA");
                        }
                        column++;
                    }
                    if (currentCourseworkEntry != null) {
                        row.createCell(column).setCellValue(currentCourseworkEntry.getOverallScore());
                    } else {
                        row.createCell(column).setCellValue("NA");
                    }
                    column++;
                }
                row.createCell(column).setCellValue(overallMark / overallNumber);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}

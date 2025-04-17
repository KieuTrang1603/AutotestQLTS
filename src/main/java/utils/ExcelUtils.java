package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {
    public static void writeDataToExcel(String filePath, int startRow, List<String> data) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        Row row = sheet.getRow(startRow);
        if (row == null) row = sheet.createRow(startRow);

        for (int i = 0; i < data.size(); i++) {
            row.createCell(i).setCellValue(data.get(i));
        }

        fis.close();

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    public static void clearDataFromRow(String filePath, int fromRowIndex) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum(); // Lấy dòng cuối có dữ liệu

        // Xóa từng dòng từ fromRowIndex đến cuối
        for (int i = fromRowIndex; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                sheet.removeRow(row);
            }
        }

        fis.close();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }

    public static String getDataFromErrorRow(String filePath, int fromRowIndex) throws IOException{
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(fromRowIndex);
        Cell cell = row.getCell(7);
        String value= cell.getStringCellValue();
        fis.close();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
        return value;
    }
}

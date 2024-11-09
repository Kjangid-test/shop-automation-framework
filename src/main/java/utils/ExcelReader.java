package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    private static final String FILE_PATH = "src/test/resources/testdata.xlsx";  // path to your Excel file

    // Read Excel data and return it as a Map
    public static Map<String, String> getCheckoutData(String sheetName) {
        Map<String, String> data = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);
            DataFormatter formatter = new DataFormatter();
            sheet.forEach(row -> {
                String key = formatter.formatCellValue(row.getCell(0));   // Read key (column 0)
                String value = formatter.formatCellValue(row.getCell(1)); // Read value (column 1)
                data.put(key, value);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

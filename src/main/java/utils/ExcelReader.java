package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {

    
        @SuppressWarnings("resource")
		public static Map<String, String> readCheckoutData(String filePath) {
            Map<String, String> checkoutData = new HashMap<>();
            try {
                FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook;
                if (filePath.endsWith(".xlsx")) {
                    workbook = new XSSFWorkbook(fis);
                } else if (filePath.endsWith(".xls")) {
                    workbook = new HSSFWorkbook(fis);
                } else {
                    throw new IllegalArgumentException("Invalid file format. Please provide an .xls or .xlsx file.");
                }
                
                Sheet sheet = workbook.getSheetAt(0);
                if (sheet.getPhysicalNumberOfRows() > 1) {
                    for (Row row : sheet) {
                        String fieldName = row.getCell(0).getStringCellValue();
                        
                        // Get the cell value as a String, even if it's numeric
                        String value = "";
                        Cell cell = row.getCell(1);
                        
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case NUMERIC:
                                	
                                	if (DateUtil.isCellDateFormatted(cell)) {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM YYYY");
                                        value =  dateFormat.format(cell.getDateCellValue());
                                        
                                      } else {
                                        DecimalFormat decimalFormat = new DecimalFormat("#");
                                        value =  decimalFormat.format(cell.getNumericCellValue());
                                      }
                                	
										/*
										 * // Handle numbers, but convert to string value =
										 * String.valueOf(cell.getNumericCellValue()); // Remove any scientific notation
										 * if it's a large number (e.g., credit card number) if (value.contains("E")) {
										 * value = String.format("%.0f", cell.getNumericCellValue()); }
										 */
                                    break;
                                case BOOLEAN:
                                    value = String.valueOf(cell.getBooleanCellValue());
                                    break;
                                default:
                                    value = "";
                                    break;
                            }
                        }
                        
                        checkoutData.put(fieldName, value);
                        System.out.println("Field: " + fieldName + " Value: " + value);  // Debugging output
                    }
                }

                workbook.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Checkout Data Loaded: " + checkoutData); // Debugging output
            return checkoutData;
        }


    public static void main(String[] args) {
        // Test the method with the file path
        readCheckoutData("src/test/resources/DataSheet.xlsx");
    }
}

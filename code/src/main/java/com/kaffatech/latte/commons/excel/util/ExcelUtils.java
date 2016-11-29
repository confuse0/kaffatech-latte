package com.kaffatech.latte.commons.excel.util;

import com.kaffatech.latte.commons.excel.model.StrXlsSheet;
import com.kaffatech.latte.commons.excel.model.TitledStrXlsSheet;
import com.kaffatech.latte.commons.io.model.exception.IoRuntimeException;
import com.kaffatech.latte.commons.io.util.FileUtils;
import com.kaffatech.latte.commons.toolkit.base.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingzhen on 16/9/10.
 */
public class ExcelUtils {

    public static TitledStrXlsSheet readOneForTitledStrXlsSheet(File file) {
        TitledStrXlsSheet titledSheet = new TitledStrXlsSheet();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                titledSheet = parseSheetForTitledXlsSheet(sheet);
                break;
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return titledSheet;
    }

    public static TitledStrXlsSheet readOneForTitledStrXlsSheet(File file, String sheetName) {
        TitledStrXlsSheet titledSheet = new TitledStrXlsSheet();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                if (StringUtils.equals(sheetName, sheet.getSheetName())) {
                    titledSheet = parseSheetForTitledXlsSheet(sheet);
                    break;
                }
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return titledSheet;
    }

    public static List<TitledStrXlsSheet> readForTitledStrXlsSheet(File file) {
        List<TitledStrXlsSheet> sheetList = new ArrayList<TitledStrXlsSheet>();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                sheetList.add(parseSheetForTitledXlsSheet(sheet));
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return sheetList;
    }

    private static TitledStrXlsSheet parseSheetForTitledXlsSheet(XSSFSheet sheet) {
        List<Map<String, String>> recList = new ArrayList<Map<String, String>>();
        Map<Integer, String> titleMap = null;
        int totalRowNum = sheet.getLastRowNum();
        for (int rowNum = 0; rowNum <= totalRowNum; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (rowNum == 0) {
                // 读取TITLE
                titleMap = parsetTitleRow(row);
            } else {
                recList.add(parsetRecordRow(row, titleMap));
            }
        }

        TitledStrXlsSheet titledSheet = new TitledStrXlsSheet();
        titledSheet.setRecordList(recList);
        return titledSheet;
    }

    private static Map<Integer, String> parsetTitleRow(XSSFRow row) {
        Map<Integer, String> titleMap = new HashMap<Integer, String>();
        int totalCellNum = row.getLastCellNum();
        for (int cellNum = 0; cellNum < totalCellNum; cellNum++) {
            XSSFCell cell = row.getCell(cellNum);
            titleMap.put(cellNum, cell.getStringCellValue());
        }
        return titleMap;
    }

    private static Map<String, String> parsetRecordRow(XSSFRow row, Map<Integer, String> titleMap) {
        Map<String, String> recMap = new HashMap<String, String>();
        int totalCellNum = row.getLastCellNum();
        for (int cellNum = 0; cellNum < totalCellNum; cellNum++) {
            XSSFCell cell = row.getCell(cellNum);
            recMap.put(titleMap.get(cellNum), cell.getStringCellValue());
        }
        return recMap;
    }

    /**
     * no title
     *
     * @param file
     * @return
     */
    public static StrXlsSheet readOneSheetForStrXlsSheet(File file) {
        StrXlsSheet strSheet = new StrXlsSheet();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                strSheet = parseSheetForStrXlsSheet(sheet);
                break;
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return strSheet;
    }

    public static StrXlsSheet readOneSheetForStrXlsSheet(File file, String sheetName) {
        StrXlsSheet strSheet = new StrXlsSheet();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                if (StringUtils.equals(sheetName, sheet.getSheetName())) {
                    strSheet = parseSheetForStrXlsSheet(sheet);
                    break;
                }
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return strSheet;
    }

    public static List<StrXlsSheet> readSheetForStrXlsSheet(File file) {
        List<StrXlsSheet> sheetList = new ArrayList<StrXlsSheet>();
        InputStream is = null;
        try {
            is = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(is);
            // 解析多个sheet
            int sheetNum = wb.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                XSSFSheet sheet = wb.getSheetAt(i);
                sheetList.add(parseSheetForStrXlsSheet(sheet));
            }
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            FileUtils.close(is);
        }
        return sheetList;
    }


    private static StrXlsSheet parseSheetForStrXlsSheet(XSSFSheet sheet) {
        List<List<String>> recList = new ArrayList<List<String>>();
        List<String> strList = null;
        int totalRowNum = sheet.getLastRowNum();
        for (int rowNum = 0; rowNum < totalRowNum; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            recList.add(parsetRecordRow(row));
        }

        StrXlsSheet strSheet = new StrXlsSheet();
        strSheet.setRecordList(recList);
        return strSheet;
    }

    private static List<String> parsetRecordRow(XSSFRow row) {
        List<String> recList = new ArrayList<String>();
        int totalCellNum = row.getLastCellNum();
        for (int cellNum = 0; cellNum < totalCellNum; cellNum++) {
            XSSFCell cell = row.getCell(cellNum);
            recList.add(cell.getStringCellValue());
        }
        return recList;
    }

}

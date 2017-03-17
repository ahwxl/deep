package com.bplow.deep.base.utils;

import java.io.IOException;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
    
    public void read() throws InvalidFormatException, IOException{
        
        OPCPackage pkg = OPCPackage.open("D:/www/work/G-光大银行/借记卡快捷接入/接口文档/光大借记卡快捷接口整理.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(pkg);
        
        int sheetsnum = wb.getNumberOfSheets();
        
        for(int i=0;i<sheetsnum;i++){
            
            Sheet sheet = wb.getSheetAt(i);
            
            String sheetName = sheet.getSheetName();
            
            DataFormatter formatter = new DataFormatter();
            for (Row row : sheet) {
                for (Cell cell : row) {

                    String text = formatter.formatCellValue(cell);

                }
            }
            
            
            
        }
        
        pkg.close();
    }
    
    
    public static void main(String[] args) {
        ExcelUtils e = new ExcelUtils();
        try {
            e.read();
        } catch (InvalidFormatException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

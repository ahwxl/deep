package com.bplow.deep.base.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    List<Table> tables = new ArrayList<Table>();

    public void read() throws InvalidFormatException, IOException {

        OPCPackage pkg = OPCPackage.open("D:/www/work/网联平台/网联报文字段整理2.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(pkg);

        int sheetsnum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetsnum; i++) {
            Sheet sheet = wb.getSheetAt(i);

            String sheetName = sheet.getSheetName();
            Table newtable = new Table(sheetName);
            tables.add(newtable);
            DataFormatter formatter = new DataFormatter();
            int rownum = 0;
            for (Row row : sheet) {
                rownum++;
                if (rownum == 1)
                    continue;
                //判断是否为最后一空行
                Cell fieldnameCell = row.getCell(1);
                String lastrowcell = formatter.formatCellValue(fieldnameCell);
                if (StringUtils.isBlank(lastrowcell)) {
                    break;
                }

                Field newField = new Field();
                newtable.addField(newField);
                for (Cell cell : row) {

                    String text = formatter.formatCellValue(cell);
                    int colummnIndex = cell.getColumnIndex();
                    if (1 == colummnIndex) {
                        newField.setName(text);
                        newField.setComment(text);
                    } else if (2 == colummnIndex) {
                        newField.setType(text);
                    }

                }

            }
        }

        pkg.close();
    }

    public String getTableDml() {

        StringBuilder sql = new StringBuilder();

        for (Table tmptable : tables) {

            sql.append("\nCREATE TABLE ");
            sql.append(tmptable.getName()).append("(\n");

            List<Field> fields = tmptable.getFields();
            int nums = fields.size();
            int i = 0;
            for (Field field : fields) {
                sql.append(changerName(field.getName())).append(" VARCHAR(32) ");
                sql.append(" COMMENT '").append(field.getComment());
                i++;
                if (i < nums)
                    sql.append("',\n");
                else
                    sql.append("'");
            }

            sql.append(");");
        }

        return sql.toString();
    }

    public String printTableNames() {
        StringBuilder sql = new StringBuilder();

        for (Table tmptable : tables) {
            sql.append("<table tableName=\"").append(tmptable.getName()).append("\" />\n");
        }
        System.out.println(sql.toString());
        return null;
    }

    class Field {

        String name;
        String comment;
        String type;

        public Field() {
            super();
        }

        public Field(String name) {
            super();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

    class Table {

        String      name;
        List<Field> fields = new ArrayList<Field>();

        public Table(String name) {
            super();
            this.name = name;
        }

        public void addField(Field field) {
            this.fields.add(field);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Field> getFields() {
            return fields;
        }

        public void setFields(List<Field> fields) {
            this.fields = fields;
        }

    }

    public String changerName(String original) {

        String txt = original;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < txt.length()) {
            char tmp = txt.charAt(i);
            if (i == 0) {
                sb.append(tmp);
            } else {
                if (Character.isUpperCase(tmp)) {
                    sb.append("_").append(Character.toLowerCase(tmp));
                } else {
                    sb.append(tmp);
                }
            }
            i++;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ExcelUtils e = new ExcelUtils();
        try {
            e.read();
            String sql = e.getTableDml();
            System.out.println(sql);

            e.printTableNames();
        } catch (InvalidFormatException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}

package com.ascend.wangfeng.locationby4g.util;

import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.locationby4g.MainApp;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengye on 2018/3/30.
 * email 1040441325@qq.com
 */

public class ExcelUtil {
    private static final String BASE_PATH = MainApp.getContent().getString(R.string.base_path);

    public static boolean writeExcel(List<CellMeaureAckEntry> entries, String filename) {
        Workbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = (HSSFSheet) workbook.createSheet("imsi");
        fillData(entries, sheet);
        OutputStream os = null;
        File file = FileUtil.createFileByTime(BASE_PATH, filename, "xls");
        try {
            os = new FileOutputStream(file);
            workbook.write(os);
            os.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<CellMeaureAckEntry> readExcel(String filename) {
        ArrayList<CellMeaureAckEntry> mEntries = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(BASE_PATH + filename);
            Workbook wk = new HSSFWorkbook(fis);
            HSSFSheet sheet = (HSSFSheet) wk.getSheetAt(0);
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                CellMeaureAckEntry entry = new CellMeaureAckEntry();
                entry.setTimestamp(row.getCell(0).getDateCellValue().getTime()/1000);
                entry.setImsi(row.getCell(1).getStringCellValue());
                entry.setFieldIntensity((int) row.getCell(2).getNumericCellValue());
                boolean has = false;// 是否已存在
                for (int j = 0; j < mEntries.size(); j++) {
                    if (mEntries.get(i).getImsi().equals(entry.getImsi())) {
                        mEntries.get(i).copy(entry);
                        has = true;
                        break;
                    }
                }
                if (!has){mEntries.add(entry);}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return mEntries;
        }
    }

    private static void fillData(List<CellMeaureAckEntry> entries, HSSFSheet sheet) {
        int rowCounter = 0;// 行计数
        int cellCounter = 0;// 单元格计数
        HSSFRow row = sheet.createRow(rowCounter++);
        row.createCell(cellCounter++).setCellValue("时间");
        row.createCell(cellCounter++).setCellValue("IMSI");
        row.createCell(cellCounter++).setCellValue("场强");
        row.createCell(cellCounter++).setCellValue("运营商");
        cellCounter = 0;
        for (int i = 0; i < entries.size(); i++) {
            CellMeaureAckEntry entry = entries.get(i);
            for (int j = 0; j < entry.getFieldIntensitys().size(); j++) {
                HSSFRow row1 = sheet.createRow(rowCounter++);
                row1.createCell(cellCounter++).setCellValue(TimeUtil.format(entry.getTimeStamps().get(j)));
                row1.createCell(cellCounter++).setCellValue(entry.getImsi());
                row1.createCell(cellCounter++).setCellValue(entry.getFieldIntensitys().get(j));
                row1.createCell(cellCounter++).setCellValue(ImsiUtil.getImsiOperator(entry.getImsi()));
                cellCounter = 0;
            }
            HSSFRow row1 = sheet.createRow(rowCounter++);
            row1.createCell(cellCounter++).setCellValue(TimeUtil.format(entry.getTimestamp()));
            row1.createCell(cellCounter++).setCellValue(entry.getImsi());
            row1.createCell(cellCounter++).setCellValue(entry.getFieldIntensity());
            row1.createCell(cellCounter++).setCellValue(ImsiUtil.getImsiOperator(entry.getImsi()));
            cellCounter = 0;
        }
    }
}

package com.ascend.wangfeng.locationby4g.util;

import android.util.Log;

import com.ascend.wangfeng.latte.util.FileUtil;
import com.ascend.wangfeng.locationby4g.MainApp;
import com.ascend.wangfeng.locationby4g.R;
import com.ascend.wangfeng.locationby4g.delegates.imsi.CellMeaureAckEntry;
import com.ascend.wangfeng.locationby4g.services.bean.CellMeaureAck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by fengye on 2018/3/30.
 * email 1040441325@qq.com
 */

public class ExcelUtil {
    private static final String BASE_PATH = MainApp.getContent().getString(R.string.base_path);

    public static boolean writeExcel(List<CellMeaureAckEntry> entries, String filename) {
        ArrayList<String[]> data = fillData(entries);
        File file = FileUtil.createFileByTime(BASE_PATH, filename, "csv");
        Writer writer = null;
        try {
            writer = new FileWriter(file);
            CSVWriter csvWriter = new CSVWriter(writer, ',');
            for (int i = 0; i < data.size(); i++) {
                csvWriter.writeNext(data.get(i));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<CellMeaureAckEntry> readExcel(File file) {
        ArrayList<CellMeaureAckEntry> mEntries = new ArrayList<>();
        try {
            FileReader fis = new FileReader(file);
            CSVReader csvReader = new CSVReader(fis);
            List<String[]> data = csvReader.readAll();
            for (int i = 1; i < data.size(); i++) {
                String[] strings = data.get(i);
                CellMeaureAckEntry entry = new CellMeaureAckEntry();
                entry.setTimestamp(TimeUtil.getTimeStamp(strings[0]));
                entry.setImsi(strings[1]);
                entry.setFieldIntensity(Integer.parseInt(strings[2]));
                boolean has = false;// 是否已存在
                for (int j = 0; j < mEntries.size(); j++) {
                    if (mEntries.get(j).getImsi().equals(entry.getImsi())) {
                        mEntries.get(j).copy(entry);
                        has = true;
                        break;
                    }
                }
                if (!has) {
                    mEntries.add(entry);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("ExcelUtil", "readExcel: ", e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExcelUtil", "readExcel: ", e);
        } finally {
            return mEntries;
        }
    }

    private static ArrayList<String[]> fillData(List<CellMeaureAckEntry> entries) {
        ArrayList<String[]> strsList = new ArrayList<>();

        // 填充标题
        String[] strings = new String[4];
        strings[0] = "时间";
        strings[1] = "IMSI";
        strings[2] = "场强";
        strings[3] = "运营商";
        strsList.add(strings);
        // 数据组合排序
        ArrayList<CellMeaureAck> acks = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            CellMeaureAckEntry entry = entries.get(i);
            for (int j = 0; j < entry.getFieldIntensitys().size(); j++) {
                CellMeaureAck ack = new CellMeaureAck();
                ack.setImsi(entry.getImsi());
                ack.setTimestamp(entry.getTimeStamps().get(j));
                ack.setFieldIntensity(entry.getFieldIntensitys().get(j));
                acks.add(ack);
            }
            CellMeaureAck ack = new CellMeaureAck();
            ack.setImsi(entry.getImsi());
            ack.setTimestamp(entry.getTimestamp());
            ack.setFieldIntensity(entry.getFieldIntensity());
            acks.add(ack);

        }
        Collections.sort(acks, new Comparator<CellMeaureAck>() {
            @Override
            public int compare(CellMeaureAck o1, CellMeaureAck o2) {
                return (int) (o1.getTimestamp() - o2.getTimestamp());
            }
        });
        // 填充数据
        for (CellMeaureAck entry : acks) {
            String[] strings1 = new String[4];
            strings1[0] = TimeUtil.format(entry.getTimestamp());
            strings1[1] = entry.getImsi();
            strings1[2] = String.valueOf(entry.getFieldIntensity());
            strings1[3] = ImsiUtil.getImsiOperator(entry.getImsi());
            strsList.add(strings1);
        }
        return strsList;
    }
}

package com.guo.common.util.excel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExcelUtilDemo {
  public static void main(String[] args) {

    String fileName = "河南专线列表-IMS专线开通3";
    String readFilePath = "/Users/guoxiaoxiao/Desktop/"+fileName+".xlsx";
    String writeFilePaht = "/Users/guoxiaoxiao/Desktop/"+fileName+".sql";
    List<List<List<Object>>> datas = ExcelUtil.readExcel(readFilePath);
//    System.out.println("多少页 = " + datas.size());
//    for (List<List<Object>> d1 : datas) {
//      System.out.println("多少行 = " + d1.size());
//      for (List<Object> d2 : d1) {
//        System.out.println("多少列 = " + d2.toString());
//      }
//    }
    String sql = excelToSql(fileName, datas);
    write(writeFilePaht, sql);
  }

  public static String excelToSql(String fileName,List<List<List<Object>>> datas) {
    String sqlResult = "";
    String sql = "INSERT INTO enum_contrast_config(remark, target_name, target_code, source_name, source_code, contrast_type, province_code, uuid, create_by, update_by, create_time, update_time, class_id, serial_no) " +
        "VALUES('','%s', '%s', '%s', '%s','PRODUCT_LIST',  '河南' ,LOWER(sys_guid()), 'guoxc3', 'guoxc3', sysdate, sysdate, '', %s);\n";
    String source_name = "";
    String source_code = "";
    String target_name = "";
    String target_code = "";
    String serial_no = "";
    for (List<List<Object>> data : datas) {
      for (int i = 1; i < data.size(); i++) {
        List<Object> d = data.get(i);
        System.out.println("多少列 = " + d.toString());
        source_name = (String) d.get(0);
        source_code = (String) d.get(1);
        target_name = (String) d.get(2);
        target_code = (String) d.get(3);
        serial_no = (String) d.get(4);
        String result = String.format(sql, source_name, source_code, target_name, target_code, serial_no);
        System.out.println("sql = " + result);
        sqlResult += result;
      }
    }
    return sqlResult;
  }


  private static void write(String fileName, String content) {
    System.out.println("写入：" + fileName +";内容："+ content);
    try {
      if (fileName == null || fileName.equals("")) {
        fileName = "脚本.sql";
      }
      File file = new File(fileName);
      file.delete();
      file.createNewFile();
      FileWriter fileWritter = new FileWriter(file.getName(), true);
      fileWritter.write(content);
      fileWritter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


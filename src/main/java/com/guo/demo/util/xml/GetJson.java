package com.guo.demo.util.xml;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * xml转json
 */
public class GetJson {
  //用Element方式
  public static void element(NodeList list) {
    for (int i = 0; i < list.getLength(); i++) {
      Element element = (Element) list.item(i);
      NodeList childNodes = element.getChildNodes();
      for (int j = 0; j < childNodes.getLength(); j++) {
        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
          //获取节点
          System.out.print(childNodes.item(j).getNodeName() + ":");
          //获取节点值
          System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
        }
      }
    }
  }

  public static void node(NodeList list) {
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      NodeList childNodes = node.getChildNodes();
      for (int j = 0; j < childNodes.getLength(); j++) {
        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
          System.out.print(childNodes.item(j).getNodeName() + ":");
          System.out.println(childNodes.item(j).getFirstChild().getNodeValue());
        }
      }
    }
  }

  public static void toJson(String fileName, NodeList list) {
    String result = "{\r";
    for (int i = 0; i < list.getLength(); i++) {
      Node node = list.item(i);
      NodeList childNodes = node.getChildNodes();
      for (int j = 0; j < childNodes.getLength(); j++) {
        if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE) {
          if (childNodes.item(j).getNodeName().equals("fieldEnName")) {
            result += "\"" + childNodes.item(j).getFirstChild().getNodeValue() + "\"" + ":";
//						System.out.print("\"" + childNodes.item(j).getFirstChild().getNodeValue() + "\"" + ":");
          }
          if (childNodes.item(j).getNodeName().equals("fieldContent")) {
            try {
              result += "\"" + childNodes.item(j).getFirstChild().getNodeValue() + "\"" + ",";
//							System.out.println("\"" + childNodes.item(j).getFirstChild().getNodeValue() + "\"" + ",");
            } catch (Exception e) {
              result += "\"\"" + ",";
//							System.out.println("\"\"" + ",");
            }
          }
        }
      }
      result += "\r";
    }
    result = result.substring(0, result.length() - 2);
//		System.out.print("}");
    result += "\n}";
    System.out.println("---------报文输出-----");
    System.out.printf("resutl==" + result);
    write(fileName, result);
  }

  private static void write(String fileName, String content) {
    try {
      if (fileName == null || fileName.equals("")) {
        fileName = "报文.json";
      }
      File file = new File(fileName);

      //if file doesnt exists, then create it
//			if(!file.exists()){
      file.delete();
      file.createNewFile();
//			}

      //true = append file
      FileWriter fileWritter = new FileWriter(file.getName(), true);
      fileWritter.write(content);
      fileWritter.close();

//			System.out.println("Done");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    System.out.printf("请输入文件名(如果不输入默认1.xml)：");
    //创建输入对象
    Scanner sc = new Scanner(System.in);
    //获取用户输入的字符串
    String str = "1.xml";
    str = sc.nextLine();
    if (str.equals("")) {
      str = "1.xml";
    }
    String fileName = str.split("\\.")[0] + ".json";
    //1.创建DocumentBuilderFactory对象
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //2.创建DocumentBuilder对象
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document d = builder.parse(str);
      NodeList sList = d.getElementsByTagName("fieldInfo");
      //element(sList);
      // node(sList);
      toJson(fileName, sList);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


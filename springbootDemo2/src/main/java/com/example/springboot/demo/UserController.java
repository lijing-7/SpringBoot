package com.example.springboot.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.bson.Document;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@EnableAutoConfiguration
public class UserController {

    @ResponseBody
    @RequestMapping("/hello1")
    //获取mangodb表的一个字段一列的内容
    public void hello() {
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection("GOODS_CATEGORY_CODE");
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //查找集合中的所有文档
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        List<Object> list = new ArrayList<>();
        while (cursor.hasNext()) {
            String jsonString = JSON.toJSONString(cursor.next());
            JSONObject jsonObject = JSON.parseObject(jsonString);
            list.add(jsonObject.get("GATEGORY"));
        }
        Set<Object> set = new HashSet<>();
        set.addAll(list);
        for (Object sets : set) {
            System.out.println(sets);
        }
    }

    //插入一个文档
    @ResponseBody
    @RequestMapping("/sss")
    public void insertOneTest() {
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection("T_BAS_Bxxxx_GOODS_PART_KEYBOARD");
        String goods = null;
        String type = null;
        //要插入的数据
        Document document = new Document("CATEGORY", "Bxxxx")
                .append("GOODS_SPEC", goods)
                .append("TYPE", type);
        //插入一个文档
        collection.insertOne(document);
    }


    //读取excel表中数据，保存到MangoDB数据库
    @ResponseBody
    @RequestMapping("/1234")
    public static void ReadExcel() throws Exception {
        //获取数据库连接对象
        MongoDatabase mongoDatabase = MongoDBUtil.getConnect();
        //获取集合
        MongoCollection<Document> collection = MongoDBUtil.getConnect().getCollection("T_BAS_Bxxxx_GOODS_PART_BEDDING");
        //用流的方式先读取到你想要的excel的文件
        FileInputStream fis = new FileInputStream(new File("D:\\sijiantao.xls"));
        POIFSFileSystem fs = new POIFSFileSystem(fis);
        HSSFWorkbook hs = new HSSFWorkbook(fs);
        System.out.println("sheet总数：" + hs.getNumberOfSheets());
        for (int i = 0; i < hs.getNumberOfSheets(); i++) {
            HSSFSheet sheet = hs.getSheetAt(i);
            List<String> head = new ArrayList<>();
            head.add("GOODS_SPEC");
            head.add("TYPE");
            List<Document> docs = new ArrayList<Document>();
            //获取第一行
            int firstrow = sheet.getFirstRowNum();
            //获取最后一行
            int lastrow = sheet.getLastRowNum();
            for (int r = firstrow; r <=lastrow; r++) {
                //获取哪一行r
                Row row = sheet.getRow(r);
                if (!"".equals(row) && row != null) {
                    List<Object> list = new ArrayList<>();
                    //获取这一行的第一列
                    int firstcell = row.getFirstCellNum();
                    //获取这一行的最后一列
                    int lastcell = row.getLastCellNum();
                    if (lastcell != 1) {
                        for (int j = firstcell; j <lastcell; j++) {
                            //获取第j列
                            Cell cell = row.getCell(j);
                            HSSFCell cellinfo = sheet.getRow(r).getCell(j);
                            list.add(cellinfo.toString());
                        }
                        // 插入到Mongo中
                        Document document = new Document();
                        document.append("CATEGORY", "Bxxx");
                        document.append("GOODS_SPEC", list.get(0));
                        document.append("TYPE", list.get(1));
                        docs.add(document);
                    }
                }

            }
            collection.insertMany(docs);
        }
        fis.close();

    }


    //读取Excel表中内容，进行判断筛选后将内容按行写入re文件里
    @ResponseBody
    @RequestMapping("/456")
    public static void Data() throws IOException {
        FileInputStream fis = new FileInputStream(new File("D:\\ups_zol.xls"));
//        File file1=new File("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\re");
        BufferedWriter writer=new BufferedWriter(new FileWriter("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\re"));
        POIFSFileSystem fs = new POIFSFileSystem(fis);
        HSSFWorkbook hs = new HSSFWorkbook(fs);
        System.out.println("sheet总数：" + hs.getNumberOfSheets());
//        for (int i=0;i<hs.getNumberOfSheets();i++){
        HSSFSheet sheet = hs.getSheetAt(2);
        //获取第一行
        int firstrow = sheet.getFirstRowNum();
        //获取最后一行
        int lastrow = sheet.getLastRowNum();
        for (int r = firstrow; r <= lastrow; r++) {
            Row row = sheet.getRow(r);
            if (!"".equals(row) && row != null) {
                //获取这一行的第一列
                int firstcell = row.getFirstCellNum();
                //获取这一行的最后一列
                int lastcell = row.getLastCellNum();
                List<Object> list = new ArrayList<>();
                for (int j = firstcell; j < lastcell; j++) {
                    //获取第j列
                    Cell cell = row.getCell(j);
                    HSSFCell cellinfo = sheet.getRow(r).getCell(j);
                    if (!"".equals(cellinfo) && cellinfo != null) {
//                                String cellToStr=cellinfo.toString();
                        String cellToStr = cellinfo.getStringCellValue();
                        List<String> b = new ArrayList();
                        if (cellToStr.contains("±")){
                            String string=cellToStr.substring(0,cellToStr.indexOf("±"));
                            System.out.println("±拆分后的字符串");
                            System.out.println(string);

                        }

//                                if (cellToStr.contains(" ")||cellToStr.contains(".")||cellToStr.contains(",")||cellToStr.contains("+"))

//                                String str=cellToStr.substring(0,3);
//                                System.out.println(str);
                        if (cellToStr.contains(",")) {
                            String[] arr = cellToStr.split(",");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains("：")) {
                            String[] arr = cellToStr.split("：");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains(":")) {
                            String[] arr = cellToStr.split(":");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains("，")) {
                            String[] arr = cellToStr.split("，");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains(";")) {
                            String[] arr = cellToStr.split(";");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains("；")) {
                            String[] arr = cellToStr.split("；");
//                                    List<String>  b = new ArrayList();
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        } else if (cellToStr.contains("、")) {
                            String[] arr = cellToStr.split("、");
//                                    List<String>  b = new ArrayList();
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        }
                        else if (cellToStr.contains("+")) {
                            String[] arr = cellToStr.split("\\+");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        }
                        else if (cellToStr.contains("/")) {
                            String[] arr = cellToStr.split("/");
                            for (int i = 0; i < arr.length; i++) {
                                b.add(arr[i]);
                            }
                        }
                        for (String bs : b) {
                            System.out.println(bs);
                            writer.write(bs+"\r\n");
                        }
                        writer.close();
                        list.add(cellToStr);
                        System.out.println(list);
                    }
                }
            }
        }
    }

//        }


    //读取文本，按行输出
    @RequestMapping("789")
    @ResponseBody
    public static void toArrayByFileReader() {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\haha");
            BufferedWriter writer=new BufferedWriter(new FileWriter("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\re"));
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            String str;
            List<String> c = new ArrayList();
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
               //截取括号之前的字符串，去重
//                if (str.contains("（")){
//                    String t=str.substring(0,str.indexOf("（"));
//                    str=t;
//                }
//                String A=str+"键";
//                str=A;
//                if (str.contains("电池")){
//                    arrayList.add(str);
//                }
//                if (str.endsWith("电池")){
//                    arrayList.add(str);
//                }
                if (str.contains("，")){
                    String[] arr2=str.split("，");
                    for (int i=0;i<arr2.length;i++){
                        c.add(arr2[i]);
                    }
                }
                else if (str.contains("；")){
                    String[] arr2=str.split("；");
                    for (int i=0;i<arr2.length;i++){
                        c.add(arr2[i]);
                    }
                }
                else if (str.contains("±")){
                    String string=str.substring(0,str.indexOf("±"));
                    str=string;
//                    arrayList.add(str);
                }
//               else if (str.contains("/")){
//                    String[] arr1 = str.split("/");
//                    for (int i = 0; i < arr1.length; i++) {
//                        c.add(arr1[i]);
//                    }
//                }

                arrayList.add(str);
            }
//            for (String d:c){
////                System.out.println("这是拆分后分行显示的字符串");
//                System.out.println(d);
//                writer.write(d+"\r\n");
//            }

            System.out.println(arrayList);

            List<String> listTemp=new ArrayList<String>(arrayList);
            for (int i = 0;i < listTemp.size();i++){
                if (listTemp.get(i).contains("套装")){
                    listTemp.remove(i);
                }
                else if (listTemp.get(i).contains("鼠标")){
                    listTemp.remove(i);
                }
                else if (listTemp.get(i).contains("鼠标套装")){
                    listTemp.remove(i);
                }
//                else if (listTemp.get(i).contains("键")){
//                    listTemp.remove(i);
//                }

//                System.out.println(listTemp.get(i));
            }
            for (String str1:listTemp){
                System.out.println(str1);
                writer.write(str1+"\r\n");
            }

            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    }







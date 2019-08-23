package com.example.springboot.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
public class TextController {
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    @RequestMapping("/hei")
    public static  void  isChinese(){
        // 使用ArrayList来存储每行读取到的字符串
        List<String> arrayList = new ArrayList<>();
        try {
            File file=new File("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\haha");
//            File file1 =new File("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\re");
            BufferedWriter writer=new BufferedWriter(new FileWriter("D:\\Documents\\Rbps\\springbootDemo2\\src\\main\\java\\com\\example\\springboot\\demo\\re"));
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(inputReader);
            String str;
            List<String>  strr= new ArrayList();
            // 按行读取字符串
//            &&!"".equals(str = bf.readLine())
            while ((str = bf.readLine())!= null) {
                if (str.contains("/")&&str.contains("g")){
                    continue;

                }
                else if (str.endsWith("/")){
                    String a=str.substring(0,str.indexOf("/"));
                    str=a;
                }
                else if (str.endsWith("g")){
                    continue;
                }
                else if (str.contains("（")){
                    String a=str.substring(0,str.indexOf("（"));
                    str=a;
                }
                else if (str.contains("(")){
                    String a=str.substring(0,str.indexOf("("));
                    str=a;
                }
//                else {
//                    continue;
//                }
                arrayList.add(str);


//                if (str.contains("*")||str.contains("英寸")){
//                   continue;
//                }
//                else{
//                Pattern pat = Pattern.compile(REGEX_CHINESE);
//                // 去除中文
//                Matcher mat = pat.matcher(str);
//               arrayList.add(mat.replaceAll("")) ;
//
//                }
//                拆分后的字符串都打印出来
//                if (str.contains("K")){
//                    String[] a1=str.split("K");
//                    for (int i=0;i<a1.length;i++){
//                        arrayList.add(a1[i]);
//                    }
//                }else{
//                    arrayList.add(str);
//                }

                //截取包含某字符的字符串
//                else{
//                    if (str.contains("＜")||str.contains("×")||str.contains("％")||str.contains("VAC/")){
//                        continue;
//                    }
//                    else if (str.contains("+")||str.contains("*")){
//                        continue;
//                    }
//                   else if (str.contains("±")){
//                        String s=str.substring(0,str.indexOf("±"));
//                        str=s;
//                    }
//                   else if (str.contains("@")){
//                        String s1=str.substring(0,str.indexOf("@"));
//                        str=s1;
//                    }
////                    else if(str.length()>11){
////                        continue;
////                    }
//
//                    String a=str+"枚";
//                    str=a;
//                    arrayList.add(str);
//                }
//                arrayList.add(str);

            }

            //读取到的字符串按行存入haha文件
            for (String str1:arrayList){
                System.out.println(str1);
                //未完成
               writer.write(str1+"\r\n");

            }
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }


    }


}

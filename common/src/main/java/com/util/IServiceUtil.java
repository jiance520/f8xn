package com.util;

import java.io.*;

public class IServiceUtil {
    //不能放外部，
    public static void read(String basepath) throws IOException {
        File basefile = new File(basepath);
        File newfile = new File(basepath.substring(0,basepath.length()-4) + "\\service");
        newfile.mkdirs();
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringBuffer stringBuffer = null;
        String[] strArr = basefile.list();/*返回所有的文件名*/
//        File[] fileList = file.listFiles();//返回所有的文件对象    //getName()获取文件名
        for (int i = 0; i < strArr.length; i++) {
            File childFile = new File(basefile.getCanonicalPath() + "\\" + strArr[i]);
            if (childFile.isFile()) {//判断是否是文件
                System.out.println("-----这是文件:" + strArr[i]);
//                读入文件
                fileReader = new FileReader(childFile);
                bufferedReader = new BufferedReader(fileReader);
                stringBuffer = new StringBuffer();
//          String str = bufferedReader.readLine();/*使用行读取，后面的操作也必须全以行来操作*/
                int unicode = bufferedReader.read();//读入硬盘文件里的一个字符，返回unicode编码。读取到末尾返回-1。
                while (unicode != -1) {
                    stringBuffer.append((char) unicode);
//            System.out.println(stringBuffer);//可以用%c输出数字对应的字符(System.out.printf("c%",num))
                    unicode = bufferedReader.read();//读取文件的下一个字符。
                }
////          ---------------------------这里执行相关的替换----------------------------
                String newObject = strArr[i].substring(0,strArr[i].length()-11);
                File newChildFile = null;

                stringBuffer = replace(stringBuffer,newObject);
//            ---------------------------这里执行相关的替换----------------------------
//                   输出文件
                newChildFile = new File(basepath.substring(0,basepath.length()-4) + "\\service\\" + "I"+newObject+"Service.java");
                System.out.println("-----newChildFile:"+newChildFile.toString());
                fileWriter = new FileWriter(newChildFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(stringBuffer.toString());//自带创建文件功能，但不能创建文件夹

                bufferedWriter.flush();
                fileWriter.flush();
            }
            if (childFile.isDirectory()) {
                System.out.println("-----这是文件夹:" + strArr[i]);
            }
        }
        bufferedWriter.close();
        fileWriter.close();
        bufferedReader.close();
        fileReader.close();
    }
    public static String captureName(String name){
        return name.substring(0,1).toUpperCase()+name.substring(1);
    }
    public static StringBuffer replace(StringBuffer stringBuffer,String newObject){
        newObject = captureName(newObject);
        String str = stringBuffer.toString();
        String str1 = str.replaceAll(newObject+"Mapper","I"+newObject+"Service");
        String str2 = str1.replaceAll("guo.dao","guo.service");
        return new StringBuffer(str2);
    }
    public static void main(String[] args) throws IOException {
        IServiceUtil.read("D:\\workspace\\idea\\guo\\zufang\\src\\main\\java\\guo\\dao");
    }
}

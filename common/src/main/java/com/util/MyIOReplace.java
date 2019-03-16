package com.util;
import java.io.*;

public class MyIOReplace {
    //不能放外部，之前写入的文件可能会为空
    public static void read(String basepath) throws IOException {
        if(basepath==null||"".equals(basepath)){
            basepath = "D:\\workspace\\idea\\hshop\\hshop\\src\\main\\java\\com\\test";
        }
        File basefile = new File(basepath);
        File newfile = new File(basepath + "\\new");
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
////          System.out.println("-----stringBuffer:\n"+stringBuffer);
////          ---------------------------这里执行相关的替换----------------------------
////          ---------------------------这里执行相关的替换----------------------------
////          输出文件
                File newChildFile = new File(basefile.getCanonicalPath() + "\\new\\" + strArr[i]);
                fileWriter = new FileWriter(newChildFile);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(stringBuffer.toString());//自带创建文件功能，但不能创建文件夹
            }
            if (childFile.isDirectory()) {
                System.out.println("-----这是文件夹:" + strArr[i]);
            }
            bufferedWriter.flush();
            fileWriter.flush();
        }
        bufferedWriter.close();
        fileWriter.close();
        bufferedReader.close();
        fileReader.close();
    }
    public static void main(String[] args) throws IOException {
    }
}

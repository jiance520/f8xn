package com.utils;

import java.util.regex.Pattern;

public class TestPattern {
    public static void main(String[] args) {
//        Boolean isTwoNum = Pattern.matches("a(.*|\n)*","a\na\n\n");
//        Boolean isTwoNum = Pattern.matches("java[\\s\\w-\\.:/\n]*-overwrite","java -jar D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/mybatis-generator-core-1.3.2.jar-overwrite");
        String oldStr = "#allow 192.168.0.0/16";
        String regEx = "#allow [0-9\\./]*";
        String newStr = "#allow 10/8";
        Boolean flag = Pattern.matches(regEx,oldStr);
        if(flag){
            StringBuffer stringBufferRun = new StringBuffer(oldStr);
            String str = stringBufferRun.toString();
            str = str.replaceAll(oldStr,newStr);
            System.out.println("-----str:"+str);
        }
        else {
            System.out.println("-----没有匹配到");
        }
    }
}

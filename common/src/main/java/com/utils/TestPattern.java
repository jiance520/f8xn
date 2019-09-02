package com.utils;

import java.util.regex.Pattern;

public class TestPattern {
    public static void main(String[] args) {
//        Boolean isTwoNum = Pattern.matches("a(.*|\n)*","a\na\n\n");
//        Boolean isTwoNum = Pattern.matches("java[\\s\\w-\\.:/\n]*-overwrite","java -jar D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/mybatis-generator-core-1.3.2.jar-overwrite");
        Boolean isTwoNum = Pattern.matches("java[\\s\\w-\\.:/]*-overwrite(.*|\n)*","java -jar D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/mybatis-generator-core-1.3.2.jar -configfile D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/generator.xml -overwrite\n" +
                "exit\n" +
                "exit\n" +
                "exit");
        System.out.println("-----isTwoNum:"+isTwoNum);
        StringBuffer stringBufferRun = new StringBuffer("java -jar D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/mybatis-generator-core-1.3.2.jar -configfile D:/workspace/idea/guo/milmajordb2012127/src/main/resources/mybatisGenerator/generator.xml -overwrite\n" +
                "exit\n" +
                "exit");
        String runBatStr = stringBufferRun.toString();
        runBatStr = runBatStr.replaceAll("java[\\s\\w-\\.:/\n]*-overwrite[\\sa-z\n]*","java -jar -overwrite\nexit");
        System.out.println("-----runBatStr:"+runBatStr);
    }
}

package com.util;

import java.util.ArrayList;
import java.util.Arrays;

//使用方法，打开右边的datatabse连接Oracle或mysql数据库,shift全选表，右键copy,在idea中,
// 粘贴在String tableStr ="这里粘贴"，
//输入数据库用户名。tableObj = tableObj.replaceAll("schema=\".+\" ", "schema=\"这里输入\" ");
//执行，复制输出内容到mybatis工具的generator里面。

public class MyUtilTable {
    public static void main(String[] args) {
        String tableStr = "zufang.favorites\n" +
                "zufang.house\n" +
                "zufang.userinfo\n" +
                "zufang.yanshou\n" +
                "zufang.yuyue\n" +
                "zufang.zudan";
//                "QSHOP.VIP";
        String tableObj ="       <table schema=\"user\" tableName=\"table\"\n" +
                "            domainObjectName=\"Table\" enableCountByExample=\"false\"\n" +
                "            enableUpdateByExample=\"false\" enableDeleteByExample=\"false\"  \n" +
                "            enableSelectByExample=\"false\" selectByExampleQueryId=\"false\">\n" +
                "       </table>  ";
        String dataStr = null;
        if(tableStr.contains(".")){
            dataStr = tableStr.substring(0,tableStr.indexOf("."));
            tableStr = tableStr.replaceAll(dataStr+".","");
        }
        tableObj = tableObj.replaceAll("schema=\"\\S+\" ", "schema=\""+dataStr+"\" ");
//        tableObj = tableObj.replaceAll("schema=\"\\S+\" ", "");

        tableStr = tableStr.replace(" ", "");//去空格
        tableStr = tableStr.toLowerCase();//全转小写
        String[] tableArr = tableStr.split("\n");
        System.out.println("-----tableArr:"+ Arrays.toString(tableArr));
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <tableArr.length ; i++) {
//            tableArr[i] = tableArr[i].substring(tableArr[i].indexOf(".")+1);//截取后面的部分，再放入替换。
            tableObj = tableObj.replaceAll("tableName=\"\\S+\"", "tableName=\""+tableArr[i]+"\"");

            tableArr[i] = tableArr[i].substring(0,1).toUpperCase()+tableArr[i].substring(1);//截取第一个大写，再拼接，再替换
            tableObj = tableObj.replaceAll("domainObjectName=\"\\S+\" ", "domainObjectName=\""+tableArr[i]+"\" ");
            System.out.println(tableObj);
        }
    }
}


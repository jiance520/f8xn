//package com.test;
//
//import com.App;
//import com.util.JdbcUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.util.HashMap;
//import java.util.List;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = App.class)
//@WebAppConfiguration
//public class OutPG {
//    @Test
//    @Transactional
//    @Rollback
//    public void query() throws IOException {
//        Connection connection = JdbcUtil.getConn();
////        shp矢量数据读取位置
//        List<HashMap> shpMapList = JdbcUtil.exectueQuery(connection,"select gid,name,st_astext(geom),kind from point");
////        String basepath = "D:";
////        basefile.mkdirs();//创建文件夹目录
//        File file = new File("D:\\shp.txt");
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("name表坐标对应的点线面,point表示坐标\n");
//        for (int i = 0; i < shpMapList.size(); i++) {
//            HashMap<String, Object> shpMap = shpMapList.get(i);
//            String point = (String)shpMap.get("st_astext");
//            String name = (String)shpMap.get("name");
//            stringBuffer.append("name:"+name+",point:"+point+"\n");
//        }
//        FileWriter fileWriter = new FileWriter(file);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//        bufferedWriter.write(stringBuffer.toString());
//        bufferedWriter.flush();
//        fileWriter.flush();
////        adf栅格数据读取高程和像素位置
//        HashMap<String, Object> adfMetaMap = JdbcUtil.queryOne(connection,"select rid,(foo.md).* from (select rid,st_metadata(rast) as md from testadf where rid=?) as foo", 1);
//        System.out.println("-----adfMetaMap:"+adfMetaMap);
//        double upperleftx = (double)adfMetaMap.get("upperleftx");
//        double upperlefty = (double)adfMetaMap.get("upperlefty");
//        int width = (int)adfMetaMap.get("width");
//        int height = (int)adfMetaMap.get("height");
//        double scalex = (double)adfMetaMap.get("scalex");
//        double scaley = (double)adfMetaMap.get("scaley");
//        file = new File("D:\\adf.txt");
//        stringBuffer = new StringBuffer();
//        stringBuffer.append("upperleftx表示栅格x像素坐标,upperlefty表示栅格y像素坐标,histogram表示坐标对应的高程\n");
//        HashMap<String, Object> adfHeightMap = JdbcUtil.queryOne(connection,"select rid,st_value(rast,1,?,?) as r_val from testadf", 1,1);
//        double histogram = 1191.9188232421875;
//        double flag = 1191.9188232421875;
//        for (int i = 1; i <=height; i++) {
//            if(i==1){
//                upperlefty = upperlefty;
//            }else {
//                upperlefty = upperlefty+scaley;
//            }
//            for (int j = 1; j <=width; j++) {
//                if(j==1){
//                    upperleftx = upperleftx;
//                }else {
//                    upperleftx = upperleftx+scalex;
//                }
//                adfHeightMap = JdbcUtil.queryOne(connection,"select rid,st_value(rast,1,?,?) as r_val from testadf", i,j);
//                if(adfHeightMap.get("r_val")!=null){
//                    histogram = (double)adfHeightMap.get("r_val");
//                    flag = histogram;
//                }
//                else{
//                    histogram = flag;
//                }
//                stringBuffer.append("["+i+"]["+j+"]"+"upperleftx:"+upperleftx+",upperlefty:"+upperlefty+",histogram:"+histogram+"\n");
//            }
//        }
//        fileWriter = new FileWriter(file);
//        bufferedWriter = new BufferedWriter(fileWriter);
//        bufferedWriter.write(stringBuffer.toString());
//        bufferedWriter.close();
//        fileWriter.close();
//        JdbcUtil.closeAll();
//    }
//}

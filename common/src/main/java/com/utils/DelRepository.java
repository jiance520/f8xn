package com.utils;


import java.io.File;
//删除空文件夹，无.jar的文件夹，没有更新完的lastUpdated文件,m2e-lastUpdated.properties文件
public class DelRepository {

    // 仓库根目录
//    static String path = "C:\\Users\\Administrator\\.m2\\repository";
    static String path = "D:\\OneDrive\\repository";

    public static void main(String[] args) {

        File file = new File(path);

        File[] sonFiles = file.listFiles();
        if (sonFiles != null && sonFiles.length > 0) {
            for (File sonFile : sonFiles) {
                haveJar(sonFile);
            }
        }

    }

    public static boolean haveJar(File file) {
        boolean flagJar = false;
        File[] sonFiles = file.listFiles();
        if (sonFiles != null && sonFiles.length > 0) {
            // 判断是否有*jar 是否是有文件夹
            for (File sonFile : sonFiles) {
                if (sonFile.getName().endsWith(".jar")) {
                    flagJar = true;
                }
                if (sonFile.isDirectory()) {
                    boolean nextFlagJar = haveJar(sonFile);
                    if (nextFlagJar) {
                        flagJar = true;
                    }
                }
                if(sonFile.getName().endsWith("lastUpdated")){
                    sonFile.delete();
                    System.out.println("-----lastUpdated:"+sonFile);
                }
                if(sonFile.getName().equals("m2e-lastUpdated.properties")){
//                    sonFile.delete();
                    System.out.println("-----m2e-lastUpdated.properties:"+sonFile);
                }
            }
        }
        if (!flagJar) {
            delete(file);
        }
        if(file.getName().endsWith("lastUpdated")){
            file.delete();
            System.out.println("-----lastUpdated:"+file);
        }
        if(file.getName().equals("m2e-lastUpdated.properties")){
//            file.delete();
            System.out.println("-----m2e-lastUpdated.properties:"+file);
        }
        return flagJar;
    }

    public static void delete(File file) {
        File[] sonFiles = file.listFiles();
        if (sonFiles != null && sonFiles.length > 0) {
            for (File sonFile : sonFiles) {
                if (sonFile.isDirectory()) {
                    delete(sonFile);
                }
//                sonFile.delete();
                System.out.println("-----删除文件或空文件夹:"+file);
            }
        } else {
//            file.delete();
            System.out.println("-----else删除空文件夹:"+file);
        }
    }
}

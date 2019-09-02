package com.utils;

import java.io.File;
import java.util.Date;

public class FoldersDeep {
    public static int digui(String path,File file,File[] files,int num,int backInt){
        if(files.length!=0){
            for (int k = 0; k < files.length; k++) {
                String folderName = files[k].getName();
                String sonPath=path+"\\"+folderName;
                File sonFile = new File(sonPath);
                if(files[k].isDirectory()){
                    num++;
                    File[] sonFiles = sonFile.listFiles();
                    //判断是否文件夹为空
                    if(sonFiles.length==0){
                        if(num>backInt){
                            backInt=num;
                            System.out.println("-----最后一层子文件夹无文件backInt:"+backInt);
                            System.out.println("-----最后一层子文件夹无文件path:"+sonPath);
                        }
                        num--;//在最后一层让num值还原，进行下一次兄弟文件夹内的遍历。
                    }
                    else {
                        boolean flag = false;
                        for (int l = 0; l < sonFiles.length; l++) {
                            if(sonFiles[l].isDirectory()){
                                flag=true;
                                break;
                            }
                        }
                        if(flag==false){
                            if(num>=backInt){
                                backInt=num;
                                System.out.println("-----最后一层子文件夹有文件backInt:"+backInt);
                                System.out.println("-----最后一层子文件夹有文件path:"+sonPath);
                            }
                            num--;//在最后一层让num值还原，进行下一次兄弟文件夹内的遍历。
                        }
                        //如果文件夹不为空，且内部还有文件夹，则继续循环
                        else {
                            if(num>backInt){
                                backInt=num;
                            }//if可有可无
                            backInt=digui(sonPath, sonFile, sonFiles, num,backInt);
                            num--;
                        }
                    }
                }
                else {
                    if(num>backInt){
                        backInt=num;
                    }
                    //如果是文件，不做处理，判断下一个兄弟文件类型
                    //System.out.println("-----不是文件夹");
                }
            }
        }
        else {
            //可有可无的代码，在调用自己前已做判断，只用于第一次
            if(num>backInt){
                backInt=num;
                System.out.println("-----最后一层文件夹无文件backInt:"+backInt);
                System.out.println("-----最后一层文件夹无文件path:"+path);
            }
        }
        return backInt;
    }
    public static Long lastModified(String path,File file,File[] files,Long lastModified){
        Long newModified = file.lastModified();
        if(files.length!=0){
            for (int k = 0; k < files.length; k++) {
                String folderName = files[k].getName();
                String sonPath=path+"\\"+folderName;
                File sonFile = new File(sonPath);
                newModified = sonFile.lastModified();
                if(files[k].isDirectory()){
                    File[] sonFiles = sonFile.listFiles();
                    //判断是否文件夹为空
                    if(sonFiles.length==0){
                        if(lastModified<newModified){
                            lastModified=newModified;
                            System.out.println("-----最后一层子文件夹无文件更新时间lastModified1:"+lastModified);
                            System.out.println("-----最后一层子文件夹无文件path1:"+sonPath);
                        }
                    }
                    else {
                        boolean flag = false;
                        for (int l = 0; l < sonFiles.length; l++) {
                            if(sonFiles[l].isDirectory()){
                                flag=true;
                                break;
                            }
                        }
                        if(flag==false){//如果没有文件夹
                            if(lastModified<newModified){
                                lastModified=newModified;
                                System.out.println("-----最后一层子文件夹有文件lastModified2:"+lastModified);
                                System.out.println("-----最后一层子文件夹有文件path2:"+sonPath);
                            }
                        }
                        //如果文件夹不为空，且内部还有文件夹，则继续循环
                        else {
                            if(lastModified<newModified){
                                lastModified=newModified;
                                System.out.println("-----lastModified3:"+lastModified);
                                System.out.println("-----path3:"+sonPath);
                            }//if可有可无
                            lastModified=lastModified(sonPath, sonFile, sonFiles,lastModified);
                        }
                    }
                }
                else {
                    if(lastModified<newModified){
                        lastModified=newModified;
                        System.out.println("-----lastModified4:"+lastModified);
                        System.out.println("-----path4:"+sonPath);
                    }
                    //如果是文件，不做处理，判断下一个兄弟文件类型
                    //System.out.println("-----不是文件夹");
                }
            }
        }
        else {
            //可有可无的代码，在调用自己前已做判断，只用于第一次
            if(lastModified<newModified){
                lastModified=newModified;
                System.out.println("-----最后一层文件夹无文件lastModified5:"+lastModified);
                System.out.println("-----最后一层文件夹无文件path5:"+path);
            }
        }
        return lastModified;
    }
    public static void main(String[] args) {
        String path = "I:\\\\yum\\\\https@repos.fedorapeople.org";
//        String path = "I:\\yum\\https@repos.fedorapeople.org";
        File file = new File(path);
        File[] files = file.listFiles();
        int num = 0;
        int backInt = 0;
        int backInt2=digui(path,file,files,num,backInt);
        System.out.println("-----最终文件夹层数是backInt2:"+backInt2);//D:\OneDrive\repository\org\eclipse\jdt\core\compiler\ecj\4.2.1

//        String path = "I:\\yum\\https@repos.fedorapeople.org";
//        File file = new File(path);
//        File[] files = file.listFiles();
//        Long lastModified = file.lastModified();
//        //Date可以得到1970年01月1日0点零分以来到date的毫秒数=lastModified=1566137392082
//        Long lastModified2=lastModified(path,file,files,lastModified);
//        System.out.println("-----最终文件夹层数是lastModified2:"+lastModified2);
        //50万文件需要5分钟以上。

    }
}

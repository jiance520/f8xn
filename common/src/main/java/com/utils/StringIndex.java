package com.utils;

import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringIndex {
    //判断单个字母是否大小写boolean bool = Character.isLowerCase('c');
    //首字母小写
    public static String upFirstWord(String tablename){
        return tablename.substring(0,1).toUpperCase()+tablename.substring(1);
    }
    //首字母大写
    public static String lowerFirstWord(String tablename){
        return tablename.substring(0,1).toLowerCase()+tablename.substring(1);
    }
    //判断str是否符合正则表达式Pattern.matcher("regExp","str");
    //判断字符串是否包含符合正则表达式的内容，如是否包含大小写数字
    public static boolean isContain(String str,String regExp){
        return Pattern.compile(regExp).matcher(str).find();
    }
    //从左往右，查找第从1开始的第n个字符串chStr在str中的位置
    public static int indexOf(String str,String chStr,int n){
        if(n<=0){
            System.out.println("请输入大于0的整数参数");
            return -1;
        }
        else {
            if(isContain(str,chStr)){
                int index = str.indexOf(chStr);
                int num = 0;
                int oldIndex = index;
                for (int i = 1; i <=n; i++) {
                    if(index==-1){
                        break;
                    }
                    else {
                        num=i;
                        oldIndex = index;
                        index=str.indexOf(chStr,index+1);
                    }
                }
                if(num==n){
                    System.out.println("从左往右第"+n+"次出现的下标的位置是:"+oldIndex);
                }
                else {
                    System.out.println("从左往右少于"+n+"次，实际"+num+"次，最大下标的位置是:"+oldIndex);
                }
                //方法二
                //Matcher matcher = Pattern.compile("o").matcher("要查找的字符串hello world");
                //int x=0;
                //while (matcher.find()){
                //    x++;
                //    if(x==2){
                //        break;
                //    }
                //}
                //System.out.println("-----test:"+matcher.end());
                return oldIndex;
            }
            else {
                System.out.println("不存在"+chStr+",没有匹配到");
                return -1;
            }
        }
    }
    //从右往左，查找第从1开始的第n个字符串chStr在str中的位置
    public static int lastIndexOf(String str, String chStr, int n){
        if(n<=0){
            System.out.println("请输入大于0的整数参数");
            return -1;
        }
        else {
            if(isContain(str,chStr)){
                int index = str.lastIndexOf(chStr);
                int num = 0;
                int oldIndex = index;
                for (int i = 1; i <=n; i++) {
                    if(index==-1){
                        break;
                    }
                    else {
                        num=i;
                        oldIndex = index;
                        index=str.lastIndexOf(chStr,index-1);
                    }
                }
                if(num==n){
                    System.out.println("从右往左第"+n+"次出现的下标的位置是:"+oldIndex);
                }
                else {
                    System.out.println("从右往左少于"+n+"次，实际"+num+"次，最大下标的位置是:"+oldIndex);
                }
                return oldIndex;
            }
            else {
                System.out.println("不存在"+chStr+",没有匹配到");
                return -1;
            }
        }
    }
    //从左往右，截取第n1个chStr="*"到第n2个"*"之间的字符串*str*
    public static String substringIndexOf(String str,String chStr,int n1,int n2){
        int indexStart = indexOf(str,chStr,n1);
        int indexEnd = indexOf(str,chStr,n2);
        str=str.substring(indexStart+1,indexEnd);
        return str;
    }
    //从右往左，截取第n1个chStr="*"到第n2个"*"之间的字符串*str*
    public static String substringLastIndexOf(String str,String chStr,int n1,int n2){
        int indexEnd = lastIndexOf(str,chStr,n1);
        int indexStart = lastIndexOf(str,chStr,n2);
        str=str.substring(indexStart+1,indexEnd);
        return str;
    }
    //是否包括，使用str.matches("[是否包含boolean]")
    //matcher.lookingAt()返回boolean;matcher.matches()返回boolean;
    //matcher.find()，返回boolean,是否匹配到，
    //matcher.start()返回第一个匹配到的字符串下标的开始,起点;
    //matcher.end();返回匹配到的字符的结束下标。=matcher.start()+字符串str长度。
    //matcher.group();返回匹配到的字符内容，非下标,用于while，多次匹配。
    //matcher.appendTail(str),从匹配位置，将str加上，提是构造macther时使用入参StringBuffer，而不是String。
    //从左往右正向匹配,匹配到符合正则表达式的regExpStr第一个下标位置,
    public static int patternIndexOf(String str,String regExpStr){//=matcher.start()
        Pattern pattern = Pattern.compile(regExpStr);
        Matcher matcher = pattern.matcher(str);
        int index = -1;
        if (matcher.find()){
            index = matcher.start();
            System.out.println("从左往右正则匹配到的第一个字符串开始位置是:"+index);
        }
        else{
            System.out.println("从左往右正则匹配,没有匹配到:"+index);
        }
        return index;
    }
    //从右往左反向匹配,匹配到符合正则表达式的regExpStr第一个下标位置(不推荐，慢)
    public static int patternLastIndexOf(String str,String regExpStr){//=matcher.start()
        Pattern pattern = Pattern.compile(regExpStr);
        Matcher matcher = pattern.matcher(str);
        int index = -1;
        while (matcher.find()){
            index=matcher.start();
        }
        System.out.println("从右往左，正则匹配字符，第一个下标开始位置:"+index);
        return index;
    }
    //从左往右，匹配长字符串中第n个，符合正则表达式的短字符串，并返回该部分内容。
    public static String substringLeftRegExp(String str,String regExp,int n){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        int num = 0;
//        int indexStart = -1;
//        int indexEnd = -1;
        while (matcher.find()){
            num += 1;
            if(n==num){
//                indexStart = matcher.start();
//                indexEnd = matcher.end();
                str = matcher.group();
                //System.out.println("正则表达式正向匹配第"+n+"个,匹配到内容是:"+str);
                break;
            }
            else {
                //System.out.println("正则表达式正向匹配第"+n+"个没有匹配到内容");
                str = null;
            }
        }
        return str;
    }
    //从左往右，匹配长字符串中第n个，符合正则表达式的短字符串，并返回该部分右边的内容。
    public static String substringRegExpLeft(String str,String regExp,int n){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        int num = 0;
//        int indexStart = -1;
        int indexEnd = -1;
        while (matcher.find()){
            num += 1;
            if(n==num){
//                indexStart = matcher.start();
                indexEnd = matcher.end();
                str = str.substring(indexEnd);
                System.out.println("正则表达式正向匹配第"+n+"个,匹配截取右边的内容是:"+str);
                break;
            }
            else {
                System.out.println("正则表达式正向匹配第"+n+"个没有匹配到内容");
                str = null;
            }
        }
        return str;
    }
    //从右往左，匹配长字符串中第n个,符合正则表达式的短字符串，并返回该部分内容
    public static String substringRightRegExp(String str,String regExp,int n){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        int num = 0;
        int index = -1;
        Map<String,String> map = new HashMap();
        while (matcher.find()){
            num += 1;
            map.put(num+"",matcher.group());
        }
        if(!map.isEmpty()){
            str = map.get(n+"");
            if(str!=null&&!"".equals(str)){
                System.out.println("正则表达式反向匹配第"+n+"个,匹配到内容是:"+str);
            }
            else {
                System.out.println("正则表达式反向匹配第"+n+"个没有匹配到内容");
            }
        }
        return str;
    }
    //从右往左，匹配长字符串中第n个,符合正则表达式的短字符串，并返回该部分左边的内容
    public static String substringRegExpRight(String str,String regExp,int n){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        int indexStart = -1;
        int indexEnd = -1;
        int num = 0;
        Map<String,String> map = new HashMap();
        if(n<=0){
            System.out.println("请输入大于0的整数参数");
            return null;
        }
        else {
            while (matcher.find()){
                indexStart=matcher.start();
                //indexEnd=matcher.end();
                num += 1;
                map.put(num+"",indexStart+"");
            }
            if(!map.isEmpty()){
                String tempStr = map.get((num+1-n)+"");//下标
                if(tempStr!=null&&!"".equals(tempStr)){
                    str = str.substring(0,Integer.parseInt(tempStr));
                    System.out.println("正则表达式反向匹配第"+n+"个,匹配到左边的内容是:"+str);
                }
                else {
                    System.out.println("正则表达式反向匹配第"+n+"个没有匹配到内容");
                }
            }
            return str;
        }
    }
    //匹配符合正则表达式的内容，并返回匹配的所有下标位置，如果匹配有重叠的部分，则不能用此方法(用于表字段转实体属性时匹配下划线_)
    public static ArrayList arrInt(String mapAttrKey, String regExp){
        ArrayList arrayList = null;
        if(isContain(mapAttrKey,regExp)){
            arrayList = new ArrayList();
            int index = 0;
            int num = 0;
            int sum = index+num;
            int len = 0;
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(mapAttrKey);
            //找到位置，不能替换，只能截取
            //index index+len index+len+len
            while (matcher.find()){
                int newIndex = matcher.start();
                index = index+newIndex;
                num=num+len;//累计匹配的字符
                sum = num + index;
                arrayList.add(sum);
                String leftRegExp = substringLeftRegExp(mapAttrKey, regExp, 1);//返回正则匹配到的内容
                len = leftRegExp.length();
                mapAttrKey = mapAttrKey.substring(newIndex+len,mapAttrKey.length());//把第一次匹配到的内容去掉
                matcher=pattern.matcher(mapAttrKey);
            }
            System.out.println("-----arrayList_:"+arrayList);
        }
        return arrayList;
    }
    public static void main(String[] args) {
        StringIndex stringIndex = new StringIndex();
//        String str = "D:/workspace/idea/guo/zufang/src/main/java/guo/utils/";
        String str = "D:/workspace/idea/guo/zufang/src/main/java/guo/dao/";
//        String str = "01/34567/9012345678/0123/56789/1234";
//        System.out.println("-----test:"+str.substring(stringIndex.indexOf(str,"/",9)+1,stringIndex.indexOf(str,"/",10)));
//        System.out.println("-----stringindex:"+stringIndex.lastIndexOf(str,"/",3));
//        stringIndex.substringRegExpRight(str,"/",2);
        arrInt("__w_flat_id", "_[a-z]");
    }
}

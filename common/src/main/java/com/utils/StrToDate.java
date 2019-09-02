package com.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class StrToDate {
    //方法返回null，可能不是时间类型。
    //使用时，要换算为24小时制。年默认不允许简写19，不包括公元前1000-9999，时区统一为美国中部时间CST,能判断大部分时间，但不做全部解析。
    //yearHead只对不带时区的年进行简写年还原,默认认为""。
    //对于23:59，只做HHmm判断，不做mmss判断。
    //只算公历不算农历
    public static Date dateFormat(String dateStr,String yearHead,Boolean hour24or12){
        if(dateStr.contains("星期")){
            if(dateStr.contains("星期一")){
                dateStr=dateStr.replaceAll("星期一","Mon");
            }
            if(dateStr.contains("星期二")){
                dateStr=dateStr.replaceAll("星期二","Tue");
            }

            if(dateStr.contains("星期三")){
                dateStr=dateStr.replaceAll("星期三","Web");
            }
            if(dateStr.contains("星期四")){
                dateStr=dateStr.replaceAll("星期四","Thu");
            }
            if(dateStr.contains("星期五")){
                dateStr=dateStr.replaceAll("星期五","Fri");
            }
            if(dateStr.contains("星期六")){
                dateStr=dateStr.replaceAll("星期六","Sat");
            }
            if(dateStr.contains("星期日")){
                dateStr=dateStr.replaceAll("星期日","Sun");
            }
        }
        if(dateStr.contains("点")){
            dateStr=dateStr.replaceAll("点","时");
        }
        if(dateStr.contains("正月")){
            dateStr=dateStr.replaceAll("正月","1月");
        }
        if(dateStr.contains("初一")){
            dateStr=dateStr.replaceAll("初一","1日");
        }
        if(dateStr.contains("初二")){
            dateStr=dateStr.replaceAll("初二","2日");
        }
        if(dateStr.contains("初三")){
            dateStr=dateStr.replaceAll("初三","3日");
        }
        if(dateStr.contains("初四")){
            dateStr=dateStr.replaceAll("初四","4日");
        }
        if(dateStr.contains("初五")){
            dateStr=dateStr.replaceAll("初五","5日");
        }
        if(dateStr.contains("初六")){
            dateStr=dateStr.replaceAll("初六","6日");
        }
        if(dateStr.contains("初七")){
            dateStr=dateStr.replaceAll("初七","7日");
        }
        if(dateStr.contains("初八")){
            dateStr=dateStr.replaceAll("初八","8日");
        }
        if(dateStr.contains("初九")){
            dateStr=dateStr.replaceAll("初九","9日");
        }
        if(dateStr.contains("初十")){
            dateStr=dateStr.replaceAll("初十","10日");
        }
        if(dateStr.contains("上午")){
            dateStr=dateStr.replaceAll("上午","AM");
        }
        if(dateStr.contains("下午")){
            dateStr=dateStr.replaceAll("下午","PM");
        }
        if(dateStr.contains("公元前")){
            dateStr=dateStr.replaceAll("公元前","BC");
        }
        if(dateStr.contains("公元后")){
            dateStr=dateStr.replaceAll("公元后","AD");
        }
        if(dateStr.contains("十一")){
            dateStr=dateStr.replaceAll("十一","11");
        }
        if(dateStr.contains("十二")){
            dateStr=dateStr.replaceAll("十二","12");
        }
        if(dateStr.contains("〇")){
            dateStr=dateStr.replaceAll("〇","0");
        }
        if(dateStr.contains("一")){
            dateStr=dateStr.replaceAll("一","1");
        }
        if(dateStr.contains("二")){
            dateStr=dateStr.replaceAll("二","2");
        }
        if(dateStr.contains("三")){
            dateStr=dateStr.replaceAll("三","3");
        }
        if(dateStr.contains("四")){
            dateStr=dateStr.replaceAll("四","4");
        }
        if(dateStr.contains("五")){
            dateStr=dateStr.replaceAll("五","5");
        }
        if(dateStr.contains("六")){
            dateStr=dateStr.replaceAll("六","6");
        }
        if(dateStr.contains("七")){
            dateStr=dateStr.replaceAll("七","7");
        }
        if(dateStr.contains("八")){
            dateStr=dateStr.replaceAll("八","8");
        }
        if(dateStr.contains("九")){
            dateStr=dateStr.replaceAll("九","9");
        }
        if(dateStr.contains("十")){
            dateStr=dateStr.replaceAll("十","10");
        }
        String yearyyRegExp = "((0?|[1-9]){1}\\d{0,3})";
        Boolean isTwoNum = Pattern.matches("[0-9]{2}",yearHead);
        if(isTwoNum){//是两位的时间头，就进行添加，不是，则不做处理
            //2019,2019,19,1000-9999
            //yearyyRegExp = "(([1-9]{1}[0-9]{3})|(\\d{2}))";
            //01,19年
            if(Pattern.matches("\\d{2}",dateStr)){
                yearyyRegExp = "(\\d{2})";
            }
            else if(Pattern.matches("(\\d[1]|\\d[3]|\\d{4})",dateStr)){
                yearHead = "";
            }
        }
        else {
            if(Pattern.matches("0[0-9]",dateStr)){
                yearyyRegExp = "(\\d{2})";
            }
        }
        //月份：1,12,01,Jan,January
        String monthMMRegExp = "((0?([1-9]))|(1[0|1|2]))";
        String monthMMMRegExp = "((Jan)|(Feb)|(Mar)|(Apr)|(May)|(Jun)|(Jul)|(Aug)|(Sep)|(Oct)|(Nov)|(Dec))";
        String monthMMMMMRegExp = "((January)|(February)|(March)|(April)|(May)|(June)|(July)|(Augest)|(September)|(October)|(November)|(December))";
        String monthAllRegExp = "("+monthMMRegExp+"|"+monthMMMRegExp+"|"+monthMMMMMRegExp+")";
        //星期，Mon,Monday
        String weekEEERegExp = "((Mon)|(Tue)|(Wed)|(Thu)|(Fri)|(Sat)|(Sun))";
        String weekEEEEERegExp = "((Monday)|(Tuesday)|(Webdnesday)|(Thursday)|(Friday)|(Saturday)|(Sunday))";
        String weekAllRegExp = "("+weekEEERegExp+"|"+weekEEEEERegExp+")";
        //1,11,31,01，一月的几日
        String dateddRegExp = "((0?[0-9])|([12]([0-9]))|(3[0|1]))";
        //小时1，01,12，23
        String hourHhRegExp = null;
        if(hour24or12){
            hourHhRegExp = "(((1|0?)[0-9])|(2[0-3]))";//通用24小时制
        }
        else {
            hourHhRegExp = "((0?[0-9])|10|11)";//12小时
        }
        String kKRegExp = null;
        if(hour24or12){
            kKRegExp = "([1-9]|(1[0-9])|(2[0-4]))";//1-24小时制
        }
        else {
            kKRegExp = "((0?[0-9])|(10|11))";//0-11，12小时制
        }
        //分钟，秒，01,1,59
        String mMssRegExp = "(0?|[1-5])[0-9]";
        //毫秒，纳秒0.1235345...
        String hsnsws = "([0-9]+)";
        //时区，-0700，+0830,
        String timeZoneZRegExp = "([-|\\+](([1|0][0-9])|(2[0-3]))[3|0]0)";
        //时区，所有国家时区英文简写，CST美国中部，PDT太平洋夏至，PST太平洋标准，GMT中国标准时间,UTC世界时间
        String timeZonezRegExp = "((CST)|(UTC)|(PDT)|(PST)|(GMT))";//一般三个字母，也可能2-6个([A-Z]{2,6})，不常用的省略匹配
        //时区 -0700，+0830,GMT-0530,所有国家时区英文简写，CST美国中部，PDT太平洋夏至，PST太平洋标准，GMT中国标准时间,UTC世界时间
        String timeZoneZzRegExp = "("+timeZonezRegExp+"|"+timeZoneZRegExp+"|("+timeZonezRegExp+timeZoneZRegExp+"))";
        String zzzzRegExp = "Pacific Daylight Time";
        //公元前后
        String gADBCRegExgp = "((AD)|(BC)|(A\\.D)|(B\\.C)|(ad)|(bc)|(a\\.d)|(b\\.c))";
        //上午下午
        String apmRegExp = "((AM)|(PM)|(am)|(pm))";
        //mysqlDate,2019-12-30,2019年12月12日,2019 12 30,
        String date_yyMMddRegExp = "("+yearyyRegExp+"(/|-|\\.|年| )?"+monthMMRegExp+"(/|-|\\.|月| )?"+dateddRegExp+"(日)?)";
        //12-30-2012,12/30/2012,,12.30.2012,12月30日2012年，12月30日12年
        String date_MMddyyRegExp = "("+monthMMRegExp+"(/|-|\\.|月| )?"+dateddRegExp+"(/|-|\\.|日| )?"+yearyyRegExp+"(年)?)";
        //mysqlDate,2019-12-30,2019年12月12日,2019 12 30,//12-30-2012,12/30/2012,,12.30.2012,12月30日2012年，12月30日12年
        String date_yMdAllRegExp = "(("+yearyyRegExp+"(/|-|\\.|年| )?"+monthMMRegExp+"(/|-|\\.|月| )?"+dateddRegExp+"(日)?)|("+monthMMRegExp+"(/|-|\\.|月| )?"+dateddRegExp+"(/|-|\\.|日| )?"+yearyyRegExp+"(年)?))";
        //时分秒"15:32:58";//mysqlTime,15:32,15:2:8,8:32，15时32分58秒，15时32分,15:32:58,包括15°32'58"
        String time_HHmmssXRegExp = "("+hourHhRegExp+"([:|时]|°)"+mMssRegExp+"(([:|分|'])("+mMssRegExp+"[秒|\"]?)?)?(\\."+hsnsws+")?)";
        //分秒毫秒"32:58.234...";"32:58"(只做带误差匹配，可能是HHmm或mmss);
        String time_mmssXRegExp = "("+mMssRegExp+":"+mMssRegExp+"(\\."+hsnsws+")?)";
//        //时分秒毫秒"15:32:58.234...";15:32:58";
//        String time_HHmmssXRegExp = "("+hourHhRegExp+":"+mMssRegExp+":"+mMssRegExp+"(\\."+hsnsws+")?)";
        //"2019-12-12 15:32:58";//dateTime14Mysql,15:32:58 2019-12-12
//        String dateTime14MysqlRegExp ="("+date_yyMMddRegExp+" "+time_HHmmssXRegExp+")|("+time_HHmmssXRegExp+" "+date_yyMMddRegExp+")";
        //"2019-04-18 15:32:58.0";
//        String timeStampSMysqlRegExp =date_yyMMddRegExp+" "+time_HHmmssXRegExp+"(\\.[0-9])";
        //"2019-04-18 15:32:58.3595";
//        String timeStampSSSSMysqlRegExp =date_yyMMddRegExp+" "+time_HHmmssXRegExp+"(\\.[0-9]{4})";
        //"2019-04-18 15:32:58.3595....";未知的长度1+
//        String time_HHmmssXRegExp =date_yyMMddRegExp+" "+time_HHmmssXRegExp+"(\\."+hsnsws+")";
        //"2019-04-18 15:32:58.359+0800";//这里哪里来的格式？js?+0800时区
        String timeStampSSSZRegExp =date_yyMMddRegExp+" "+time_HHmmssXRegExp+"\\.[0-9]{3}"+timeZoneZRegExp;
        //"Thu Apr 18 16:30:45 CST 2019";
        String newDateJavaRegExp = weekEEERegExp+" "+monthMMMRegExp+" "+dateddRegExp+" "+time_HHmmssXRegExp+" [A-Z]{3} "+yearyyRegExp;
        //"Thu Apr 25 2019 14:45:23 GMT+0800";//newDateJs,Thu Apr 25 2019 14:45:23 GMT+0800 (中国标准时间)
        String newDateJsRegExp = weekEEERegExp+" "+monthMMMRegExp+" "+dateddRegExp+" "+yearyyRegExp+" "+time_HHmmssXRegExp+" "+timeZonezRegExp+timeZoneZRegExp+"( \\(中国标准时间\\))?";

        //--------------------以下为不常用的，可有可无-------------------------
        //"2001.07.04 AD at 12:08:56 PDT";
        String dmdgttRegExp = date_yyMMddRegExp+" "+gADBCRegExgp+" at "+time_HHmmssXRegExp+" "+timeZonezRegExp;
        // "Wed,Jul 4,01";
        String wmdyRegExp = weekEEERegExp+","+monthMMMRegExp+" "+dateddRegExp+","+yearyyRegExp;
        //"12:08 PM";
        String taRegExp = time_HHmmssXRegExp+" "+apmRegExp;
        //12 o'clock PM,Pacific Daylight Time
        String hazRegExp = hourHhRegExp+" o'clock "+apmRegExp+","+zzzzRegExp;
        //"0:08 PM,PDT";//PDT太平洋时间
        String kmatRegExp = kKRegExp+":"+mMssRegExp+" "+apmRegExp+","+timeZonezRegExp;
        //"02001.July.04 AD 12:08 PM";
        String ymdgtRegExp = "[0-9]"+yearyyRegExp+"\\."+monthMMMMMRegExp+"\\."+dateddRegExp+" "+gADBCRegExgp+" "+taRegExp;
        //"Wed,4 Jul 2001 12:08:56 -0700";
        String wdmyttRegExp = weekEEERegExp+","+dateddRegExp+" "+monthMMMRegExp+" "+yearyyRegExp+" "+time_HHmmssXRegExp+" "+timeZoneZRegExp;
        //100704120856-0700 yyMMddHHmmssZ
        String ymdhmmtRegExp = yearyyRegExp+monthMMRegExp+dateddRegExp+hourHhRegExp+mMssRegExp+mMssRegExp+timeZoneZRegExp;
        //--------------------以上为不常用的，可有可无-------------------------

        String patternStr = null;
        Date date = null;
        DateFormat defaultFormat = null;
        if(Pattern.matches(yearyyRegExp,dateStr)){//2019
            patternStr="yy";
        }
        else if(Pattern.matches("[0-9]{2}年",dateStr)){
            patternStr="yy年";
        }
        else if(Pattern.matches(yearyyRegExp+"年",dateStr)){
            patternStr="yy年";
        }
        else if(Pattern.matches(yearyyRegExp+" "+gADBCRegExgp,dateStr)){//"2019 A.D";//2019 B.C
            if(dateStr.contains(".")){
                dateStr = dateStr.replaceAll("\\.","");
            }
            patternStr="yy G";
        }
        else if(Pattern.matches(date_yyMMddRegExp+" "+time_HHmmssXRegExp,dateStr)){
            if(Pattern.matches(yearyyRegExp+"年"+monthMMRegExp+"月"+dateddRegExp+"日 "+hourHhRegExp+"时"+mMssRegExp+"分"+mMssRegExp+"秒",dateStr)){//2019年12月12日 23时59分59秒
                patternStr="yy年MM月dd日 HH时mm分ss秒";
            }
            else if(Pattern.matches(yearyyRegExp+"-"+monthMMRegExp+"-"+dateddRegExp+" "+time_HHmmssXRegExp,dateStr)){//2019-12-12 23:59:59.3596...
                if(dateStr.contains(".")){
                    int len = dateStr.substring(dateStr.indexOf(".")+1,dateStr.length()).length();
                    patternStr="yy-MM-dd HH:mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {
                    patternStr="yy-MM-dd HH:mm:ss";
                }
            }
            else if(Pattern.matches(yearyyRegExp+"/"+monthMMRegExp+"/"+dateddRegExp+" "+time_HHmmssXRegExp,dateStr)){//2019-12-12 23:59:59.3596...
                if(dateStr.contains(".")){
                    int len = dateStr.substring(dateStr.indexOf(".")+1,dateStr.length()).length();
                    patternStr="yy/MM/dd HH:mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {
                    patternStr="yy/MM/dd HH:mm:ss";
                }
            }
            else if(Pattern.matches(yearyyRegExp+"\\."+monthMMRegExp+"\\."+dateddRegExp+" "+time_HHmmssXRegExp,dateStr)){//2019-12-12 23:59:59.3596...
                String string = dateStr.substring(dateStr.indexOf(".")+1);
                string = string.substring(string.indexOf(".")+1);
                if(string.contains(".")){
                    int len = string.substring(string.indexOf(".")+1,string.length()).length();
                    patternStr="yy.MM.dd HH:mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {
                    patternStr="yy.MM.dd HH:mm:ss";
                }
            }
            else if(Pattern.matches(yearyyRegExp+" "+monthMMRegExp+" "+dateddRegExp+" "+time_HHmmssXRegExp,dateStr)){//2019-12-12 23:59:59.3596...
                if(dateStr.contains(".")){
                    int len = dateStr.substring(dateStr.indexOf(".")+1,dateStr.length()).length();
                    patternStr="yy MM dd HH:mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {
                    patternStr="yy MM dd HH:mm:ss";
                }
            }
        }
        else if(Pattern.matches(date_yyMMddRegExp,dateStr)){
            if(Pattern.matches(yearyyRegExp+"-"+monthMMRegExp+"-"+dateddRegExp,dateStr)){//2019-12-12
                patternStr="yy-MM-dd";
            }
            else if(Pattern.matches(yearyyRegExp+"/"+monthMMRegExp+"/"+dateddRegExp,dateStr)){//2019/12/12
                patternStr="yy/MM/dd";
            }
            else if(Pattern.matches(yearyyRegExp+"年"+monthMMRegExp+"月"+dateddRegExp+"日",dateStr)){//2019年12月12日
                patternStr="yy年MM月dd日";
            }
            else if(Pattern.matches(yearyyRegExp+"\\."+monthMMRegExp+"\\."+dateddRegExp,dateStr)){//2019.12.12
                patternStr="yy.MM.dd";
            }
            else if(Pattern.matches(yearyyRegExp+" "+monthMMRegExp+" "+dateddRegExp,dateStr)){//2019 12 12
                patternStr="yy MM dd";
            }
        }
        if(patternStr!=null){
            if(isTwoNum){
                //patternStr = yearHead+patternStr;
                if(Pattern.matches("[0-9]{2}\\D.*",dateStr)){
                    dateStr = yearHead + dateStr;
                }
            }
        }
        else{
            if(Pattern.matches(date_MMddyyRegExp,dateStr)){
                if(Pattern.matches(monthMMRegExp+"-"+dateddRegExp+"-"+yearyyRegExp,dateStr)){//12-12-2019
                    patternStr="MM-dd-yy";
                }
                else if(Pattern.matches(monthMMRegExp+"/"+dateddRegExp+"/"+yearyyRegExp,dateStr)){//12/12/2019
                    patternStr="MM/dd/yy";
                }
                else if(Pattern.matches(monthMMRegExp+"月"+dateddRegExp+"日"+yearyyRegExp+"年",dateStr)){//2月12日2019年1
                    patternStr="MM月dd日yy年";
                }
                else if(Pattern.matches(monthMMRegExp+"\\."+dateddRegExp+"\\."+yearyyRegExp,dateStr)){//12.12.2019
                    patternStr="MM.dd.yy";
                }
                else if(Pattern.matches(monthMMRegExp+" "+dateddRegExp+" "+yearyyRegExp,dateStr)){//12 12 2019
                    patternStr="MM dd yy";
                }
                if(patternStr!=null){
                    if(isTwoNum){
                        if(Pattern.matches(".*\\D[0-9]{2}",dateStr)){
                            dateStr = dateStr.substring(0,6)+yearHead+dateStr.substring(6,dateStr.length());
                        }
                        else if(Pattern.matches(".*日[0-9]{2}年",dateStr)){
                            String str = dateStr.substring(dateStr.indexOf("日")+1,dateStr.indexOf("年"));
                            dateStr = dateStr.substring(0,6)+yearHead+dateStr.substring(6,dateStr.length());
                        }
                    }
                }
            }
            else if(Pattern.matches(dateddRegExp+"-"+monthMMRegExp+"月"+" -"+yearyyRegExp,dateStr)){//"21-4月 -19";//oracleDate,oracle数据库内显示的Date时间
                patternStr="dd-MM月 -yy";
                if(isTwoNum){
                    if(Pattern.matches(".*\\D[0-9]{2}",dateStr)){
                        dateStr = dateStr.substring(0,8)+yearHead+dateStr.substring(8,dateStr.length());
                    }
                }
            }
        }
        if(patternStr==null){
            if(Pattern.matches(hourHhRegExp+":"+mMssRegExp,dateStr)){//20:59
                patternStr="HH:mm";
            }
            else if(Pattern.matches(time_mmssXRegExp,dateStr)){//20:59.234...
                if(dateStr.contains(".")){//20:59.234...
                    int len = dateStr.substring(dateStr.indexOf(".")+1,dateStr.length()).length();
                    patternStr="mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {//20:59分秒
                    patternStr="mm:ss";
                }
            }
            else if(Pattern.matches(mMssRegExp+"['|＇]"+mMssRegExp+"[\"|〃]",dateStr)){
                patternStr="mm:ss"; 
            }
            else if(Pattern.matches(hourHhRegExp+"°"+mMssRegExp+"['|＇]"+mMssRegExp+"[\"|〃]",dateStr)){//15°15'15"
                patternStr="HH:mm:ss"; 
            }
            else if(Pattern.matches(hourHhRegExp+"时"+mMssRegExp+"分",dateStr)){//20时59分
                patternStr="HH时mm分";
            }
            else if(Pattern.matches(time_HHmmssXRegExp,dateStr)){////20:59:59,////20:59:59.234...
                if(dateStr.contains(".")){
                    int len = dateStr.substring(dateStr.indexOf(".")+1,dateStr.length()).length();
                    patternStr="HH:mm:ss.";
                    for (int i = 0; i <len ; i++) {
                        patternStr=patternStr+"S";
                    }
                }
                else {
                    patternStr="HH:mm:ss";
                }
            }
            else if(Pattern.matches(hourHhRegExp+"时"+mMssRegExp+"分"+mMssRegExp+"秒",dateStr)){//23时59分59秒
                patternStr="HH时mm分ss秒";
            }
            else if(Pattern.matches(monthMMRegExp+"月",dateStr)){
                patternStr="MM月";
            }
            else if(Pattern.matches(dateddRegExp+"日",dateStr)){
                patternStr="dd日";
            }
        }
        //如果匹配到
        if(patternStr!=null){
            System.out.println("-----patternStr:"+patternStr);
            defaultFormat = new SimpleDateFormat(patternStr) ;//不能使用正则，只能写死
            try {
                date = defaultFormat.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("-----成功date:"+date);
            return date;
        }
        //如果没有匹配到，进行以下带时区的匹配
        else{
            if(Pattern.matches(timeStampSSSZRegExp,dateStr)){//"2019-04-18 15:32:58.359+0800"
                patternStr="yy-MM-dd HH:mm:ss.SSSZ";
            }
            else if(Pattern.matches(date_yyMMddRegExp+"T"+time_HHmmssXRegExp+"\\.[0-9]{3}"+timeZoneZRegExp,dateStr)){//"2019-04-18T15:32:58.359+0800"
                patternStr="yy-MM-dd'T'HH:mm:ss.SSSZ";
            }
            else if(Pattern.matches(newDateJavaRegExp,dateStr)){//"Thu Apr 18 16:30:45 CST 2019"
                patternStr="EEE MMM dd HH:mm:ss z yy";
            }
            else if(Pattern.matches(newDateJsRegExp,dateStr)){//"Thu Apr 25 2019 14:45:23 GMT+0800"
                dateStr = dateStr.replaceAll("GMT","");//GMT+0800就是CST
                patternStr="EEE MMM dd yy HH:mm:ss z";
            }

            //--------------------以下为不常用的，可有可无-------------------------
            else if(Pattern.matches(dmdgttRegExp,dateStr)){//"2001.07.04 AD at 12:08:56 PDT";
                patternStr="yyyy.MM.dd G 'at' HH:mm:ss z";//或patternStr="yyyy.MM.dd GGG 'at' HH:mm:ss z";
            }
            //时区 -0700，+0830,GMT-0530,所有国家时区英文简写，CST美国中部，PDT太平洋夏至，PST太平洋标准，GMT中国标准时间,UTC世界时间
            else if(Pattern.matches(timeZoneZzRegExp,dateStr)){
                if(Pattern.matches(timeZoneZRegExp,dateStr)){//-0700，+0830,
                    patternStr="Z";
                }
                else if(Pattern.matches(timeZonezRegExp,dateStr)){//PDT
                    patternStr="z";
                }
                else if(dateStr.contains("GMT")){//GMT-0530
                    dateStr = dateStr.replaceAll("GMT","");//GMT+0800就是CST
                    patternStr="Z";
                }
            }
            else if(Pattern.matches(weekAllRegExp,dateStr)){
                if(Pattern.matches(weekEEERegExp,dateStr)){
                    patternStr="EEE";
                }
                else{
                    patternStr="EEEEE";
                }
            }
            else if(Pattern.matches(monthMMMRegExp,dateStr)){
                patternStr="MMM";
            }
            else if(Pattern.matches(monthMMMMMRegExp,dateStr)){
                patternStr="MMMMM";
            }
            else if(Pattern.matches(taRegExp,dateStr)){//"12:08 PM";
                patternStr="h:mm a";
            }
            else if(Pattern.matches(kmatRegExp,dateStr)){//"0:08 PM,PDT";//PDT太平洋时间
                patternStr="K:mm a,z";
            }
            else if(Pattern.matches(hazRegExp,dateStr)){//12 o'clock PM,Pacific Daylight Time
                patternStr="hh 'o''clock' a,zzzz";
            }
            else if(Pattern.matches(wmdyRegExp,dateStr)){// "Wed,Jul 4,01";
                patternStr="EEE,MMM d,yy";
            }
            else if(Pattern.matches(wdmyttRegExp,dateStr)){//"Wed,4 Jul 2001 12:08:56 -0700";
                patternStr="EEE,d MMM yyyy HH:mm:ss Z";
            }
            else if(Pattern.matches(ymdhmmtRegExp,dateStr)){//100704120856-0700 yyMMddHHmmssZ
                patternStr="yyMMddHHmmssZ";
            }
            else if(Pattern.matches(ymdgtRegExp,dateStr)){//"02001.July.04 AD 12:08 PM";
                patternStr="yyyyy.MMMMM.dd G HH:mm a";
            }
            //--------------------以上为不常用的，可有可无-------------------------

            System.out.println("-----patternStr:"+patternStr);
            if(patternStr!=null){
                defaultFormat = new SimpleDateFormat(patternStr,Locale.US) ;
                try {
                    date = defaultFormat.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("-----成功date:"+date);
                return date;
            }else {
                System.out.println("-----失败date,可能不是时间类型:"+null);
                return null;
            }
        }
    }
    public static void main(String[] args) throws ParseException {
        String year_yyyy = "19";//year_yyyy,2019年
        String date_yyyyMMdd = "2019-12-30";//mysqlDate,2019年12月12日,2019 12 30,12-30-2012,12/30/2012,,12.30.2012,,12月30日2012年
        String time_hhmmss = "15:32:58";//mysqlTime,15:32,15:2:8,8:32，15时32分58秒，15时32分,15:32:58,15°32'58"
        String dateTime14Mysql = "2019-12-12 15:32:58";//dateTime14Mysql,15:32:58 2019-12-12
        String timeStampSSSSMysql = "2019-04-18 15:32:58.3595";//timeStampSSSSMysql,Map:2019-04-18 15:32:58.0
        String timeStampSSSZ = "2019-04-18 15:32:58.359+0800";//这里哪里来的格式？
        String newDateJava = "Thu Apr 18 16:30:45 CST 2019";//newDateJava
        String newDateJs = "Thu Apr 25 2019 14:45:23 GMT+0800";//newDateJs,Thu Apr 25 2019 14:45:23 GMT+0800 (中国标准时间)
        String str0 = "12:08 PM";
        String str1 = "0:08 PM,PDT";//PDT太平洋时间
        String str2 = "Wed,Jul 4,'01";
        String str4 = "02001.July.04 AD 12:08 PM";
        String str5 = "2001.07.04 AD at 12:08:56 PDT";
        String str6 = "Wed,4 Jul 2001 12:08:56 -0700";
        String str = "12月12日24年";
        //方法返回null，可能不是时间类型。
        //使用时，要换算为24小时制。2019年默认不允许简写19年，不包括公元前1000-9999(java new Date也没有)，时区统一为美国中部时间CST,能判断大部分时间，但不做全部解析。
        StrToDate.dateFormat(str,"22",true);
    }
}
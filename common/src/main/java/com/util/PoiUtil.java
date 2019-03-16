package com.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

//写 xlsx,导出数据库到本地
public class PoiUtil {
    public static void outxlsx(List<HashMap<String,Object>> mapList,HttpServletResponse response) throws Exception{
        Workbook workbook = null;
        String filename = "hello.xlsx";
        //读取03或07的版本
//        String filePath = "D:\\workspace\\idea\\hshop\\hshop\\src\\main\\webapp\\mydata\\hello.xlsx";
//        if (filePath.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
//            boolean is03Excell = filePath.matches("^.+\\.(?i)(xls)$") ? true : false;
//            //1.创建工作簿
//            workbook = is03Excell ? new HSSFWorkbook() : new XSSFWorkbook();
//        }
        workbook = new XSSFWorkbook();
        //1.1创建合并单元格对象
//        CellRangeAddress callRangeAddress = new CellRangeAddress(0,0,0,4);//起始行,结束行,起始列,结束列
        //2.创建工作表
        Sheet sheet = workbook.createSheet("hello");
        //2.1加载合并单元格对象
//        sheet.addMergedRegion(callRangeAddress);
        //设置默认列宽
//        sheet.setDefaultColumnWidth(25);
//        创建自定义日期样式

        if(mapList != null) {
            //3.2创建列标题;并且设置列标题
            Row row_title = sheet.createRow(0);
            int entrynum = 0;
            for(Map.Entry<String,Object> entry:mapList.get(0).entrySet()) {
                Cell cell_title = row_title.createCell(entrynum);
                cell_title.setCellValue(entry.getKey());//列标题
                entrynum++;
            }
            //4.操作单元格;将用户列表写入excel
            for(int j=0; j<mapList.size(); j++){//多少行
//                System.out.println("--------第"+j+"行---------");
                //创建数据行,前面有列标题行
                Row row3 = sheet.createRow(j+1); // 第i行，
                Object obj = mapList.get(j);
                HashMap<String,Object> map = (HashMap)obj;
                int k = 0;
                for(Map.Entry<String,Object> entry:map.entrySet()){//每一行有多少个列
                    Object data = entry.getValue();
//                    if(entry.getValue()!=null){/*查看数据类型*/
//                        System.out.println("-----entrygetClass:"+data.getClass().getName()+":"+data);
//                    }
//                    System.out.println("--------第"+k+"列---------");
                    //创建列cell;下标从 0 开始
                    Cell cell = row3.createCell(k); // j列

//                    Oracle查出来的数字全是BigDecimal,还有字符串和日期Timestamp

                    Boolean isNum = false;//data是否为数值型
                    Boolean isInteger=false;//data是否为整数
                    Boolean isPercent=false;//data是否为百分数
//                    正则表达式在线豆速查https://www.jb51.net/tools/regexsc.htm
                    if (data != null || "".equals(data)) {
                        //判断data是否为数值型,此处是小数/浮点数double，也就是包括整数
                        isNum = data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
                        //判断data是否为整数（小数部分是否为0）
                        isInteger=data.toString().matches("^[-\\+]?[\\d]*$");
                        //判断data是否为百分数（是否包含“%”）
                        isPercent=data.toString().contains("%");
                    }

                    //判断数值类型，包括字符串数字，https://blog.csdn.net/caolaosanahnu/article/details/7610063
                    if (isNum && !isPercent) {
                        if (isInteger) {
                            DataFormat format = workbook.createDataFormat();
                            CellStyle cellStyle = workbook.createCellStyle();
                            cellStyle.setDataFormat(format.getFormat("#,#0"));//数据格式只显示整数
                            cell.setCellValue(Double.parseDouble(data.toString()));/*能不能用强转，因为是正则表达式判断的*/
                            System.out.println("-----是整数:"+data);
                        }else{
//                            cellStyle.setDataFormat(format.getFormat("#,##0.00"));//保留两位小数点
                            cell.setCellValue(Double.parseDouble(data.toString()));/*保留任意位小数*/
                            System.out.println("-----是小数:"+data);
                        }
                    }
                    else if(isNum && isPercent){
                        // 百分数设置单元格内容为字符型
                        cell.setCellValue(data.toString());
                        System.out.println("-----是百分比数:"+data);
                    }
                    else if(data instanceof String){/*可以判断出来*/
                        String value = (String) data;
                        cell.setCellValue(value);
                        System.out.println("-----String:"+value);
                    }
                    else if (data instanceof Timestamp) {/*可以判断出来java.sql.Timestamp:2013-04-07 17:05:50.0*/
                        Timestamp value = (Timestamp)data;
//                        必须要设置样式，否则显示：41371.7123842593
                        DataFormat format = workbook.createDataFormat();
                        CellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(value);
                        System.out.println("-----Timestamp:"+value);
                    }
                    else if (data instanceof Date) {/*可以判断出来=Timestamp*/
                        Date value = (Date)data;
//                        必须要设置样式，否则显示：41371.7123842593
                        DataFormat format = workbook.createDataFormat();
                        CellStyle cellStyle = workbook.createCellStyle();
                        cellStyle.setDataFormat(format.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(value);
                        System.out.println("-----Date:"+value);
                    }
                    else{
                        cell.setCellValue(JSON.toJSONString(data));//todo
//                      这些未做判断  Calendar Error Boolean  RichTextString
                        System.out.println("-----JSON:"+JSON.toJSONString(data));
                    }
                    k++;
                }
            }
        }
        // 设置 第 1 行 自动扩展  ; 下标 是 行 的下标
        sheet.autoSizeColumn(0);
        //5.输出
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition",  "attachment;filename="+filename);//下载时显示的文件名。带后缀
//        FileOutputStream  out = new  FileOutputStream(filePath);//本地下载
        OutputStream  out = response.getOutputStream();//客户端请求下载
        workbook.write(out);
        out.flush();
        out.close();
        System.out.println("---------out-------------");
    }
    public static ArrayList<HashMap<String,Object>> inxlsx(MultipartFile excelfile)throws Exception{/*excel的xlsx可插入*/
        Workbook workbook = null;
        String fname = excelfile.getOriginalFilename();
//        读取03或07的版本
//        String filePath = filepath.replaceAll("\\\\","\\\\\\\\");/*带路径的文件*/
        System.out.println("-----fname:"+fname);
//        if (filePath.matches("^.+\\.(?i)((xls)|(xlsx))$")) {/*带路径的文件*/
        if (fname.matches("^.+.(?i)((xls)|(xlsx))$")) {/*不带路径的文件*/
//            FileInputStream is = new FileInputStream(filePath);
            InputStream is = excelfile.getInputStream();
//            boolean is03Excell = filePath.matches("^.+\\.(?i)(xls)$") ? true : false;/*带路径的文件*/
            boolean is03Excell = fname.matches("^.+.(?i)(xls)$") ? true : false;/*不带路径的文件*/
            //1.创建工作簿
//            workbook = is03Excell ? new HSSFWorkbook(is) : new XSSFWorkbook(is);
            workbook = new XSSFWorkbook(is);
        }
//        workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0); // 得到 sheet

        int lastnum = sheet.getLastRowNum(); // 得到最后1行的下标
        System.out.println(" lastnum :"+lastnum);
        HashMap<String,Object> hashMap = null;
        ArrayList listmap = new ArrayList();
        ArrayList listtitle = new ArrayList();
// 取行
        Row row0 = sheet.getRow(0);
        int lastcell = row0.getLastCellNum();
        for(int i=0;i<=lastcell;i++) {//0行是行标题，也就是map的key
            Cell cell = row0.getCell(i);
            listtitle.add(cell);//这里面存的是标题key
        }
        for(int i=1;i<=lastnum;i++){//0行是行标题，也就是map的key
            Row row = sheet.getRow(i);
            hashMap = new HashMap<String,Object>();
            //取列
            int firstcell = row.getFirstCellNum();
            //System.out.println(" firstcell = "+firstcell);
            for(int j=firstcell;j<lastcell;j++){
                Cell cell = row.getCell(j);
                String vs = getCellValue(cell);// 拿值
                hashMap.put(listtitle.get(j).toString(),vs);
            }
            listmap.add(hashMap);
            System.out.println("hashMap:"+hashMap);
        }
        System.out.println("---------in-------------");
        return listmap;
    }
    //单元格样式
    private static CellStyle createCellStyle(Workbook workbook, short fontsize) {
        // TODO Auto-generated method stub
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
        style.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        //创建字体
        Font font = workbook.createFont();
        font.setFontHeightInPoints(fontsize);
        //加载字体
        style.setFont(font);
        return style;
    }
    // 类型转化
    private static String getCellValue(Cell cell){
        String value = null;
        System.out.println("-----cell:"+cell);
        if(cell.getCellTypeEnum()== CellType.NUMERIC){//日期格式也是数字类型？
            System.out.println("style:"+cell.getCellStyle().getDataFormatString());/*查看NUMERIC数据的日期格式General 和yyyy\-mm\-dd\'\T\'hh:mm:ss\.ss\Z*/
            double cellvalue = cell.getNumericCellValue();
            int format = cell.getCellStyle().getDataFormat();//格式format的值：Excel2007和Excel2003都是一样的，；
            SimpleDateFormat sdf = null;
//      自定义时间格式处理(难点)：
            if("yyyy\\-mm\\-dd\\'\\T\\'hh:mm:ss\\.ss\\Z".equals(cell.getCellStyle().getDataFormatString())){
                value = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(cellvalue);
            }
            else if("yyyy-MM-dd'T'HH:mm:ss.SSSZ".equals(cell.getCellStyle().getDataFormatString())){
                value = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(cellvalue);
            }
            else if (format == 14 || format == 31 || format == 57 || format == 58
                    || (176<=format && format<=178) || (182<=format && format<=196)
                    || (210<=format && format<=213) || (208==format ) ) { // 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd");
//                sdf = new SimpleDateFormat("yyyy\\9\\17  0:49:10");
                Date date = DateUtil.getJavaDate(cellvalue);
                value = sdf.format(date);
            }
            else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
//                Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cellvalue);
//                value = sdf.format(date);
                value = sdf.format(cellvalue);/*format的参数可以是Object*/
            } else { // 不是日期格式
                // poi导入数据格式 小数点处理(重点)
                Object inputValue = null;// 单元格值
                Long longVal = Math.round(cell.getNumericCellValue());/*去掉小数位*/
                Double doubleVal = cell.getNumericCellValue();
                if(Double.parseDouble(longVal + ".0") == doubleVal){   //判断是否含有小数位.0
                    inputValue = longVal;
                }
                else{
                    inputValue = BigDecimal.valueOf(doubleVal);
                }
                value = String.valueOf(inputValue);
            }
        }
        else if(cell.getCellTypeEnum()== CellType.BOOLEAN){
            value = String.valueOf(cell.getBooleanCellValue());
        }
        else if(cell.getCellTypeEnum()== CellType.FORMULA){/*公式类型*/
            value = cell.getCellFormula();
        }
        else if(cell.getCellTypeEnum()== CellType.BLANK) {
            value = "";
        }
        else if(cell.getCellTypeEnum()== CellType.STRING){
            value = cell.getStringCellValue();
        }
        else if(cell.getCellTypeEnum()== CellType.ERROR){
            value = String.valueOf(cell.getErrorCellValue());
        }
        else if(cell.getCellTypeEnum()== CellType._NONE){/*未知类型*/
            value = cell.toString();
        }
        else {
            value = cell.toString();
        }
        return value;
    }
}
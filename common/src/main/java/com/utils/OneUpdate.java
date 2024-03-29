package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

//必须放在utils文件夹，否则手动修改必须保证所有文件夹名规范，dao,service,才能使用此工具
//数据库中的大写在对象属性中是小写，数据库中下划线后的字母在对象属性中是大写，
//@Component
//@Configuration //当前类是一个配置类，同.xml,.properties。
//浅析PropertySource 基本使用https://www.cnblogs.com/cxuanBlog/p/10927823.html
//@PropertySource("classpath:/config.properties")自定义使用哪个配置文件来注入属性值，通常结合@Configuration
//@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = false)
@Component
public class OneUpdate {
    private static final Logger logger = LoggerFactory.getLogger(OneUpdate.class);
    //private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(OneUpdate.class.getName());
    private String jarName = "";//mysql-connector-java-5.1.47.jar
    private String propertiesName="";//config.properties
    private String resourcesPath="";//D:/workspace/idea/com/zufang/src/main/resources
    private String jarLocation="";//D:/workspace/idea/com/zufang/src/main/resources/mybatisGenerator/mysql-connector-java-5.1.47.jar
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;//com.mysql.jdbc.Driver
    @Value("${spring.datasource.url}")
    private String connectionURL;//jdbc:mysql://localhost:3306/src?characterEncoding=utf8
    @Value("spring.datasource.name")
    private String dataBaseName;//数据库名不一定是工程名，所以generator.xml要检查。
    private String javaModelGenerator="";//com.entity
    private String javaTargetProject="";//D:/workspace/idea/com/zufang/src/main/java
    private String sqlMapGenerator="";//resources.mapper
    private String sqlTargetProject="";//D:/workspace/idea/com/zufang/src/main
    private String clientGenerator="";//com.dao
    private String clientTargetProject="";//D:/workspace/idea/com/zufang/src/main/java
    @Value("${spring.datasource.username}")//只有在项目运行时才能获取？
    private String userId;//root
    @Value("${spring.datasource.password}")
    private String password;//root
    //@Value("${server.servlet.context-path}") //由于springcloud的yml不配置context-path，这里读取会报错，
    private String projectName;//zufang,工程名不一定是数据库名，所以generator.xml要检查。
    private String generatorPath = "D:/workspace/idea/springcloud/f8xn/src/resources/mybatisGenerator/";// D:/workspace/idea/springcloud/f8xn/src/resources/mybatisGenerator/，不能是路径\\
    private String jarMybatis = "mybatis-generator-core-1.3.2.jar";
    //private URL oneUpdateURL = "file:/D:/workspace/idea/springcloud/f8xn/autof8/target/classes/com/utils/";
    private URL oneUpdateURL = OneUpdate.class.getResource("");//=this.getClass().getResource("/").toString();当前类或utils所在的本地target中的URL。在当前调用类的同一路径下查找该资源""
    private String oneUpdatePath = "";
    //private String oneUpdatePath = "file:/D:/workspace/idea/springcloud/f8xn/autof8/target/classes/com/utils/";//项目目录file /x:/xxx/target/classes/com/utils/
    private boolean flagDel = false;//重构是否删除原来的dao、entity、mapper.xml。false不删除
    private String cmd = "";//"cmd /k start D:/workspace/idea/com/zufang/src/main/resources/mybatisGenerator/run.bat";
    private String comName = "";//com
    private String daoFolderName = "";//dao
    private String daoLastName = "";//Dao、Mapper
    private String entityName = "";//entity，这个必须填写，不能为空
    private String iServiceFolderName = "";//service
    private String serviceFolderName = "";//impl
    private String tableName = "";//tableName=entity,区分大小写，Testtype。
    private String[] tableNameArr = null;
    private String daoPath = "";//D:/workspace/idea/guo/zufang/src/main/java/com/dao
    private String iServicePath = "";//D:/workspace/idea/guo/zufang/src/main/java/com/service
    private String servicePath = "";//D:/workspace/idea/guo/zufang/src/main/java/com/service/impl
    private String actionPath = "";
    public OneUpdate(){
        super();
    }
    //private String daoPackageName = comName+"."+daoFolderName;//com.dao;
    //private String iServicePackageName = comName+"."+iServiceFolderName;;//com.service
    //private String servicePackageName = comName+"."+serviceFolderName;//com.service.impl
    public OneUpdate(String propertiesName, String jarName, String daoFolderName, String entityName, String daoLastName, String iServiceFolderName, String serviceFolderName, boolean flagDel, Object... tableNames){
        if(this.propertiesName==null||"".equals(this.propertiesName)){
            this.propertiesName=propertiesName;
        }
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(this.propertiesName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //-----------由于不启动，不能通过@Value取config.properties里的属性值(已验证)，以下必须-----------
        if(this.userId==null||"".equals(this.userId)){
            userId = properties.getProperty("spring.datasource.username");
        }
        if(this.password==null||"".equals(this.password)){
            password = properties.getProperty("spring.datasource.password");
        }
        if(this.driverClass==null||"".equals(this.driverClass)){
            driverClass = properties.getProperty("spring.datasource.driver-class-name");
        }
        if(this.connectionURL==null||"".equals(this.connectionURL)){
            connectionURL = properties.getProperty("spring.datasource.url");
        }
        if(connectionURL.contains("?")){
            connectionURL = connectionURL.replaceAll("&","&amp;");
        }
        if(this.dataBaseName==null||"".equals(this.dataBaseName)){
            dataBaseName = properties.getProperty("spring.datasource.name");
            if(this.dataBaseName==null||"".equals(this.dataBaseName)){
                if(connectionURL.contains("mysql")){
                    int indexStart = StringIndex.indexOf(connectionURL,"/" ,3);
                    int indexEnd = connectionURL.indexOf("?");
                    this.dataBaseName=connectionURL.substring(indexStart+1,indexEnd);
                }
                else if(connectionURL.contains("oracle")){
                    int indexStart = connectionURL.lastIndexOf(":");
                    this.dataBaseName=connectionURL.substring(indexStart+1);
                }
                else if(connectionURL.contains("postgresql")){
                    int indexStart = StringIndex.indexOf(connectionURL,"/" ,3);
                    int indexEnd = connectionURL.indexOf("?");
                    this.dataBaseName=connectionURL.substring(indexStart+1,indexEnd);
                }
                else {
                    logger.info("-----没有匹配到数据库名，请在OneUpdate.java的100行增加数据库");
                    System.exit(0);//0停止程序，1是异常停止。
                }
            }
        }
        //-------------------以上必需------------------------

        if(this.daoFolderName==null||"".equals(this.daoFolderName)){this.daoFolderName =daoFolderName;}
        if(this.entityName==null||"".equals(this.entityName)){this.entityName =entityName;}
        if(this.daoLastName==null||"".equals(this.daoLastName)){this.daoLastName =daoLastName;}
        if(this.iServiceFolderName==null||"".equals(this.iServiceFolderName)){this.iServiceFolderName=iServiceFolderName;}
        if(this.serviceFolderName==null||"".equals(this.serviceFolderName)){this.serviceFolderName =serviceFolderName;}
        if(tableNames[0].toString().contains("\n")){
            String tableStr = tableNames[0].toString();
            String dataStr = null;
            if(tableStr.contains(".")){
                dataStr = tableStr.substring(0,tableStr.indexOf("."));
                tableStr = tableStr.replaceAll(dataStr+".","");
            }
            tableStr = tableStr.replace(" ", "");//去空格
            if(!Pattern.compile("[a-z]+").matcher(tableStr).find()){
                tableStr = tableStr.toLowerCase();//如果字符串全是大写，则全转小写
            }
            String[] tableArr = tableStr.split("\n");
            for (int i = 0; i <tableArr.length ; i++) {
                tableArr[i]= StringIndex.upFirstWord(tableArr[i]);
            }
            this.tableNameArr = tableArr;
            this.tableName = tableArr[0];
        }
        else {
            if(this.tableName==null){
                setTableName(tableNames[0].toString());
            }
            if(this.tableNameArr==null){
                this.tableNameArr = new String[tableNames.length];
                for (int i = 0; i <tableNames.length ; i++) {
                    this.tableNameArr[i]= StringIndex.upFirstWord(tableNames[i].toString());
                }
            }
        }
        if(this.oneUpdatePath==null||"".equals(this.oneUpdatePath)){
            this.oneUpdatePath = this.oneUpdateURL.toString();
            System.out.println("oneUpdateURLStr:"+oneUpdatePath);
        }
        this.oneUpdatePath=this.oneUpdatePath.replaceAll("file:/","");
        this.oneUpdatePath=this.oneUpdatePath.replaceAll("target/classes","src/main/java");
        //oneUpdatePath=D:/workspace/idea/guo/zufang/src/main/java/com/utils/
        String utils = StringIndex.substringLastIndexOf(this.oneUpdatePath,"/",1,2);
        if(this.daoPath==null||"".equals(this.daoPath)){
            this.daoPath = this.oneUpdatePath.replaceAll(utils, daoFolderName);
            System.out.println("-----daoPath:"+this.daoPath);
        }
        if(this.projectName==null||"".equals(this.projectName)){
            this.projectName= StringIndex.substringLastIndexOf(this.daoPath,"/",6,7);
            //this.projectName=properties.getProperty("server.servlet.context-path");
        }
        if(this.comName==null||"".equals(this.comName)){
            this.comName = StringIndex.substringLastIndexOf(oneUpdatePath,"/",2,3);
        }
        if(this.iServicePath==null||"".equals(this.iServicePath)){
            this.iServicePath = oneUpdatePath.replaceAll(utils, iServiceFolderName);
            System.out.println("-----iServicePath:"+this.iServicePath);
        }
        if(this.servicePath==null||"".equals(this.servicePath)){
            this.servicePath = oneUpdatePath.replaceAll(utils, iServiceFolderName +"/"+ serviceFolderName);
            System.out.println("-----servicePath:"+this.servicePath);
        }
        if(this.generatorPath==null||"".equals(this.generatorPath)){
            String javacomutils = StringIndex.substringLastIndexOf(oneUpdatePath,"/",1,4);
            this.generatorPath = oneUpdatePath.replaceAll(javacomutils, "resources/mybatisGenerator");
            //D:\workspace\idea\guo\zufang\src\main\resources\mybatisGenerator\generator.xml
            System.out.println("-----generatorPath:"+generatorPath);
        }
        if(this.jarName==null||"".equals(this.jarName)){
            this.jarName=jarName;
        }
        if(this.jarLocation==null||"".equals(this.jarLocation)){
            this.jarLocation=this.generatorPath+this.jarName;
            System.out.println("-----jarLocation:"+this.jarLocation);
        }
        if(this.javaModelGenerator==null||"".equals(this.javaModelGenerator)){
            this.javaModelGenerator=this.comName+"."+entityName;
        }
        if(this.javaTargetProject==null||"".equals(this.javaTargetProject)){
            int index = StringIndex.lastIndexOf(oneUpdatePath,"/",3);
            this.javaTargetProject=oneUpdatePath.substring(0,index);
            System.out.println("-----javaTargetProject:"+this.javaTargetProject);
        }
        if(this.sqlMapGenerator==null||"".equals(this.sqlMapGenerator)){
            this.sqlMapGenerator="resources.mapper";
        }
        if(this.sqlTargetProject==null||"".equals(this.sqlTargetProject)){
            int index = StringIndex.lastIndexOf(oneUpdatePath,"/",4);
            this.sqlTargetProject=oneUpdatePath.substring(0,index);
            System.out.println("-----sqlTargetProject:"+this.sqlTargetProject);
        }
        if(this.clientGenerator==null||"".equals(this.clientGenerator)){
            this.clientGenerator=comName+"."+this.daoFolderName;
        }
        if(this.clientTargetProject==null||"".equals(this.clientTargetProject)){
            int index = StringIndex.lastIndexOf(oneUpdatePath,"/",3);
            this.clientTargetProject=oneUpdatePath.substring(0,index);
            System.out.println("-----clientTargetProject:"+this.clientTargetProject);
        }
        if(this.resourcesPath==null||"".equals(this.resourcesPath)){
            //oneUpdatePath=D:/workspace/idea/guo/zufang/src/main/java/com/utils/
            int index= StringIndex.lastIndexOf(oneUpdatePath,"/",4);
            this.resourcesPath = oneUpdatePath.substring(0,index+1)+"resources/";
            System.out.println("-----resourcesPath:"+resourcesPath);
        }
        if(this.cmd==null||"".equals(this.cmd)){
            this.cmd = "cmd /c start "+generatorPath+"run.bat";
            //this.cmd = "cmd /c start "+generatorPath+"run.bat";//c表示执行命令后关闭窗口，关闭无效，使用exit加在了run.bat后,或者直接在bat文件里手动添加一个exit。
            //命令可直接执行，也可以放在bat批量文件处理。
            //只打开文件exec("start .//a.doc");
        }
        this.flagDel = flagDel;
        System.out.println("-----userid:"+this.userId);
        System.out.println("-----cmd:"+this.cmd);
    }

    public void setTableName(String tableName) {
        //tableName = tableName.substring(0,1).toUpperCase()+tableName.substring(1);
        if(Character.isLowerCase(tableName.charAt(0))){//如果首字母是小写，则改为大写
            tableName = StringIndex.upFirstWord(tableName);
        }
        this.tableName = tableName;
    }
//    public void setDaoPackageName(String daoPackageName) {
//        this.daoPackageName = daoPackageName;
//    }
//    public void setiServicePackageName(String iServicePackageName) {
//        this.iServicePackageName = iServicePackageName;
//    }
//    public void setServicePackageName(String servicePackageName) {
//        this.servicePackageName = servicePackageName;
//    }
//    public String getDaoPackageName(){
//        if(daoPackageName==null||"".equals(daoPackageName)){
//            String classStr = OneUpdate.class.getName();//com.utils.OneUpdate
//            classStr=classStr.substring(0,classStr.lastIndexOf(".")-1);
//            classStr = classStr.replaceAll(classStr.substring(classStr.indexOf(".")+1), daoFolderName);
//            this.daoPackageName = classStr;
//            System.out.println("-----daoPackageName:"+daoPackageName);
//        }
//        return daoPackageName;
//    }
//    public String getIServicePackageName(){
//        if(iServicePackageName==null||"".equals(iServicePackageName)){
//            String classStr = OneUpdate.class.getName();//com.utils.OneUpdate
//            classStr=classStr.substring(0,classStr.lastIndexOf(".")-1);
//            classStr = classStr.replaceAll(classStr.substring(classStr.indexOf(".")+1), iServiceFolderName);
//            this.iServicePackageName = classStr;
//            System.out.println("-----iServicePackageName:"+iServicePackageName);
//        }
//        return iServicePackageName;
//    }
//    public String getServicePackageName(){
//        if(servicePackageName==null||"".equals(servicePackageName)){
//            String classStr = OneUpdate.class.getName();//com.utils.OneUpdate
//            classStr=classStr.substring(0,classStr.lastIndexOf(".")-1);
//            classStr = classStr.replaceAll(classStr.substring(classStr.indexOf(".")+1), iServiceFolderName +"."+ serviceFolderName);
//            this.servicePackageName = classStr;
//            System.out.println("-----servicePackageName:"+servicePackageName);
//        }
//        return servicePackageName;
//    }

    //配置generator.xml中的tableName
    public void generator(){
        String tableObj ="       <table schema=\"user\" tableName=\"table\"\n" +
                "            domainObjectName=\"Table\" enableCountByExample=\"false\"\n" +
                "            enableUpdateByExample=\"false\" enableDeleteByExample=\"false\"  \n" +
                "            enableSelectByExample=\"false\" selectByExampleQueryId=\"false\">\n" +
                "       </table>  ";

        File daoFolderFile = new File(daoPath);
        String[] mapperFileStrArr = daoFolderFile.list();/*返回所有的dao文件名*///TesttypeMapper.java,
        List<String> tableList = new ArrayList<String>();
        boolean flag = false;
        //判断是否则重构实体,默认false，不删除重构
        if(flagDel){
            //删除指定dao文件
            for(String tableNamei:tableNameArr){
                File tempFile = new File(daoPath+tableNamei+daoLastName+".java");
                if(tempFile!=null){
                    tempFile.delete();
                }
            }
            //删除指定entity文件
            for(String tableNamei:tableNameArr){
                File tempFile = new File(StringIndex.substringRegExpRight(daoPath,"/",2)+"/"+entityName+"/"+tableNamei+".java");
                if(tempFile!=null){
                    tempFile.delete();
                }
            }
            //删除指定的mapper.xml
            for(String tableNamei:tableNameArr){
                File tempFile = new File(resourcesPath+"/"+"mapper"+"/"+tableNamei+daoLastName+".xml");
                if(tempFile!=null){
                    tempFile.delete();
                }
            }
        }
//        else {
//            for (String tablej:tableNameArr) {
//                //判断要修改的实体，是否已生成
//                for (String mapperi:mapperFileStrArr) {
//                    if ((tablej.toLowerCase() + daoLastName.toLowerCase() + ".java").equals(mapperi.toLowerCase())) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if(!flag){
//                    tableList.add(tablej);
//                }
//                else {
//                    flag = false;
//                }
//            }
//            StringBuffer stringBuffer = new StringBuffer();
//            String tableStr = null;
//            for (int i = 0; i <tableList.size() ; i++) {
//                tableObj = tableObj.replaceAll("schema=\"\\S+\" ", "schema=\""+dataBaseName+"\" ");
//                tableObj = tableObj.replaceAll("tableName=\"\\S+\"", "tableName=\""+tableList.get(i)+"\"");
//                tableStr = tableList.get(i).substring(0,1).toUpperCase()+tableList.get(i).substring(1);//截取第一个大写，再拼接，再替换
//                tableObj = tableObj.replaceAll("domainObjectName=\"\\S+\" ", "domainObjectName=\""+tableStr+"\" ");
//                stringBuffer.append("\n"+tableObj);
//            }
//        }
        StringBuffer stringBuffer = new StringBuffer();
        String tableStr = null;
        for (int i = 0; i <tableNameArr.length ; i++) {
            tableObj = tableObj.replaceAll("schema=\"\\S+\" ", "schema=\""+dataBaseName+"\" ");
            tableObj = tableObj.replaceAll("tableName=\"\\S+\"", "tableName=\""+tableNameArr[i]+"\"");
            tableStr = tableNameArr[i].substring(0,1).toUpperCase()+tableNameArr[i].substring(1);//截取第一个大写，再拼接，再替换
            tableObj = tableObj.replaceAll("domainObjectName=\"\\S+\" ", "domainObjectName=\""+tableStr+"\" ");
            stringBuffer.append("\n"+tableObj);
        }
        System.out.println("stringBuffer"+stringBuffer);

        //FileInputStream fileInputStream = null;
        //FileOutputStream fileOutputStream = null;
        //BufferedInputStream bufferedInputStream = null;
        //BufferedOutputStream bufferedOutputStream = null;
        //不能用字节流，因为要文本替换
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        //D:\workspace\idea\guo\zufang\src\main\resources\mybatisGenerator\generator.xml
        File generatorFile = new File(generatorPath+"generator.xml");
        StringBuffer generatorXml = new StringBuffer();
        try {
            fileReader = new FileReader(generatorFile);
            bufferedReader = new BufferedReader(fileReader);
            int unicode = bufferedReader.read();
            while (unicode!=-1){
                generatorXml.append((char)unicode);
                unicode = bufferedReader.read();
            }
            String generatorXmlStr = generatorXml.toString();
            generatorXmlStr = generatorXmlStr.replaceAll("location=\"[\\u4e00-\\u9fa5\\w\\.\\-:/\\\\]+\\.jar\"","location=\""+jarLocation+"\"");
            if(connectionURL.contains("mysql-connector-java-6")||connectionURL.contains("mysql-connector-java-8")){
                logger.info("请使用低于6版本的mysql驱动，否则修改generator和OneUpdate.java");
                System.exit(0);
            }
            else {
                //generatorXmlStr = generatorXmlStr.replaceAll("driverClass=\"[\\w\\.]+Driver","driverClass=\""+driverClass); //后面不要加+"\""
                generatorXmlStr = generatorXmlStr.replaceAll("driverClass=\"[\\w\\.]+Driver","driverClass=\""+driverClass); //后面不要加+"\""
            }
            generatorXmlStr = generatorXmlStr.replaceAll("connectionURL=\"[\\w\\.\\-:=&;\\? /\\\\]*\" userId","connectionURL=\""+connectionURL+"\" userId");
            generatorXmlStr = generatorXmlStr.replaceAll("&(?!amp;)","&amp;");
            generatorXmlStr = generatorXmlStr.replaceAll("<javaModelGenerator[\\s]+targetPackage=\"[\\w\\./]*+\"[\\s]+targetProject=\"[\\u4e00-\\u9fa5\\w\\.\\-:/\\\\]+\">","<javaModelGenerator targetPackage=\""+javaModelGenerator+"\" targetProject=\""+javaTargetProject+"\">");
            generatorXmlStr = generatorXmlStr.replaceAll("<sqlMapGenerator[\\s]+targetPackage=\"[\\w\\./]*+\"[\\s]+targetProject=\"[\\u4e00-\\u9fa5\\w\\.\\-:/\\\\]+\">","<sqlMapGenerator targetPackage=\""+sqlMapGenerator+"\" targetProject=\""+sqlTargetProject+"\">");
            generatorXmlStr = generatorXmlStr.replaceAll("<javaClientGenerator[\\s]+targetPackage=\"[\\w\\./]*+\"[\\s]+targetProject=\"[\\u4e00-\\u9fa5\\w\\.\\-:/\\\\]+\" type=\"XMLMAPPER\">","<javaClientGenerator targetPackage=\""+clientGenerator+"\" targetProject=\""+clientTargetProject+"\" type=\"XMLMAPPER\">");
            generatorXmlStr = generatorXmlStr.replaceAll("<table[\\s\\w=\">]+</table>","");
            generatorXmlStr = generatorXmlStr.replaceAll("javaClientGenerator>\\s+","javaClientGenerator>\n"+stringBuffer.toString()+"\n");
            //System.out.println("-----generatorXmlStr:"+generatorXmlStr);
            fileWriter = new FileWriter(generatorFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(generatorXmlStr);
            fileWriter.flush();
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            bufferedReader.close();
            fileReader.close();

            File runFile = new File(generatorPath+"run.bat");
            fileReader=new FileReader(runFile);
            bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBufferRun = new StringBuffer();
            int unicode2 = bufferedReader.read();
            while (unicode2!=-1){
                stringBufferRun.append((char)unicode2);
                unicode2=bufferedReader.read();
            }
            String runBatStr = stringBufferRun.toString();
            runBatStr = runBatStr.replaceAll("java[\\s\\w\\-\\.:/\n\\\\]*-overwrite[\\sa-z\n]*","java -jar "+generatorPath+jarMybatis+" -configfile "+generatorPath+"generator.xml -overwrite\nexit");
            System.out.println("-----runBatStr:"+runBatStr);
            fileWriter = new FileWriter(runFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(runBatStr);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //调用自动生成器run.bat,也可以调用.exe,cmd后直接写路径，不要加前缀参数
    public void runbat(){
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            //cmd 中有特殊符号时，使用exec(String[] arr);
//            InputStream inputStream = process.getInputStream();
//            int c=inputStream.read();
//            StringBuffer stringBuffer = new StringBuffer();
//            while (c!=-1){//这个会挂起，不能执行后面的代码,等待命令？
//                stringBuffer.append((char)c);
//                c = inputStream.read();
//            }
//            System.out.println("-----cmdRun:");
//            inputStream.close();
//            process.waitFor();//要在这之前读取文件流,
        } catch (IOException e) {
            e.printStackTrace();
        }
        //执行java *.jar提示没有主体清单属性，pom.xml增加以下内容？可有可无！自己引用的mybatis.jar错了。
//        <plugin>
//          <groupId>org.springframework.boot</groupId>
//          <artifactId>spring-boot-maven-plugin</artifactId>
//        </plugin>
    }
    //dao生成iservice,
    public void mapperToIService(Object... tableNames){
        if(this.tableName==null){
            if(tableNames[0]!=null){
                setTableName(tableNames[0].toString());
            }
        }
        if(this.tableNameArr==null){
            this.tableNameArr = new String[tableNames.length];
            for (int i = 0; i <tableNames.length ; i++) {
                tableNameArr[i]= StringIndex.upFirstWord(tableNames[i].toString());
            }
        }
        File daoFolderFile = new File(daoPath);
        //File iServiceFolderFile = new File(daoPath.substring(0,daoPath.length()-4) + "\\service");
        File iServiceFolderFile = new File(iServicePath);
        iServiceFolderFile.mkdirs();
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringBuffer stringBuffer = null;
        String[] mapperFileStrArr = daoFolderFile.list();/*返回所有的文件名*///TesttypeMapper.java,
        System.out.println("-----mapperFileStrArr:"+ Arrays.toString(mapperFileStrArr));
        boolean flag = false;
        for (int j = 0; j <tableNameArr.length; j++) {
            for (int i = 0; i <mapperFileStrArr.length ; i++) {
                if((tableNameArr[j].toLowerCase()+daoLastName.toLowerCase()+".java").equals(mapperFileStrArr[i].toLowerCase())){
                    try {
                        flag = true;
                        tableName=tableNameArr[j];
                        File mapperFile = new File(daoFolderFile.getCanonicalPath() + "\\" + mapperFileStrArr[i]);//daoFolderFile.getCanonicalPath()=dapPath
                        if(mapperFile.isFile()){
                            System.out.println("mapperFile是文件不是文件夹");
                            fileReader = new FileReader(mapperFile);
                            bufferedReader = new BufferedReader(fileReader);
                            stringBuffer = new StringBuffer();
                            //String str = bufferedReader.readLine();/*使用行读取，后面的操作也必须全以行来操作*/
                            int unicode = bufferedReader.read();//读入硬盘文件里的一个字符，返回unicode编码。读取到末尾返回-1。
                            while (unicode != -1) {
                                stringBuffer.append((char) unicode);
                                //System.out.println(stringBuffer);//可以用%c输出数字对应的字符(System.out.printf("c%",num))
                                unicode = bufferedReader.read();//读取文件的下一个字符。
                            }

                            File iServiceFile = null;
                            String daoStr = stringBuffer.toString();
                            String importStr = "import org.springframework.stereotype.Repository;";
                            String repositoryStr = "@Repository";
                            //修改daoStr
                            daoStr = daoStr.replaceAll("\\s*public","\n"+importStr+"\n\n"+repositoryStr+"\npublic");
                            fileWriter = new FileWriter(mapperFile);
                            bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(daoStr);
                            bufferedWriter.flush();
                            fileWriter.flush();

                            //---------------------------dao生成iservice,这里执行相关的替换----------------------------
                            //tableName = mapperFileStrArr[i].substring(0,mapperFileStrArr[i].length()-11);
                            //setTableName(tableName);
                            //tableName = this.tableName;

                            //替换dao中的内容
                            daoStr = daoStr.replaceAll(tableName+ daoLastName,"I"+tableName+"Service");
                            daoStr = daoStr.replaceAll(daoFolderName,iServiceFolderName);
                            int indexImport = daoStr.indexOf(importStr);
                            int indexRep = daoStr.indexOf(repositoryStr);
                            if(daoStr.contains(importStr)){
                                daoStr=daoStr.replaceAll("\\s*"+importStr+"\\s*","\n");
                            }
                            if(daoStr.contains(repositoryStr)){
                                daoStr=daoStr.replaceAll("\\s*"+repositoryStr,"\n");
                            }
                            stringBuffer= new StringBuffer(daoStr);

                            //--------------------------这里执行相关的替换----------------------------
                            //iServiceFile = new File(daoPath.substring(0,daoPath.length()-4) + "\\service\\" + "I"+tableName+"Service.java");
                            iServiceFile = new File(iServicePath + "\\" + "I"+tableName+"Service.java");
                            System.out.println("-----iServiceFile:"+iServiceFile.toString());
                            fileWriter = new FileWriter(iServiceFile);
                            bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(daoStr);//自带创建文件功能，但不能创建文件夹
                            bufferedWriter.flush();
                            fileWriter.flush();
                        }
                        else {
                            System.out.println("mapperFile是文件夹不是文件");
                        }
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!flag){
                System.out.println("指定的dao文件不存在");
            }
            else {
                flag = false;
            }
        }
        try{
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(fileWriter!=null){
                fileWriter.close();
            }
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //iservice生成service
    public void iServiceToService(Object... tableNames){
        if(this.tableName==null){
            if(tableNames[0]!=null){
                setTableName(tableNames[0].toString());
            }
        }
        if(this.tableNameArr==null){
            this.tableNameArr = new String[tableNames.length];
            for (int i = 0; i <tableNames.length ; i++) {
                tableNameArr[i]= StringIndex.upFirstWord(tableNames[i].toString());
            }
        }
        File iServiceFolderFile = new File(iServicePath);
        File serviceFolderFile = new File(servicePath);
        serviceFolderFile.mkdirs();
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        StringBuffer stringBuffer = null;
        String[] iServiceFileStrArr = iServiceFolderFile.list();/*返回所有的文件名*/
        //File[] fileList = file.listFiles();//返回所有的文件对象    //getName()获取文件名
        System.out.println("-----iServiceFileStrArr:"+ Arrays.toString(iServiceFileStrArr));
        boolean flag = false;
        for (int j = 0; j <tableNameArr.length ; j++) {
            for (int i = 0; i < iServiceFileStrArr.length; i++) {
                if(("i"+tableNameArr[j].toLowerCase()+"service.java").equals(iServiceFileStrArr[i].toLowerCase())){
                    try {
                        flag = true;
                        tableName=tableNameArr[j];
                        File iServiceFile = new File(iServiceFolderFile.getCanonicalPath() + "\\" + iServiceFileStrArr[i]);
                        if (iServiceFile.isFile()) {//判断是否是文件
                            System.out.println("-----这是文件:" + iServiceFileStrArr[i]);
                            //读入文件
                            fileReader = new FileReader(iServiceFile);
                            bufferedReader = new BufferedReader(fileReader);
                            stringBuffer = new StringBuffer();
                            //String str = bufferedReader.readLine();/*使用行读取，后面的操作也必须全以行来操作*/
                            int unicode = bufferedReader.read();//读入硬盘文件里的一个字符，返回unicode编码。读取到末尾返回-1。
                            while (unicode != -1) {
                                stringBuffer.append((char) unicode);
                                //System.out.println(stringBuffer);//可以用%c输出数字对应的字符(System.out.printf("c%",num))
                                unicode = bufferedReader.read();//读取文件的下一个字符。
                            }
                            int indexLong = stringBuffer.toString().indexOf("Long");
                            int indexInteger = stringBuffer.toString().indexOf("Integer");
                            int indexBlob = stringBuffer.toString().indexOf("WithBLOBs");
                            //---------------------------这里执行相关的替换----------------------------
                            //tableName =iServiceFileStrArr[i].substring(0,iServiceFileStrArr[i].length()-12).substring(1);
                            //setTableName(tableName);
                            //tableName = this.tableName;
                            File serviceFile = null;
                            //String string = ServiceModel.servieReplace(tableName);
                            String string = servieReplace(tableName);
                            if(indexLong>0){
                                string = string.replaceAll("Integer","Long");
                            }
                            if(indexInteger<0&&indexLong<0){
                                string = string.replaceAll("Integer","String");
                            }
                            //如果iservice中有updateByPrimaryKeyWithBLOBs，则在impl中也增加
                            if(indexBlob>0){
                                int indexWithBLOBs = StringIndex.patternIndexOf(string,"WithBLOBs");
                                if(indexWithBLOBs>0){
                                    String addStr = "    @Override\n" +
                                            "    public int updateByPrimaryKeyWithBLOBs("+tableName+" "+ StringIndex.lowerFirstWord(tableName)+") {\n" +
                                            "        return "+ StringIndex.lowerFirstWord(tableName)+daoLastName+".updateByPrimaryKeyWithBLOBs("+ StringIndex.lowerFirstWord(tableName)+");\n" +
                                            "    }\n";
                                    string = string.replaceAll("//WithBLOBs\n",addStr);
                                }
                            }
                            stringBuffer = new StringBuffer(string);
                            System.out.println("-----iServiceFileStrArr[i]:"+iServiceFileStrArr[i]);
                            System.out.println("-----tableName:"+tableName);//Testtype
                            System.out.println("-----stringBuffer:\n"+stringBuffer);
                            //---------------------------这里执行相关的替换----------------------------
                            //输出文件
                            serviceFile = new File(iServiceFolderFile.getCanonicalPath() + "\\"+serviceFolderName+"\\" + tableName+"Service.java");
                            System.out.println("-----serviceFile:"+serviceFile.toString());
                            fileWriter = new FileWriter(serviceFile);
                            bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(stringBuffer.toString());//自带创建文件功能，但不能创建文件夹

                            bufferedWriter.flush();
                            fileWriter.flush();
                        }
                        if (iServiceFile.isDirectory()) {
                            System.out.println("-----这是文件夹:" + iServiceFileStrArr[i]);
                        }
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(!flag){
                System.out.println("指定的iService文件不存在");
            }
            else {
                flag = false;
            }
        }
        try {
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(fileWriter!=null){
                fileWriter.close();
            }
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //replaceModel
    public String  servieReplace(String tableName){
        setTableName(tableName);
        tableName = this.tableName;
        String string = "package "+comName+"."+iServiceFolderName+"."+serviceFolderName+";\n" +
                "\n" +
                "import "+comName+"."+daoFolderName+".Manager"+ daoLastName +";\n" +
                "import "+comName+"."+entityName+".Manager;\n" +
                "import "+comName+"."+iServiceFolderName+".IManagerService;\n" +
                "import org.mybatis.spring.annotation.MapperScan;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "@Transactional\n" +
                "@MapperScan(basePackages = \""+comName+"."+daoFolderName+"\")\n" +
                "@Service(\"managerService\")\n" +
                "public class ManagerService implements  IManagerService{\n" +
                "    @Autowired\n" +
                "    private ManagerMapper managerMapper;\n" +
                "    @Override\n" +
                "    public int deleteByPrimaryKey(Integer mid) {\n" +
                "\n" +
                "        return managerMapper.deleteByPrimaryKey(mid);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int insert(Manager manager) {\n" +
                "\n" +
                "        return managerMapper.insert(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int insertSelective(Manager manager) {\n" +
                "        return managerMapper.insertSelective(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public Manager selectByPrimaryKey(Integer mid) {\n" +
                "        return managerMapper.selectByPrimaryKey(mid);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int updateByPrimaryKeySelective(Manager manager) {\n" +
                "        return managerMapper.updateByPrimaryKeySelective(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int updateByPrimaryKey(Manager manager) {\n" +
                "        return managerMapper.updateByPrimaryKey(manager);\n" +
                "    }\n" +
                "//WithBLOBs\n"+
                "}\n";
        StringBuffer stringBuffer = new StringBuffer(string);

        String oldTableName = "Manager";//截取ManagerService中的Manager
        String strServiceBack = "";//要返回的已被替换的文件内容
        String oldTableNameLowerCase = oldTableName.toLowerCase();
        String tableNameToLowerCase = tableName.toLowerCase();/*小写*/
//        String objectBeanService = objectToLowerCase+"Service";
//        String objectClassService = object+"Service";
//        String objectInterfaceService = "I"+tableName+"Service";
        String oldTableNameMapper = oldTableName+ daoLastName;
        String oldTableNamemapper = oldTableNameLowerCase+ daoLastName;
        String objectMapper = tableName+ daoLastName;
        String objectmapper = tableNameToLowerCase+ daoLastName;
        String strServiceRead = stringBuffer.toString();//读取的文件的内容
//        String strServiceReadid = strServiceRead.substring(0,1)+"id";
        String objectId = tableNameToLowerCase.substring(0,1)+"id";
        int index = strServiceRead.indexOf("private");
        if(index==-1){
            int index2 = strServiceRead.indexOf("Service {");
            strServiceRead = strServiceRead.substring(0,index2+9)+"\n\t@Autowired\n\tprivate "+objectMapper+" "+objectmapper+";"+strServiceRead.substring(index2+9);
            strServiceRead = strServiceRead.replaceAll(comName+"."+daoFolderName+".\\S+;",comName+"."+daoFolderName+".*;\n");
            strServiceRead = strServiceRead.replaceAll(comName+"."+entityName+".\\S+;\n",comName+"."+entityName+".*;\n");
            strServiceRead = strServiceRead.replaceAll("import "+comName+"."+iServiceFolderName+".\\S+;\n","import "+comName+"."+iServiceFolderName+".*;\n");
            strServiceRead = strServiceRead.replaceAll("@Service(\"\\S*\")","@Service(\""+tableNameToLowerCase+"Service\")");
            strServiceRead = strServiceRead.replaceAll("public class \\S+ implements \\S+Service","public class "+tableName+"Service implements"+" I"+tableName+"Service");
            strServiceRead = strServiceRead.replaceAll("private \\S+ \\S+;","private "+objectMapper+" "+objectmapper+";");
            strServiceRead = strServiceRead.replaceAll(" \\S*id\\)\\s*\\{"," "+objectId+") {");
            strServiceRead = strServiceRead.replaceAll("\\(\\S*id\\)","("+objectId+")");
            strServiceRead = strServiceRead.replaceAll("return \\S*"+daoLastName+".","return "+objectmapper+".");
            strServiceRead = strServiceRead.replaceAll("return \\S*Dao.","return "+objectmapper+".");
            strServiceRead = strServiceRead.replaceAll("return 0;","return "+objectmapper+".;");
            strServiceRead = strServiceRead.replaceAll("return null;","return "+objectmapper+".;");
            strServiceRead = strServiceRead.replaceAll(oldTableNameMapper,objectMapper);
            strServiceRead = strServiceRead.replaceAll(oldTableNamemapper,objectmapper);
            strServiceRead = strServiceRead.replaceAll(oldTableName,tableName);
            strServiceBack = strServiceRead.replaceAll(oldTableNameLowerCase,tableNameToLowerCase);
//            System.out.println("strServiceBack:\n"+strServiceBack);
        }
        else {
            strServiceRead = strServiceRead.replaceAll(comName+"."+daoFolderName+".\\S+;",comName+"."+daoFolderName+".*;\n");
            strServiceRead = strServiceRead.replaceAll(comName+"."+entityName+".\\S+;\n",comName+"."+entityName+".*;\n");
            strServiceRead = strServiceRead.replaceAll("import "+comName+"."+iServiceFolderName+".\\S+;\n","import "+comName+"."+iServiceFolderName+".*;\n");
            strServiceRead = strServiceRead.replaceAll("@Service(\"\\S*\")","@Service(\""+tableNameToLowerCase+"Service\")");
            strServiceRead = strServiceRead.replaceAll("public class \\S+ implements \\S+Service","public class "+tableName+"Service implements"+" I"+tableName+"Service");
            strServiceRead = strServiceRead.replaceAll("private \\S+ \\S+;","private "+objectMapper+" "+objectmapper+";");
            strServiceRead = strServiceRead.replaceAll(" \\S*id\\)\\s*\\{"," "+objectId+") {");
            strServiceRead = strServiceRead.replaceAll("\\(\\S*id\\)","("+objectId+")");
            strServiceRead = strServiceRead.replaceAll("return \\S*"+daoLastName+".","return "+objectmapper+".");
            strServiceRead = strServiceRead.replaceAll("return \\S*Dao.","return "+objectmapper+".");
            strServiceRead = strServiceRead.replaceAll("return 0;","return "+objectmapper+".;");
            strServiceRead = strServiceRead.replaceAll("return null;","return "+objectmapper+".;");
            strServiceRead = strServiceRead.replaceAll(oldTableNameMapper,objectMapper);
            strServiceRead = strServiceRead.replaceAll(oldTableNamemapper,objectmapper);
            strServiceRead = strServiceRead.replaceAll(oldTableName,tableName);
            strServiceBack = strServiceRead.replaceAll(oldTableNameLowerCase,tableNameToLowerCase);
//            System.out.println("strServiceBack:\n"+strServiceBack);
        }
        return strServiceBack;
    }
    //依次调用所需步骤的方法
    public void runFun(){
        if(flagDel){
            generator();//生成配置文件,每次的table不一样，所以每次都要生成
            runbat();//重新生成实体类
        }
        try {
            Thread.sleep(2000);//程序更新需要时间？使用join()无效！否则mapperFileStrArr获取不到generator()删除的daoMapper.java，1秒不够，
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapperToIService();
        iServiceToService();
    }
    //生成Controller
    public void controller(String actionName,String groupId,String tableNames) throws IOException {
        String tableName = "";
        if(tableNames==null||"".equals(tableNames)){
            tableNames = "s_role_permssion\n" +
                    "s_role\n" +
                    "credibilitylist\n" +
                    "s_user_role\n" +
                    "incomelist\n" +
                    "s_permssion\n" +
                    "accountlist\n" +
                    "userinfo";
        }
        String strController = "package com.action;\n" +
                "\n" +
                "import com.alibaba.fastjson.JSON;\n" +
                "import com.entity.T_user;\n" +
                "import com.service.IT_userService;\n" +
                "import com.utils.MapToBeanUtil;\n" +
                "import org.springframework.stereotype.Controller;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import org.springframework.web.context.ServletContextAware;\n" +
                "import org.springframework.web.multipart.MultipartFile;\n" +
                "\n" +
                "import javax.annotation.Resource;\n" +
                "import javax.servlet.ServletContext;\n" +
                "import javax.servlet.http.HttpServletRequest;\n" +
                "import java.io.File;\n" +
                "import java.util.Iterator;\n" +
                "import java.util.Map;\n" +
                "\n" +
                "@Controller\n" +
                "public class T_userController implements ServletContextAware {\n" +
                "    private static String primarynameKey = \"pidnamedjj\";\n" +
                "    private ServletContext application;\n" +
                "    @Resource\n" +
                "    private IT_userService iT_userService;\n" +
                "    @Override\n" +
                "    public void setServletContext(ServletContext servletContext) {\n" +
                "        this.application = servletContext;\n" +
                "    }\n" +
                "    @CrossOrigin\n" +
                "    @ResponseBody\n" +
                "    @RequestMapping(value = \"/t_userInsert\",produces = \"application/json;chart=UTF-8\")\n" +
                "    public String t_userInsert(HttpServletRequest request, @RequestParam(required = false) Map<String,Object> params,@RequestParam(required = false) MultipartFile[] excelfiledjj){\n" +
                "        System.out.println(\"-----params36:\"+params.toString());\n" +
                "        Iterator iterator = params.keySet().iterator();\n" +
                "        while (iterator.hasNext()){\n" +
                "            String entryKey = iterator.next().toString();\n" +
                "            if(entryKey.equals(\"driverNamedjj\")||entryKey.equals(\"datasourceUrldjj\")||entryKey.equals(\"userNamedjj\")||entryKey.equals(\"passworddjj\")||entryKey.equals(\"pronamedjj\")||entryKey.equals(\"tabnamedjj\")||entryKey.contains(\"pidnamedjj\")||entryKey.equals(\"acttypedjj\")||entryKey.equals(\"excelfiledjj\")){\n" +
                "                iterator.remove();\n" +
                "            }\n" +
                "        }\n" +
                "        T_user t_user = (T_user)MapToBeanUtil.backInstanceMapBean(new T_user(),params);\n" +
                "        int i = iT_userService.insertSelective(t_user);\n" +
                "        return JSON.toJSONString(i);\n" +
                "    }\n" +
                "    @CrossOrigin\n" +
                "    @ResponseBody\n" +
                "    @RequestMapping(value = \"/t_userDelete\",produces = \"application/json;chart=UTF-8\")\n" +
                "    public String t_userDelete(HttpServletRequest request, @RequestParam(required = false) Map<String,Object> params){\n" +
                "        System.out.println(\"-----params:\"+params.toString());\n" +
                "        String primaryname = request.getParameter(primarynameKey+1);\n" +
                "        String primaryval = params.get(primaryname).toString();\n" +
                "        int i = iT_userService.deleteByPrimaryKey(Integer.valueOf(primaryval));\n" +
                "        return JSON.toJSONString(i);\n" +
                "    }\n" +
                "    @CrossOrigin\n" +
                "    @ResponseBody\n" +
                "    @RequestMapping(value = \"/t_userUpdate\",produces = \"application/json;chart=UTF-8\")\n" +
                "    public String t_userUpdate(HttpServletRequest request, @RequestParam(required = false) Map<String,Object> params,@RequestParam(required = false) MultipartFile[] excelfiledjj) throws Exception {\n" +
                "        System.out.println(\"-----params:\"+params.toString());\n" +
                "        String path = application.getRealPath(\"img\")+ File.separator;\n" +
                "        System.out.println(\"-----img/product:\"+path);\n" +
                "        //List<HashMap<String,Object>> mapList = PoiUtil.inxlsx(excelfiledjj);//把接收的文件中的数据转为listmap。\n" +
                "        Iterator iterator = params.keySet().iterator();\n" +
                "        while (iterator.hasNext()){\n" +
                "            String entryKey = iterator.next().toString();\n" +
                "            if(entryKey.equals(\"driverNamedjj\")||entryKey.equals(\"datasourceUrldjj\")||entryKey.equals(\"userNamedjj\")||entryKey.equals(\"passworddjj\")||entryKey.equals(\"pronamedjj\")||entryKey.equals(\"tabnamedjj\")||entryKey.contains(\"pidnamedjj\")||entryKey.equals(\"acttypedjj\")||entryKey.equals(\"excelfiledjj\")){\n" +
                "                iterator.remove();\n" +
                "            }\n" +
                "        }\n" +
                "        T_user t_user = (T_user) MapToBeanUtil.backInstanceMapBean(new T_user(),params);\n" +
                "        int i = iT_userService.updateByPrimaryKeySelective(t_user);\n" +
                "        return JSON.toJSONString(i);\n" +
                "    }\n" +
                "    @CrossOrigin\n" +
                "    @ResponseBody\n" +
                "    @RequestMapping(value = \"/t_userSelect\",produces = \"application/json;chart=UTF-8\")\n" +
                "    public String t_userSelect(HttpServletRequest request, @RequestParam(required = false) Map<String,Object> params){\n" +
                "        System.out.println(\"-----params:\"+params.toString());\n" +
                "        String primaryname = request.getParameter(primarynameKey+1);\n" +
                "        String primaryval = params.get(primaryname).toString();\n" +
                "        T_user t_user = iT_userService.selectByPrimaryKey(Integer.valueOf(primaryval));\n" +
                "        return JSON.toJSONString(t_user);\n" +
                "    }\n" +
                "}";
        if(this.actionPath==null||"".equals(this.actionPath)){
            if(this.oneUpdatePath==null||"".equals(this.oneUpdatePath)){
                this.oneUpdatePath = this.oneUpdateURL.toString();
                System.out.println("oneUpdateURLStr:"+oneUpdatePath);
            }
            this.oneUpdatePath=this.oneUpdatePath.replaceAll("file:/","");
            this.oneUpdatePath=this.oneUpdatePath.replaceAll("target/classes","src/main/java");
            //oneUpdatePath=D:/workspace/idea/guo/zufang/src/main/java/com/utils/
            String utils = StringIndex.substringLastIndexOf(this.oneUpdatePath,"/",1,2);
            this.actionPath = this.oneUpdatePath.replaceAll(utils, actionName);
            File actionPathFolderFile = new File(this.actionPath);
            //文件夹路径不存在
            if (!actionPathFolderFile.exists() && !actionPathFolderFile.isDirectory()) {
                actionPathFolderFile.mkdirs();
                logger.info("文件夹路径不存在，创建路径:" + actionPath);
            }
            System.out.println("-----actionPath:"+this.actionPath);
        }

        File file = null;
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        StringBuffer stringBuffer = new StringBuffer(strController);

        if(tableNames.contains("\n")){
            String[] tableArr = tableNames.split("\n");
            for (int i = 0; i <tableArr.length ; i++) {
                tableArr[i]= StringIndex.upFirstWord(tableArr[i]);
                tableName=tableArr[i];
                String newStr = strController.replaceAll("T_user", StringIndex.upFirstWord(tableName));
                newStr = newStr.replaceAll("t_user", StringIndex.lowerFirstWord(tableName));
                newStr = newStr.replaceAll("com\\.",groupId+".");
                String conPath = actionPath+"/"+ StringIndex.upFirstWord(tableName)+"Controller.java";
                file = new File(conPath);
                fileWriter = new FileWriter(file);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(newStr);
                bufferedWriter.flush();
            }
        }
        else{
            tableNames= StringIndex.upFirstWord(tableNames);
            tableName=tableNames;
            String newStr = strController.replaceAll("T_user", StringIndex.upFirstWord(tableName));
            newStr = newStr.replaceAll("t_user", StringIndex.lowerFirstWord(tableName));
            newStr = newStr.replaceAll("com\\.",groupId+".");
            String conPath = actionPath+"/"+ StringIndex.upFirstWord(tableName)+"Controller.java";
            file = new File(conPath);
            fileWriter = new FileWriter(file);//能创建文件，不能创建文件夹。
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newStr);
            bufferedWriter.flush();
        }
        bufferedWriter.close();
        fileWriter.close();

    }
    public static void main(String[] args) throws IOException {
        //手动配置好config.properties
        //String daoFolderName,String daoLastName,String serviceFolderName,Object... tableNames
//        String tableStr="t_decisemanagetable\n" + "t_flatequip_correspond";
        String tableStr = "s_role_permssion\n" +
                "s_role\n" +
                "credibilitylist\n" +
                "s_user_role\n" +
                "incomelist\n" +
                "s_permssion\n" +
                "accountlist\n" +
                "userinfo";
        //读取配置文件值https://blog.csdn.net/jiangyu1013/article/details/82188593,能读a.bc=3和a.c: 4格式，不分yml或properties。但是不支持getProperty读取不在同一行的bootstrap.yml格式！
        //执行前，必须先build生成target,否则无法获取路径，使用mysql5，不要用6和8.要改配置。
        //OneUpdate oneUpdate = new OneUpdate("config.properties","mysql-connector-java-5.1.20.jar", "dao","Mapper", "service","impl",true,tableStr);
        OneUpdate oneUpdate = new OneUpdate("config.properties","mysql-connector-java-5.1.20.jar", "dao","entity","Mapper", "service","impl",true,tableStr);
        //根据传入的表(一个或多个)进行重新生成该表的相关信息，tableNames在调用时指定.
        oneUpdate.runFun();//最后输出-----serviceFile，生成xml配置文件，生成实体类，生成服务接口，实现接口，可拆分执行。

        //生成controller,只调用其中的一个方法。生成最后的控制层，前面的注消。//不需要依赖其它属性，因些不初始化有参构造。
//        OneUpdate oneUpdate = new OneUpdate();
        //oneUpdate.controller("D:\\workspace\\idea\\springcloud\\f8xn\\autof8\\src\\main\\java\\com\\action","t_decisemanagetable");
        //可以不指定actionPath,tableNames要么在方法里指定，要么调用时指定.
        oneUpdate.controller("action","com",tableStr);
    }
}

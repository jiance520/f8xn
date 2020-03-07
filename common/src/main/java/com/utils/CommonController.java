package com.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommonController implements ServletContextAware {
    //slf4j与log4j、log4j2:https://blog.csdn.net/HarderXin/article/details/80422903?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
    //Spring Boot 日志配置(超详细),https://blog.csdn.net/Inke88/article/details/75007649?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
    //SpringBoot 项目中使用Log4j2详细（避坑） https://blog.csdn.net/RyanDon/article/details/82589989

    //slf4j是一个适配器(是一个为Java程序提供日志输出的统一接口，就比如JDBC一样，单独的slf4j是不能工作的，必须搭配其他log4j或者log4j2),可助log4j 1.x升级log4j2。slf4j-api
    //log4j是真正实现日志功能的产品，是apache实现的一个开源日志java组件,log4j.jar log4j-api+log4j-core
    //logback-spring.xml是springboot自带spring-boot-starter-logging(不用添加依赖),springboot不支持log4j,支持log4j2。
    //logback同样是由log4j的作者设计完成的，用来取代log4j的一个日志框架，是slf4j的原生实现
    //Log4j2是log4j 1.x和logback的改进版，(无锁异步、更快)，spring-boot-starter-log4j2
    //slf4j与log4j2的桥接包：log4j-slf4j-impl
    //使用slf4j接口的好处是当项目需要更换日志框架的时候，只需要更换jar和配置，不需要更改相关java代码
    //日志级别从低到高分为：TRACE < DEBUG < INFO < WARN < ERROR < FATAL。
    //Spring Boot中默认配置ERROR、WARN和INFO级别的日志输出到控制台。
    //Spring Boot默认情况下，将日志输出到控制台，不会写到日志文件。
    //Spring Boot官方推荐优先使用带有-spring的文件名作为你的日志配置（如使用logback-spring.xml
    //Logback：logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy
    //Log4j：log4j-spring.properties, log4j-spring.xml, log4j.properties, log4j.xml
    //Log4j2：log4j2-spring.xml, log4j2.xml
    //log4j、logback、log4j2都是一种日志具体实现框架，所以既可以单独使用也可以结合slf4j一起搭配使用
    //Log4j2：如项目中有导入spring-boot-starter-web依赖包记得去掉spring自带的日志依赖spring-boot-starter-logging

    //org.slf4j.Logger用于项目启动时输出日志
    //private final Logger logger = LoggerFactory.getLogger(getClass());
    //slf4j+logback直接启动类能在控制台输出日志
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    //org.apache.log4j.Logger 使用1万最多，最后更新2012年，启动类不能在控制台输出日志,只在项目启动时才在控制台输出和保存到文件。
    //private static Logger logger = Logger.getLogger(CommonController.class.getName());
    //private Logger logger = Logger.getLogger(getClass());
    private ServletContext application;
    /*@Value("spring.datasource.driver-class-name")
    private String driverClassName="com.mysql.jdbc.Driver";
    @Value("spring.datasource.url")
    private String datasourceUrl="jdbc:mysql://localhost:3306/shiro?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8";
    @Value("spring.datasource.username")
    private String datasourceUsername="root";
    @Value("spring.datasource.password")
    private String datasourcePassword="root";*/
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.application = servletContext;
    }
    //private JdbcUtil jdbcUtil = new JdbcUtil(driverClassName,datasourceUrl,datasourceUsername,datasourcePassword);
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/selectAll",produces = "application/json;chart=UTF-8")
    public String selectAll(HttpServletRequest request){
        String tablename = request.getParameter("tablename");
        String backStr = null;
        if(tablename==null||"".equals(tablename)){
            backStr="tablename为空";
        }
       else{
            String sql = "select * from "+tablename;
            List<HashMap> hashMaps = JdbcUtil.exectueQuery(sql);
            backStr= JSON.toJSONString(hashMaps);
        }
        return backStr;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/selectOne",produces = "application/json;chart=UTF-8")
    public String selectOne(HttpServletRequest request){
        String tablename = request.getParameter("tablename");
        String primaryname = request.getParameter("primaryname");
        String primaryval = request.getParameter("primaryval");
        String backStr = null;
        if(tablename==null||"".equals(tablename)){
            backStr="tablename为空,";
        }
        if(primaryname==null||"".equals(primaryname)){
            backStr=backStr+"primaryname为空,";
        }
        if(primaryval==null||"".equals(primaryval)){
            backStr=backStr+"primaryval为空";
        }
        if(backStr==null){
            String sql = "SELECT * FROM "+tablename+" WHERE "+primaryname+" = "+primaryval;
            HashMap hashMaps = JdbcUtil.queryOne(sql);
            backStr = JSON.toJSONString(hashMaps);
        }
        return backStr;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteOne",produces = "application/json;chart=UTF-8")
    public Object deleteOne(HttpServletRequest request){
        String tablename = request.getParameter("tablename");
        String primaryname = request.getParameter("primaryname");
        String primaryval = request.getParameter("primaryval");
        String backStr = null;
        if(tablename==null||"".equals(tablename)){
            backStr="tablename为空,";
        }
        if(primaryname==null||"".equals(primaryname)){
            backStr=backStr+"primaryname为空,";
        }
        if(primaryval==null||"".equals(primaryval)){
            backStr=backStr+"primaryval为空";
        }
        if(backStr==null){
            String sql = "DELETE FROM "+tablename+" WHERE "+primaryname+" = "+primaryval;
            int i = JdbcUtil.executeUpdate(sql);
            backStr =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        }
        return backStr;
    }
   //不要把自增的主键，以及后台要加入的时间，传给后台。除必须key，key必须跟表字段一样。不能多也不能少。保证顺序。
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/insertOne",produces = "application/json;chart=UTF-8")
    public String insertOne(HttpServletRequest request,@RequestParam(required = false) Map<String,Object> params){
        String sql = "";
        String tablename = request.getParameter("tablename");
        logger.info("-----params:"+params.toString());

        sql="INSERT INTO "+tablename+" VALUES(";
        for(HashMap.Entry<String,Object> entry:params.entrySet()){
            String entryKey = entry.getKey();
            if(!entryKey.equals("tablename")){
                sql = sql +",\""+ entry.getValue().toString()+"\"";
            }
        }
        sql=sql+")";
        sql=sql.replaceAll("\\(,","(");
        System.out.println("-----insertOnesql:"+sql);
        int i = JdbcUtil.executeUpdate(sql);
        String str =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        logger.info("end");
        return str;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/insertOneAutoId",produces = "application/json;chart=UTF-8")
    public String insertOneAutoId(HttpServletRequest request,@RequestParam(required = false) Map<String,Object> params){
        String sql = "";
        System.out.println("-----params:"+params.toString());
        String tablename = request.getParameter("tablename");
        logger.info("-----params:"+params.toString());

        sql="INSERT INTO "+tablename+" VALUES(DEFAULT";
        for(HashMap.Entry<String,Object> entry:params.entrySet()){
            String entryKey = entry.getKey();
            if(!entryKey.equals("tablename")){
                sql = sql +",\""+ entry.getValue().toString()+"\"";
            }
        }
        sql=sql+")";
        System.out.println("-----insertOnesql:"+sql);
        int i = JdbcUtil.executeUpdate(sql);
        String str =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        return str;
    }
    //不要把自增的主键，以及后台要加入的时间，传给后台。除必须key，key必须跟表字段一样。不能多也不能少。保证顺序。
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/updateOne",produces = "application/json;chart=UTF-8")
    public String updateOne(HttpServletRequest request,@RequestParam(required = false) Map<String,Object> params){
        String sql = "";
        String tablename = request.getParameter("tablename");
        String primaryname = request.getParameter("primaryname");
        String primaryval = request.getParameter(primaryname);

        sql="UPDATE "+tablename+" SET ";
        for(HashMap.Entry<String,Object> entry:params.entrySet()){
            String entryKey = entry.getKey();
            if(!entryKey.equals("tablename")&&!entryKey.equals("primaryname")&&!entryKey.equals(primaryname)){
                sql = sql +entry.getKey()+"=\""+ entry.getValue().toString()+"\",";
            }
        }
        sql = sql.substring(0,sql.length()-1);//去掉最后一个,号
        sql=sql+" WHERE "+primaryname+"='"+primaryval+"'";
        System.out.println("-----primaryname:"+primaryname);
        System.out.println("-----insertOnesql:"+sql);
        int i = JdbcUtil.executeUpdate(sql);
        String str =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        return str;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/actionAll",produces = "application/json;chart=UTF-8")
    public Object actionAll(HttpServletRequest request,@RequestParam(required=false) Map<String,Object> params) {
        Object object = null;
        if(params.isEmpty()){
            object = "params是空";
        }
        else{
            object = ActionUtil.actionAll(this,params);
            System.out.println("-----object:"+object.toString());
        }
        return object;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/actionAllTwo",produces = "application/json;chart=UTF-8")
    public Object actionAllTwo(HttpServletRequest request,@RequestParam(required=false) Map<String,Object> params) {
        Object object = null;
        if(params.isEmpty()){
            object = "params是空";
        }
        else{
            object = ActionUtil.actionAllTwo(this,params);
        }
        return object;
    }
    public static void main(String[] args) throws Exception {
//        LoginController loginController = new LoginController();
//        loginController.action();
        //测试前，一定要注入Bean,不包括JdbcUtil
        String name = "tom";
        String password = "123";
//        String sql = "INSERT INTO t_user VALUES(2,?,?)";
        //String sql = "INSERT INTO t_user VALUES(2,\"tom\",\"123\")";
//        int i = JdbcUtil.executeUpdate(sql,name,password);
//        int i = JdbcUtil.executeUpdate(sql);
//        System.out.println("-----i:"+i);
        Object object = JdbcUtil.exectueQuery("select * from t_user");
        logger.debug("--logger---test:"+object);
        /*logger.trace("日志输出 trace");
        logger.debug("日志输出 debug");
        logger.info("日志输出 info");
        logger.warn("日志输出 warn");
        logger.error("日志输出 error");*/
        System.out.println("---test:"+object);
    }
}

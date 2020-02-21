package com.utils;

import com.alibaba.fastjson.JSON;
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
    private ServletContext application;
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.application = servletContext;
    }
    private JdbcUtil jdbcUtil = new JdbcUtil("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/epet?useSSL=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8","root","root");
//    @CrossOrigin
//    @ResponseBody
//    @RequestMapping(value = "/selectAll",produces = "application/json;chart=UTF-8")
//    public String selectAll(HttpServletRequest request){
//        String tablename = request.getParameter("tablename");
//        if(tablename==null){
//            System.out.println("-----tablename:"+tablename);
//            tablename= "作战力量管理表";
//        }
//        String sql = "select * from "+tablename;
//        List<HashMap> hashMaps = jdbcUtil.exectueQuery(sql);
//        return JSON.toJSONString(hashMaps);
//    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/selectOne",produces = "application/json;chart=UTF-8")
    public String selectOne(HttpServletRequest request){
        String tablename = request.getParameter("tablename");
        String primaryname = request.getParameter("primaryname");
        String primaryval = request.getParameter("primaryval");
        if(tablename==null){
            tablename= "t_decisemanagetable";
        }
        if(primaryname==null){
            primaryname= "deciseID";
        }
        if(primaryval==null){
            primaryval= "13";
        }
        String sql = "SELECT * FROM "+tablename+" WHERE "+primaryname+" = "+primaryval;
        HashMap hashMaps = jdbcUtil.queryOne(sql);
        return JSON.toJSONString(hashMaps);
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/deleteOne",produces = "application/json;chart=UTF-8")
    public Object deleteOne(HttpServletRequest request){
        String tablename = request.getParameter("tablename");
        String primaryname = request.getParameter("primaryname");
        String primaryval = request.getParameter("primaryval");
        if(tablename==null){
            tablename= "t_decisemanagetable";
        }
        if(primaryname==null){
            primaryname= "deciseID";
        }
        if(primaryval==null){
            primaryval= "12";
        }
        String sql = "DELETE FROM "+tablename+" WHERE "+primaryname+" = "+primaryval;
        int i = jdbcUtil.executeUpdate(sql);
        String str =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        return str;
    }
   //不要把自增的主键，以及后台要加入的时间，传给后台。除必须key，key必须跟表字段一样。不能多也不能少。保证顺序。
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/insertOne",produces = "application/json;chart=UTF-8")
    public String insertOne(HttpServletRequest request,@RequestParam(required = false) Map<String,Object> params){
        String sql = "";
        String tablename = request.getParameter("tablename");

//        params.put("deciseName","dd");
//        params.put("redblueF","21");
//        params.put("deciseAuthorName","sdf");
//        params.put("tablename","t_decisemanagetable");
//        if(tablename==null){
//            tablename = params.get("tablename").toString();
//        }

        sql="INSERT INTO "+tablename+" VALUES(DEFAULT";
        for(HashMap.Entry<String,Object> entry:params.entrySet()){
            String entryKey = entry.getKey();
            if(!entryKey.equals("tablename")){
                sql = sql +",'"+ entry.getValue().toString()+"'";
            }
        }
        sql=sql+")";
        System.out.println("-----insertOnesql:"+sql);
        int i = jdbcUtil.executeUpdate(sql);
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

//        params.put("deciseID","21");
//        params.put("deciseName","dd");
//        params.put("redblueF","22");
//        params.put("deciseAuthorName","sdf");
//        params.put("tablename","t_decisemanagetable");
//        params.put("primaryname","deciseID");
//        if(tablename==null){
//            tablename = params.get("tablename").toString();
//        }
//        if(primaryname==null){
//            primaryname = params.get("primaryname").toString();
//        }
//        if(primaryval==null){
//            primaryval = params.get(primaryname).toString();
//        }
        sql="UPDATE "+tablename+" SET ";
        for(HashMap.Entry<String,Object> entry:params.entrySet()){
            String entryKey = entry.getKey();
            if(!entryKey.equals("tablename")&&!entryKey.equals("primaryname")&&!entryKey.equals(primaryname)){
                sql = sql +entry.getKey()+"='"+ entry.getValue().toString()+"',";
            }
        }
        sql = sql.substring(0,sql.length()-1);//去掉最后一个,号
        sql=sql+" WHERE "+primaryname+"='"+primaryval+"'";
        System.out.println("-----primaryname:"+primaryname);
        System.out.println("-----insertOnesql:"+sql);
        int i = jdbcUtil.executeUpdate(sql);
        String str =  JSON.toJSONString(i);//return JSONSerializer.toJSON(json);
        return str;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/actionAll",produces = "application/json;chart=UTF-8")
    public Object actionAll(HttpServletRequest request,@RequestParam(required=false) Map<String,Object> params) {
        if(params.isEmpty()){
            params = new HashMap();
            params.put("tablename","t_decisemanagetable");//必须
            params.put("actiontype","selectByPrimaryKey");//必须
            params.put("primaryname1","deciseID");//删除和增加，
            params.put("primaryname2","w_equip_id");//删除和增加，
            params.put("deciseID",1);//不能插入重复的值。
            params.put("deciseName","deciseName2");
            params.put("redblueF",22);
            params.put("deciseAuthorName","deciseAuthorName1");
        }
        Object object = ActionUtil.actionAll(params);
        return object;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/actionAllTwo",produces = "application/json;chart=UTF-8")
    public Object actionAllTwo(HttpServletRequest request,@RequestParam(required=false) Map<String,Object> params) {
//        if(params.isEmpty()){
//            params = new HashMap();
//            params.put("tablename","t_flatequip_correspond");//必须
//            params.put("actiontype","selectByPrimaryKey");//必须
//            params.put("primaryname1","w_flat_id");//删除和增加，
//            params.put("primaryname2","w_equip_id");//删除和增加，
//            params.put("w_flat_id","10102");//删除和增加，
//            params.put("w_equip_id","40301");//删除和增加，
//            params.put("w_equip_type",402);
//            params.put("deciseID",16);//不能插入重复的值。
//        }
        Object object = ActionUtil.actionAllTwo(params);
        return object;
    }
    public static void main(String[] args) throws Exception {
//        LoginController loginController = new LoginController();
//        loginController.action();
    }
}

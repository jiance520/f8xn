//package com.android;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import guo.component.RedisClient;
//import guo.entity.Userinfo;
//import guo.redisconfig.SerializeUtil;
//import guo.service.IUserinfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.context.ServletContextAware;
//import org.springframework.web.servlet.ModelAndView;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import javax.annotation.Resource;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
////@RequestMapping("")
//public class controller implements ServletContextAware {
//    private ServletContext application;
//    @Resource
//    private IUserinfoService iUserinfoService;
//    @Autowired
//    private JedisPool jedisPool;
//    @Resource
//    private RedisClient redisClient;
//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        this.application = servletContext;
//    }
//    @CrossOrigin
//    @ResponseBody
//    @RequestMapping(value = "selectLogin",produces = "application/json;chart=UTF-8")
////    @RequestMapping("selectLogin")
//    public String selectLogin(HttpSession session, HttpServletRequest request) throws InterruptedException, IOException {
//        String userid = request.getParameter("AccountNumber");
//        String upasswd = request.getParameter("Password");
//        System.out.println("-----userid:"+userid);
//        System.out.println("-----upasswd:"+upasswd);
//        Map hashMap = new HashMap();
//        hashMap.put("userid",userid);
//        hashMap.put("upasswd",upasswd);
//        Userinfo userinfo = iUserinfoService.selectLogin(hashMap);
//        System.out.println("-----userinfo:"+userinfo);
//        HashMap paramsMap = new HashMap();
//        String backjson = null;
//        if(userinfo==null){
//            backjson = "fail";
//            System.out.println("用户名或密码错误");
//        }
//        else {
//            backjson = "success";
//            System.out.println("登陆成功");
////            Jedis jedis = jedisPool.getResource();
////            session.setAttribute("uid",userinfo.getUid());
////            session.setAttribute("passwd",userinfo.getPasswd());
////            jedis.set("userinfo".getBytes(), SerializeUtil.serialize(userinfo));
////            jedis.expire("userinfo", 3000);//有效期5分种
//////            Thread.sleep(6000);
////            System.out.println("-----userinfo.ttl:"+jedis.ttl("userinfo".getBytes()));//输入剩余时间
//        }
//        paramsMap.put("Result",backjson);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("params",paramsMap);
//        backjson = JSON.toJSONString(jsonObject);
//        return backjson;
//        //return "forward:/indexservlet";
//    }
//    @CrossOrigin
//    @ResponseBody
//    @RequestMapping("insertSelective")
//    public String insertSelective(HttpSession session, HttpServletRequest request) throws InterruptedException, IOException {
//        String userid = request.getParameter("AccountNumber");
//        String upasswd = request.getParameter("Password");
//        System.out.println("-----AccountNumber:"+userid);
//        HashMap paramsMap = new HashMap();
//        String backjson = null;
//        if(userid!=null&&!"".equals(userid)){
//            Userinfo userinfo1 = iUserinfoService.selectByPrimaryKey(userid);
//            if(userinfo1!=null){
//                backjson = "fail";
//                System.out.println("注册失败，帐号已存在");
//            }else{
//                Userinfo userinfo = new Userinfo();
//                userinfo.setUserid(userid);
//                userinfo.setUpasswd(upasswd);
//                int i = iUserinfoService.insertSelective(userinfo);
//                System.out.println("-----i:"+i);
//                if(i!=1){
//                    backjson = "fail";
//                    System.out.println("注册失败");
//                }
//                else {
//                    backjson = "success";
//                    System.out.println("注册成功");
//                }
//            }
//        }
//        paramsMap.put("Result",backjson);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("params",paramsMap);
//        backjson = JSON.toJSONString(jsonObject);
//        return backjson;
//    }
//    @CrossOrigin
//    @RequestMapping("/indexservlet")
//    public ModelAndView indexservlet(HttpServletRequest request, ModelAndView modelAndView){
//        Jedis jedis = jedisPool.getResource();
//        Userinfo userinfo = (Userinfo)SerializeUtil.unserialize(jedis.get("userinfo".getBytes()));
//        HashMap hashMap = new HashMap();
//        hashMap.put("userinfo",userinfo);
//        modelAndView.addAllObjects(hashMap);
//        modelAndView.setViewName("index2");
//        System.out.println("-----userinfo:"+userinfo.toString());
//        return modelAndView;
//    }
//}

package com.SMSCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSCode {
    //    把手机号码和随机生成的验证码传递过来
    public static boolean sendCode(String phoneNumber,String code) throws Exception{
        String code_Str = URLEncoder.encode("#code#="+code,"utf-8");//验证码
//        包装好URL对象，将接口地址包装在此对象中,在聚合数据申请的api模板ID，&tpl_id=105051。模板&key=54abf26eec4f21a38ec5f777d4a76219
        URL url = new URL("http://v.juhe.cn/sms/send?mobile="+phoneNumber+"&tpl_id=105051&tpl_value="+code_Str+"&key=54abf26eec4f21a38ec5f777d4a76219");
//        打开连接，得到连接对象
        URLConnection urlConnection = url.openConnection();
//        向服务器发出连接请求
        urlConnection.connect();
//        获得服务器响应的数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String lineData = null;
        while ((lineData=bufferedReader.readLine())!=null){
            stringBuffer.append(lineData);
        }
        bufferedReader.close();
        System.out.println("-----stringBuffer.toString():"+stringBuffer.toString());//{"reason":"操作成功","result":{"sid":"f9946f9cbc4b415c96317e2d2a7b8092","fee":1,"count":1},"error_code":0}
//        if(stringBuffer.toString().indexOf("\"error_code\":0")>=0){
        if(stringBuffer.toString().indexOf("\"error_code\":0")>=0){//如果"error_code":0，则成功，返回true，发送成功，如果值是205402，则失败
            return true;
        }
        return false;
    }
}


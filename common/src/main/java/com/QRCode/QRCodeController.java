package com.QRCode;

import com.QRCode.QRCodeUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;

@Controller
public class QRCodeController implements ServletContextAware{
    private ServletContext application;
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.application=servletContext;
    }
    @ResponseBody
    @RequestMapping(value = "qrCodeAction",produces = "application/json;chart=UTF-8")
    public String qrCodeAction(){
        System.out.println("执行qrCodeAction");
        String imagePath = "js/qrcode/img/qrcode.jpg";
        String text = "http://1553ve6443.imwork.net:11534/userAndPwd/alipay.jsp";
        QRCodeUtil.zxingCodeCreate(text, 300, 300, imagePath, "jpg");
        String str = null;
        try {
            str = QRCodeUtil.zxingCodeAnalyze(text);
            System.out.println("生成的二维码解析成功");
            String json = JSON.toJSONString(str);
            return imagePath;//+ File.separator;
        } catch (Exception e) {
            System.out.println("生成的二维码解析失败");
            e.printStackTrace();
            return null;
        }
    }
}

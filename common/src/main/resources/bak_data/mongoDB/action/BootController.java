package bak_data.mongoDB.action;


import bak_data.mongoDB.service.BaseService;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BootController {

    @Autowired
    private BaseService bs;

    @RequestMapping("/test")
    public ModelAndView test(ModelAndView mvo) throws Exception{
        System.out.println(" --- test ");

        List<BasicDBObject> list = bs.find("person", new BasicDBObject());
        for(BasicDBObject i : list){
            System.out.println(i.get("id"));
        }
        System.out.println("查询完成！");

        mvo.setViewName("success");
        return mvo;
    }
}

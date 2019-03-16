package com.test;


import com.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

//SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
//指定我们SpringBoot工程的App启动类
@SpringBootTest(classes = App.class)
//由于是Web项目，Junit需要模拟ServletContext，
// 因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class TestBean {
  /*  @Autowired
    private IManagerService imanagerService ;*/

    @Test
    @Transactional
    @Rollback
    public void testAll(){
//        List list = iProduct_romService.selectRomAll();
//        System.out.println(" list = "+list);
    }
}

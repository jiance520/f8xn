package com.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory factory = getFactory();
    private static final ThreadLocal<SqlSession> threadLocal = new  ThreadLocal<SqlSession>();
    //     * 根据核心配置文件获得SqlSessionFactory
    public static SqlSessionFactory getFactory() {
        try {
            InputStream inputStream =  Resources.getResourceAsStream("mybatis-config.xml");
            factory = (new  SqlSessionFactoryBuilder()).build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return factory;
    }
    public static SqlSession getSession()  {
//          SqlSession session=factory.openSession(false);false表示是开启事务控制。
        SqlSession session = (SqlSession) threadLocal.get();
        if (session == null) {
            session = (factory != null) ?  factory.openSession(false): null;//A?B:C
            threadLocal.set(session);
        }
        return session;
    }
    public static void closeSession() {
        SqlSession session = (SqlSession) threadLocal.get();
        threadLocal.set(null);
        if (session != null) {
            session.close();
        }
    }
}
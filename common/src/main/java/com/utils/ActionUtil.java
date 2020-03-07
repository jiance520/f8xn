package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

//必须获取主键，不能用于查询所有。
@Component
public class ActionUtil implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ActionUtil.class);
    private static String daoFolderName = "dao";
    private static String comName = "com";
    private static String entityName = "entity";
    private static String iServiceFolderName = "service";
    private static String serviceFolderName = "impl";
    private static URL oneUpdateURL = null;//当前类所在的本地URL。
    //URL oneUpdateURL = OneUpdate.class.getResource("");//当前类所在的本地URL。

    private static String tablenameKey = "tablename";
    private static String actiontypeKey = "actiontype";
    private static String primarynameKey = "primaryname";
    //private static String primaryname1Key = "primaryname1";//必须包含primaryname，且后面跟数字1表示单主键，两个主键用12区分
    //private static String primaryname2Key = "primaryname2";
    private static String selectByPrimaryKey = "selectByKey";
    private static String deleteByPrimaryKey = "deleteByKey";
    private static String insertSelective = "insertSelective";
    private static String updateByPrimaryKeySelective = "updateByKeySelective";
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ActionUtil.applicationContext == null){
            ActionUtil.applicationContext = applicationContext;
        }
    }
    public static Object actionAll(Object thisobj,Map<String,Object> jsonMap){
        if(oneUpdateURL==null){
            oneUpdateURL = thisobj.getClass().getResource("");
        }

        String oneUpdatePath = oneUpdateURL.toString();
        System.out.println("oneUpdateURLStr:"+oneUpdatePath);
        oneUpdatePath=oneUpdatePath.replaceAll("file:/","");
        oneUpdatePath=oneUpdatePath.replaceAll("target/classes","src/main/java");
        String utils = StringIndex.substringLastIndexOf(oneUpdatePath,"/",1,2);
        String daoPath = oneUpdatePath.replaceAll(utils, daoFolderName);
        File daoFolderFile = new File(daoPath);
        String[] mapperFileStrArr = daoFolderFile.list();/*返回所有的文件名*///TesttypeMapper.java,
        //System.out.println("-----mapperFileStrArr:"+ Arrays.toString(mapperFileStrArr));
        String tableName = jsonMap.get(tablenameKey).toString();
        tableName = tableName.substring(0,1).toUpperCase()+tableName.substring(1);
        String actionType = jsonMap.get(actiontypeKey).toString();
        String mapPrimaryName=jsonMap.get(primarynameKey+"1").toString();//deciseID/mrank_id
        //primaryval1必须转化为跟bean属性一样
        String beanIdName = MapToBeanUtil.mapKeyToField(mapPrimaryName);//deciseid
        String idVal = null;
        if(mapPrimaryName!=null){
            idVal = jsonMap.get(mapPrimaryName).toString();//15
        }
        System.out.println("-----primaryname1/mapPrimaryName:"+mapPrimaryName);
        System.out.println("-----beanIdName:"+beanIdName);
        System.out.println("-----idVal:"+idVal);

        Object invoke = null;
        try {
            Class<?> entityClass = Class.forName(comName+"."+entityName+"."+tableName);
            Constructor<?> entityConstructor = entityClass.getDeclaredConstructor();
            Object backBean = MapToBeanUtil.backBean(entityConstructor.newInstance(), jsonMap);
            if(tableName!=null&&!"".equals(tableName)){
                for (int i = 0; i <mapperFileStrArr.length ; i++) {
                    mapperFileStrArr[i]=mapperFileStrArr[i].replaceAll("Mapper.java","");//直接运行是.java，启动服务是.class
                    //mapperFileStrArr[i]=mapperFileStrArr[i].replaceAll("Mapper.class","");
                   logger.debug("--如报错，检查是.java还是.class---mapperFileStrArr[i]:"+mapperFileStrArr[i]);
                    if(mapperFileStrArr[i].equals(tableName)){
                        if(actionType.equals(selectByPrimaryKey)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            //Constructor<?> c = cs.getDeclaredConstructor();

                            //非自增主键，手写主键，数据库实际有主键
                            Class fieldType = null;
                            if(mapPrimaryName!=null&&!"".equals(mapPrimaryName)){
                                fieldType = MapToBeanUtil.mapAttrKeyType(backBean,beanIdName);//属性必须包含在实体类的bean中，否则抛异常,中止执行！
                            }
                            Method method = cs.getDeclaredMethod("selectByPrimaryKey",fieldType);
                            //System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            //IT_decisemanagetableService t_decisemanagetableService = (IT_decisemanagetableService)applicationContext.getBean(serviceBeanStr);//最好使用接口转型，必须启动服务才能获取
                            //T_decisemanagetableService t_decisemanagetableService = (T_decisemanagetableService)c.newInstance();//失败.InvocationTargetException: null
                            System.out.println("-----idVal:"+idVal);
                            System.out.println("-----backBean:"+backBean);
                            System.out.println("-----serviceBeanStr:"+serviceBeanStr);
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr), MapToBeanUtil.valToBeanVal(backBean,beanIdName,idVal));
                            System.out.println("-----invoke查询:"+invoke.toString());
                        }
                        else if(actionType.equals(insertSelective)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Method method = cs.getDeclaredMethod("insertSelective",entityClass);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr),backBean);
                            System.out.println("-----invoke插入:"+invoke.toString());
                        }
                        else if(actionType.equals(updateByPrimaryKeySelective)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Method method = cs.getDeclaredMethod("updateByPrimaryKeySelective",entityClass);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr),backBean);
                            System.out.println("-----invoke修改:"+invoke.toString());
                        }
                        else if(actionType.equals(deleteByPrimaryKey)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Class fieldType = null;
                            if(mapPrimaryName!=null&&!"".equals(mapPrimaryName)){
                                fieldType = MapToBeanUtil.mapAttrKeyType(backBean,beanIdName);//属性必须包含在实体类的bean中，否则抛异常,中止执行！
                            }

                            Method method = cs.getDeclaredMethod("deleteByPrimaryKey",fieldType);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);

                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr), MapToBeanUtil.valToBeanVal(backBean,beanIdName,idVal));
                            System.out.println("-----invoke删除:"+invoke.toString());
                        }
                        break;
                    }
                }
            }
            else {
                System.out.println("-----没有传过来tablename，调用通用接口失败");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return invoke;
    }
    public static Object actionAllTwo(Object thisobj,Map<String,Object> jsonMap){
        if(oneUpdateURL==null){
            oneUpdateURL = thisobj.getClass().getResource("");
        }
        String oneUpdatePath = oneUpdateURL.toString();
        System.out.println("oneUpdateURLStr:"+oneUpdatePath);
        oneUpdatePath=oneUpdatePath.replaceAll("file:/","");
        oneUpdatePath=oneUpdatePath.replaceAll("target/classes","src/main/java");
        String utils = StringIndex.substringLastIndexOf(oneUpdatePath,"/",1,2);
        String daoPath = oneUpdatePath.replaceAll(utils, daoFolderName);
        File daoFolderFile = new File(daoPath);
        String[] mapperFileStrArr = daoFolderFile.list();/*返回所有的文件名*///TesttypeMapper.java,
        //System.out.println("-----mapperFileStrArr:"+ Arrays.toString(mapperFileStrArr));
        String tableName = jsonMap.get(tablenameKey).toString();
        tableName = tableName.substring(0,1).toUpperCase()+tableName.substring(1);
        String actionType = jsonMap.get(actiontypeKey).toString();
        //String primaryval1 = jsonMap.get("primaryname1").toString().toLowerCase();
        ArrayList<String> arrMapKeyName = new ArrayList<String>();
        ArrayList<String> arrMapPrimaryName = new ArrayList<String>();
        ArrayList<String> arrBeanIdName = new ArrayList<String>();
        ArrayList arrIdVal = new ArrayList();
        Object invoke = null;
        try {
            Class<?> entityClass = Class.forName(comName+"."+entityName+"."+tableName);
            Constructor<?> entityConstructor = entityClass.getDeclaredConstructor();
            Object backBean = MapToBeanUtil.backBean(entityConstructor.newInstance(), jsonMap);
            int primaryNum = 0;
            //循环判断map中是否有多个key=primary1 primary2
            for(Map.Entry<String,Object> jsonMapEntry:jsonMap.entrySet()){
                String jsonMapAttrKey = jsonMapEntry.getKey();
                String jsonmapattrkey = jsonMapAttrKey.substring(0,jsonMapAttrKey.length()-1);
                if(jsonmapattrkey.equals(primarynameKey)){
                    primaryNum++;
                    arrMapKeyName.add(jsonmapattrkey+primaryNum);//jsonmapattrkey=primaryname
                    String mapPrimaryName = jsonMap.get(arrMapKeyName.get(primaryNum-1)).toString();//w_flat_id,w_equip_id
                    String beanIdName = MapToBeanUtil.mapKeyToField(mapPrimaryName);//wFlatId
                    arrMapPrimaryName.add(mapPrimaryName);//w_flat_id,w_equip_id
                    arrBeanIdName.add(beanIdName);//wFlatId,wEquipId
                    arrIdVal.add(jsonMap.get(arrMapPrimaryName.get(primaryNum-1)));
                }
            }
            System.out.println("-----arrMapKeyName:"+arrMapKeyName);
            System.out.println("-----arrMapPrimaryName:"+arrMapPrimaryName);
            System.out.println("-----arrBeanIdName:"+arrBeanIdName);
            System.out.println("-----arrIdVal:"+arrIdVal);
            if(tableName!=null&&!"".equals(tableName)){
                for (int i = 0; i <mapperFileStrArr.length ; i++) {
                    mapperFileStrArr[i]=mapperFileStrArr[i].replaceAll("Mapper.java","");//直接运行是.java，启动服务是.class
                    //mapperFileStrArr[i]=mapperFileStrArr[i].replaceAll("Mapper.class","");
                    logger.debug("--如报错，检查是.java还是.class---mapperFileStrArr[i]:"+mapperFileStrArr[i]);
                    if(mapperFileStrArr[i].equals(tableName)){
                        if(actionType.equals(selectByPrimaryKey)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            //Constructor<?> c = cs.getDeclaredConstructor();

                            //非自增主键，手写主键，数据库实际有主键
                            Class fieldKeyType1 = MapToBeanUtil.mapAttrKeyType(backBean,arrBeanIdName.get(0));//属性必须包含在实体类的bean中，否则抛异常,中止执行！
                            Class fieldKeyType2 = MapToBeanUtil.mapAttrKeyType(backBean,arrBeanIdName.get(1));
                            //for(String primaryname:arrMapKeyName){}
                            System.out.println("-----arrBeanIdName.get(0):"+arrBeanIdName.get(0));
                            Method method = cs.getDeclaredMethod("selectByPrimaryKey",fieldKeyType1,fieldKeyType2);
                            //System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            //IT_decisemanagetableService t_decisemanagetableService = (IT_decisemanagetableService)applicationContext.getBean(serviceBeanStr);//最好使用接口转型，必须启动服务才能获取
                            //T_decisemanagetableService t_decisemanagetableService = (T_decisemanagetableService)c.newInstance();//失败.InvocationTargetException: null
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr), MapToBeanUtil.valToBeanVal(backBean,arrBeanIdName.get(0),arrIdVal.get(0)), MapToBeanUtil.valToBeanVal(backBean,arrBeanIdName.get(1),arrIdVal.get(1)));
                            System.out.println("-----invoke查询:"+invoke.toString());
                        }
                        else if(actionType.equals(insertSelective)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Method method = cs.getDeclaredMethod("insertSelective",entityClass);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr),backBean);
                            System.out.println("-----invoke插入:"+invoke.toString());
                        }
                        else if(actionType.equals(updateByPrimaryKeySelective)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Method method = cs.getDeclaredMethod("updateByPrimaryKeySelective",entityClass);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);
                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr),backBean);
                            System.out.println("-----invoke修改:"+invoke.toString());
                        }
                        else if(actionType.equals(deleteByPrimaryKey)){
                            Class<?> cs = Class.forName(comName+"."+iServiceFolderName+"."+serviceFolderName+"."+mapperFileStrArr[i]+"Service");//+mapperFileStrArr[i]+"Service"
                            Class fieldKeyType1 = MapToBeanUtil.mapAttrKeyType(backBean,arrBeanIdName.get(0));
                            Class fieldKeyType2 = MapToBeanUtil.mapAttrKeyType(backBean,arrBeanIdName.get(1));

                            Method method = cs.getDeclaredMethod("deleteByPrimaryKey",fieldKeyType1,fieldKeyType2);
                            System.out.println("-----method:"+method.toString());
                            String serviceBeanStr = mapperFileStrArr[i]+"Service";
                            serviceBeanStr=serviceBeanStr.substring(0,1).toLowerCase()+serviceBeanStr.substring(1);

                            invoke = method.invoke(applicationContext.getBean(serviceBeanStr), MapToBeanUtil.valToBeanVal(backBean,arrBeanIdName.get(0),arrIdVal.get(0)), MapToBeanUtil.valToBeanVal(backBean,arrBeanIdName.get(1),arrIdVal.get(1)));
                            System.out.println("-----invoke删除:"+invoke.toString());
                        }
                        break;
                    }
                }
            }
            else {
                System.out.println("-----没有传过来tablename，调用通用接口失败");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return invoke;
    }
}

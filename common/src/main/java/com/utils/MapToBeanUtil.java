package com.utils;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MapToBeanUtil {
    //对于数字是什么类型，必须先手动指定，才能判断。字符串解析parse为指定封装类型
    //支持Map中的字符串和Object。
    //返回的bean对象，不一定有值，可能是一个空对象。
    //复制一个对象的所有属性值给另一个对象BeanUtils.copyProperties(testtype,bean);，不能复制map。
    //不能转换泛型，List,Map,数组等其它类型，只转换数据库的类型。

    //Map转实体，接收前端的map，存入实体，用于插入表中,方法的判断是针对字符串，对于非字符串原类型，直接使用field.set(bean,objectValue);！
    //map中多余的不在bean中的字段不处理。关键字区分大小写
    public static <T> Object backBean(T bean,Map<String,Object> params){
        Map<String,Object> beanMap = backMap(bean);//默认返回的mapkey是bean属性，小写+大写(假如字段含下划线)
        System.out.println("-----params:"+params);
        //遍历接收的paramsMap，再遍历beanMap，如果前者的key没有在后者里面，则从前者里面移除。
        try {
            for(HashMap.Entry<String,Object> entry:params.entrySet()){
                String mapAttrKey = null;//bigint1=field.getName();
                String beanField = null;
                //System.out.println("-----mapAttrKey:"+mapAttrKey);
                boolean flag = false;
                for(HashMap.Entry<String,Object> beanEntry:beanMap.entrySet()){
                    mapAttrKey = entry.getKey();
                    String beanAttrKey = beanEntry.getKey();//bigint1=field.getName();
                    //System.out.println("-----beanEntry:"+beanEntry);
                    //beanEntry格式:aB，mapAttrKey格式“a_b
                    if(beanAttrKey.contains("_")){
                        System.out.println("-----类的属性不能包含下划线，大写代替下划线:"+beanAttrKey);
                    }
                    beanField = mapKeyToField(mapAttrKey);
                    if(beanField.equals(beanAttrKey)){//mapkey下划线转字母+mapkey大写转小写+小写,与bean原始属性进行比较
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    Object objectValue = entry.getValue();//11，默认全是String
                    if(objectValue!=null){
                        //System.out.println("-----entry.getKey:"+mapAttrKey);
                        //System.out.println("-----objectValue:"+objectValue.toString());
                        Field field = bean.getClass().getDeclaredField(beanField);//公私有字段,不包括继承字段，mapAttrKey值可以为空，但必须包含在实体类的bean中，否则抛异常,中止执行！
                        if(field!=null){
                            field.setAccessible(true);//设置可以访问和修改私有属性，包括final?
                            //System.out.println("-----field.getName():"+field.getName());
                            //System.out.println("-----fieldType.getSimpleName():"+fieldType.getSimpleName());//不能用于类型转换(short)1
                            //Object fieldParam = field.get(bean);//属性值不能为空才能返回其Object类型，再使用instanceof判断。
                            field.set(bean, valToBeanVal(bean,beanField,objectValue));
                            field.setAccessible(false);
                        }
                        else{
                            System.out.println("-----map中的key:"+field+"不在实体属性中");
                        }
                    }
                }
                else {
                    //params.remove(mapAttrKey);//可要可不要。
                }
            }
        }
        catch (NoSuchFieldException e){
            System.out.println("-----params的key不在bean中");
            e.printStackTrace();
        }
        catch (IllegalAccessException e){
            System.out.println("-----非法入参");
            e.getMessage();
        }
        System.out.println("-----bean:"+bean);
        return bean;
    }
    //实体对象bean转Map，接收前端的对象转存到map，用于模糊查询和传jsonObject.put("params",map);给前端,注意字段大小写
    //只返回下划线+小写。不返回大写。
    public static <T> Map backMap(T bean) {
        Map map  = new HashMap();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String filedStr = field.getName();//属性名有可能是大写,而表中字段是下划线
                map.put(filedStr,field.get(bean));//默认bean中属性全小写
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("-----出现错误，返回null");
                return null;
            }
        }
        return map;
    }
    //对map的key对应bean中的类型进行判断，如果匹配到类型，把map中key的值value进行转型，并返回，能转换字符串，或没匹配到返回原型T mapAttrValue。
    //入参必须是实体类的bean，不能是Class的bean。
    public static <T> Object valToBeanVal(T bean, String beanField, T mapAttrValue) {
        if(mapAttrValue!=null&&beanField!=null&&!"".equals(beanField)&&!"".equals(mapAttrValue)){
            Class fieldType = mapAttrKeyType(bean,beanField);
            System.out.println("-----fieldType:"+fieldType);
            if(fieldType.getSimpleName().equals("String")){
                return (String)mapAttrValue;
            }
            else if(fieldType.getSimpleName().equals("Integer")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Integer.parseInt(value);
                }
                else if (mapAttrValue instanceof Integer) {//经过instanceof才能强转。
                    return (Integer)mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Boolean")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Boolean.parseBoolean(value);
                }
                else if (mapAttrValue instanceof Boolean) {
                    return (Boolean)mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Long")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Long.parseLong(value);
                }
                else if (mapAttrValue instanceof Long) {
                    return (Long)mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Double")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Double.parseDouble(value);
                }
                else if (mapAttrValue instanceof Double) {
                    return (Double) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Float")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Float.parseFloat(value);
                }
                else if (mapAttrValue instanceof Float) {
                    return (Float) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Date")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return StrToDate.dateFormat(value,"20",true);
                }
                else if (mapAttrValue instanceof Date) {
                    return (Date) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("BigDecimal")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return new BigDecimal(value);
                }
                else if (mapAttrValue instanceof BigDecimal) {
                    return (BigDecimal) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Short")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Short.parseShort(value);
                }
                else if (mapAttrValue instanceof Short) {
                    return (Short) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else if(fieldType.getSimpleName().equals("Byte")){
                if (mapAttrValue instanceof String) {
                    String value = (String) mapAttrValue;
                    return Byte.parseByte(value);
                }
                else if (mapAttrValue instanceof Byte) {
                    return (Byte) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
//            if(fieldType.getSimpleName().equals("class java.lang.byte[]")){
            else if(fieldType.getSimpleName().equals("byte[]")){
                if (mapAttrValue instanceof String) {
                    //直接byte[] bytes = mapAttrValue.toString().getBytes();可能编码后乱码
                    //byte[] bytes = mapAttrValue.toString().getBytes("utf-8");编码后，字节长度增加了，标记编码格式，并且抛异常
                    //推荐以下jdk7开始，无以上问题
                    return mapAttrValue.toString().getBytes(StandardCharsets.UTF_8);//
                }
                else if (mapAttrValue instanceof byte[]) {
                    return (byte[]) mapAttrValue;
                }
                else{
                    System.out.println("-----类型不匹配:"+fieldType.getSimpleName()+"!="+mapAttrValue.toString());
                    return null;
                }
            }
            else {
                System.out.println("-----没有匹配到，返回原型:"+mapAttrValue.toString());
                return mapAttrValue;
            }
        }
        else {
            return null;
        }
    }
    //返回map中key对应bean中的class类型。注意数据库中的大写在实体类里全小写(系统方法会自动忽略？)，会去掉所有的下划线_
    public static <T> Class<?> mapAttrKeyType(T bean, String beanField) {
        if(beanField!=null&&!"".equals(beanField)){
            Field field = null;
            try {
                field = bean.getClass().getDeclaredField(beanField);
                Class fieldType = field.getType();
                return fieldType;
            }
            catch (NoSuchFieldException e){
                System.out.println("-----beanField不在bean中");
                return null;
            }
        }
        else {
            return null;
        }
    }
    //用于表中有下划线_的字段，转成属性中的带大写的驼峰名。
    public static String mapKeyToField(String mapAttrKey){
        String regExp_ = "_";
        String regExpA_Z = "[A-Z]";
        int index = 0;
        String word = null;
        ArrayList arrayList = StringIndex.arrInt(mapAttrKey,regExp_);
        if(mapAttrKey.contains(regExp_)&&StringIndex.isContain(mapAttrKey,regExpA_Z)){
            System.out.println("-----map属性名不能同时包含大写加下划线:"+mapAttrKey);
        }
        if(mapAttrKey.contains(regExp_)){
            if(!arrayList.isEmpty()){
                for (int i = 0; i < arrayList.size(); i++) {
                    index = (int)arrayList.get(i);
                    word = mapAttrKey.substring(index+1,index+2);
                    String upWord = word.toUpperCase();
                    mapAttrKey = mapAttrKey.substring(0,index+1)+upWord+mapAttrKey.substring(index+2,mapAttrKey.length());
                }
            }
            mapAttrKey=mapAttrKey.replaceAll(regExp_,"");
        }
        else if(StringIndex.isContain(mapAttrKey,regExpA_Z)){
            mapAttrKey=mapAttrKey.toLowerCase();
        }
        return mapAttrKey;
    }
    //用于属性中的含大写驼峰名，转成数据表中的下线划加小写
    public static String filedToMapKey(String beanFiled){
        String regExp = "(?=[A-Z])";
        beanFiled=beanFiled.replaceAll(regExp,"_");
        return beanFiled.toLowerCase();
    }
    public static void main(String[] args){
        String w_flat_id = mapKeyToField("wF_latid");
        System.out.println("-----w_flat_id:"+w_flat_id);
//       Testtype testtype = new Testtype();
//       Map<String,Object> params = new HashMap<>();
//       House house = new House();
//       house.setHaddress("地址");
//        params.put("house",house);
//        params.put("bigint1","1");
//        params.put("integer1",12345678);
//        params.put("tint1",13);
//        params.put("mediumint1",12345678);
//        params.put("smallint1",(short)14);//数字默认是int，要转型
//        params.put("tinyint1","123");
//        params.put("numeric1","123.123");
//        params.put("decimal1","123.123");
//        params.put("double1","123.123");
//        params.put("real1","123.123");
//        params.put("float1","123.123");
//        params.put("timestamp1","Mon May 06 14:41:28 CST 2019");
//        params.put("datetime1","Mon May 06 14:41:28 CST 2019");
//        params.put("year1","Tue Jan 01 00:00:00 CST 2019");
//        params.put("date1","Mon May 06 00:00:00 CST 2019");
//        params.put("time1","Thu Jan 01 14:41:28 CST 1970");
//        params.put("varchar1","varchar");
//        params.put("char1","char");
//        params.put("tinytext1","tinytext");
//        params.put("bit1","true");
//        params.put("longtext1","longtext");
//        params.put("mediumtext1","mediumtext");
//        params.put("text1","text");
//        params.put("longblob1","0101");
//        params.put("mediumblob1","[48, 49, 49, 49]");
//        params.put("blob1","[49, 48, 48, 48]");
//        params.put("tinyblob1","[49, 48, 48, 49]");
//        params.put("varbinary1","[49, 48, 49, 48]");
//        params.put("binary1","[49, 48, 49, 49, 0, 0, 0, 0]");
//        Object object = MapToBeanUtil.backBean(testtype,params);
//        if(object instanceof Testtype){
//            Testtype testtype1 = (Testtype) MapToBeanUtil.backBean(testtype,params);
//            System.out.println("-----Map转bean成功:"+testtype1);
//        }
//        else{
//            System.out.println("-----Map转bean失败");
//        }
//        Map map = MapToBeanUtil.backMap(object);
//        System.out.println("-----map:"+map);
    }
}

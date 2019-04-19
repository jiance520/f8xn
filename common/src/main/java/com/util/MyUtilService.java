package com.util;

//说明，只需要配置，服务层替换
// String strService0 =赋值当前的内容。
//String oldObject = "Manager"; 旧对象，如果有的话，并取消后面的注释。
//String newObject = "Comments;新对象
//执行后，复制输出内容
//注意接口是Dao还是Mapper结尾，
public class MyUtilService {
//    servieReplace()的参数stringBuffer是要替换的内容，filename是模板文件的类名，newObject是新文件的类名
    public static String  servieReplace(String newObject){
        String string = "package guo.service.impl;\n" +
                "\n" +
                "import guo.dao.ManagerMapper;\n" +
                "import guo.entity.Manager;\n" +
                "import guo.service.IManagerService;\n" +
                "import org.mybatis.spring.annotation.MapperScan;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import org.springframework.transaction.annotation.Transactional;\n" +
                "\n" +
                "@Transactional\n" +
                "@MapperScan(basePackages = \"guo.dao\")\n" +
                "@Service(\"managerService\")\n" +
                "public class ManagerService implements  IManagerService{\n" +
                "    @Autowired\n" +
                "    private ManagerMapper managerMapper;\n" +
                "    @Override\n" +
                "    public int deleteByPrimaryKey(Integer mid) {\n" +
                "\n" +
                "        return managerMapper.deleteByPrimaryKey(mid);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int insert(Manager manager) {\n" +
                "\n" +
                "        return managerMapper.insert(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int insertSelective(Manager manager) {\n" +
                "        return managerMapper.insertSelective(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public Manager selectByPrimaryKey(Integer mid) {\n" +
                "        return managerMapper.selectByPrimaryKey(mid);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int updateByPrimaryKeySelective(Manager manager) {\n" +
                "        return managerMapper.updateByPrimaryKeySelective(manager);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public int updateByPrimaryKey(Manager manager) {\n" +
                "        return managerMapper.updateByPrimaryKey(manager);\n" +
                "    }\n" +
                "}\n";
        StringBuffer stringBuffer = new StringBuffer(string);

        String oldObject = "Manager";//截取ManagerService中的Manager
        String strService13 = "";//要返回的已被替换的文件内容
        String oldObjectLowerCase = oldObject.toLowerCase();
        String newObjectToLowerCase = newObject.toLowerCase();/*小写*/
//        String objectBeanService = objectToLowerCase+"Service";
//        String objectClassService = object+"Service";
//        String objectInterfaceService = "I"+newObject+"Service";
        String oldObjectMapper = oldObject+"Mapper";
        String oldObjectmapper = oldObjectLowerCase+"Mapper";
        String objectMapper = newObject+"Mapper";
        String objectmapper = newObjectToLowerCase+"Mapper";
        String strService0 = stringBuffer.toString();//读取的文件的内容
//        String strService1id = strService1.substring(0,1)+"id";
        String objectId = newObjectToLowerCase.substring(0,1)+"id";
        int index = strService0.indexOf("private");
        if(index==-1){
            int index2 = strService0.indexOf("Service {");
            String strService00 = strService0.substring(0,index2+9)+"\n\t@Autowired\n\tprivate "+objectMapper+" "+objectmapper+";"+strService0.substring(index2+9);
            String strService1 = strService00.replaceAll("guo.dao.\\S+;","guo.dao.*;\n");
            String strService2 = strService1.replaceAll("guo.entity.\\S+;\n","guo.entity.*;\n");
            String strService3 = strService2.replaceAll("import guo.service.\\S+;\n","import guo.service.*;\n");
            String strService4 = strService3.replaceAll("@Service(\"\\S*\")","@Service(\""+newObjectToLowerCase+"Service\")");
            String strService5 = strService4.replaceAll("public class \\S+ implements \\S+Service","public class "+newObject+"Service implements"+" I"+newObject+"Service");
            String strService6 = strService5.replaceAll("private \\S+ \\S+;","private "+objectMapper+" "+objectmapper+";");
            String strService7 = strService6.replaceAll(" \\S*id\\)\\s*\\{"," "+objectId+") {");
            String strService77 = strService7.replaceAll("\\(\\S*id\\)","("+objectId+")");
            String strService8 = strService77.replaceAll("return \\S*Mapper.","return "+objectmapper+".");
            String strService88 = strService8.replaceAll("return \\S*Dao.","return "+objectmapper+".");
            String strService9 = strService88.replaceAll("return 0;","return "+objectmapper+".;");
            String strService99 = strService9.replaceAll("return null;","return "+objectmapper+".;");
            String strService10 = strService99.replaceAll(oldObjectMapper,objectMapper);
            String strService11 = strService10.replaceAll(oldObjectmapper,objectmapper);
            String strService12 = strService11.replaceAll(oldObject,newObject);
            strService13 = strService12.replaceAll(oldObjectLowerCase,newObjectToLowerCase);
//            System.out.println("strService13:\n"+strService13);
        }
        else {
            String strService1 = strService0.replaceAll("guo.dao.\\S+;","guo.dao.*;\n");
            String strService2 = strService1.replaceAll("guo.entity.\\S+;\n","guo.entity.*;\n");
            String strService3 = strService2.replaceAll("import guo.service.\\S+;\n","import guo.service.*;\n");
            String strService4 = strService3.replaceAll("@Service(\"\\S*\")","@Service(\""+newObjectToLowerCase+"Service\")");
            String strService5 = strService4.replaceAll("public class \\S+ implements \\S+Service","public class "+newObject+"Service implements"+" I"+newObject+"Service");
            String strService6 = strService5.replaceAll("private \\S+ \\S+;","private "+objectMapper+" "+objectmapper+";");
            String strService7 = strService6.replaceAll(" \\S*id\\)\\s*\\{"," "+objectId+") {");
            String strService77 = strService7.replaceAll("\\(\\S*id\\)","("+objectId+")");
            String strService8 = strService77.replaceAll("return \\S*Mapper.","return "+objectmapper+".");
            String strService88 = strService8.replaceAll("return \\S*Dao.","return "+objectmapper+".");
            String strService9 = strService88.replaceAll("return 0;","return "+objectmapper+".;");
            String strService99 = strService9.replaceAll("return null;","return "+objectmapper+".;");
            String strService10 = strService99.replaceAll(oldObjectMapper,objectMapper);
            String strService11 = strService10.replaceAll(oldObjectmapper,objectmapper);
            String strService12 = strService11.replaceAll(oldObject,newObject);
            strService13 = strService12.replaceAll(oldObjectLowerCase,newObjectToLowerCase);
//            System.out.println("strService13:\n"+strService13);
        }
        return strService13;
    }
    public static void main(String[] args) {
        System.out.println("-----test:"+MyUtilService.servieReplace("Point"));;
    }
}

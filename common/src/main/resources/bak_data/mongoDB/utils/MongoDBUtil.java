package bak_data.mongoDB.utils;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("mongoDBUtil")
public class MongoDBUtil {
	private Mongo mongo = null;
    //private  String dbString = "test";//数据库名
    //private static String hostName = "127.0.0.1";
    //private  String hostName = "192.168.126.131"; //"localhost";//主机名
    //private  int port = 27017;//端口号
    //private  int poolSize = 10;//连接池大小
    @Value("${mongo.dbString}")
    private  String dbString ;

    @Value("${mongo.hostName}")
    private String hostName ;

    @Value("${mongo.port}")
    private  int port ;

    @Value("${mongo.poolSize}")
    private  int poolSize ;
    
  /*  private MongoDBUtil(){*/
  /*      */
  /*  }*/
  /*  */
  
    //获取数据库连接
    public  DB getDB(){
        if(mongo == null){
            init();
        }        
        return mongo.getDB(dbString);
    }

    //初始化数据库
    private  void init(){
        try {
            System.out.println("-----------主机名  = "+hostName+"-------端口号 ="+port);
            //实例化Mongo
            mongo = new Mongo(hostName, port);
            MongoOptions opt = mongo.getMongoOptions();
            //设置连接池大小
            opt.connectionsPerHost = poolSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.utils;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import oracle.jdbc.OracleTypes;

/**
 * @version 时间：2018年5月10日 上午10:05:42
 * 所有的类属性和方法都是静态的属性和方法,如果不初始化，都默认使用Oracle中的mytable用户中的orcl
 */
@Component
public class JdbcUtil {//工具类，针对不同的数据库，使用同样的jdbc方法。
	private static Logger logger = Logger.getLogger(JdbcUtil.class.getName());
	private static String driver = "com.mysql.jdbc.Driver";//
	private static String url = "jdbc:mysql://localhost:3306/shiro?characterEncoding=utf8";
	private static String user = "root";
	private static String password = "root";
	private static Connection conn = null;
	private static PreparedStatement pst = null;
	private static ResultSet rst = null;
	private static CallableStatement cst = null;
//	MySQL数据库的root用户的xsh数据库
//		private static String driver = "com.mysql.jdbc.Driver";//前后不能有空格
//		private static String url = "jdbc:mysql://localhost:3306/xsh";
//		private static String user= "root";
//		private static String pwd= "root";
//		private static Connection con = null;
//	static {//已经在连接中加载
//		try {
//			Class.forName(driver);
//		} catch (ClassNotFoundException e) {
//			System.out.println("加载驱动错误");
//			e.printStackTrace();
//		}
//	}
	public JdbcUtil() {
		super();
	}
//	使用构造方法初始化工具类,必须是非静态属性
    public JdbcUtil(String driver, String url , String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
		logger.debug("构造一个新JdbcUtil");
    }
	//	得到连接，所有的其它方法中都调用了连接。
	public static Connection getConn(){
		if(conn == null){
			try {
				logger.debug("开始执行");
				Class cc = Class.forName(driver);
				logger.debug("加载驱动成功");
				conn = DriverManager.getConnection(url,user,password);
				logger.debug("连接成功");
			} catch (Exception e) {
				logger.debug("连接失败");
				e.printStackTrace();
			}
		}
		return conn;
	}
	  /**
     * insert update delete SQL语句的执行的统一方法
     * @param sql SQL语句
     * @param params 占位符?参数数组，若没有参数则为null
     * @return 受影响的行数
     */
	public static int executeUpdate(String sql,Object... params){//String...比Object[]更方便，直接传任意个参数
		conn = getConn();
		int affectedLine = 0;// 受影响的行数
		try {
			logger.debug("开始更新");
			pst = conn.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i+1,params[i]);
					logger.debug(params[i]);
				}
			}
	/*在此 PreparedStatement 对象中执行 SQL 语句，
            该语句必须是一个 SQL 数据操作语言（Data Manipulation Language，DML）语句，
            比如 INSERT、UPDATE 或 DELETE语句；或者是无返回内容的 SQL 语句，比如 DDL 语句。    */
			affectedLine = pst.executeUpdate();
			logger.debug("更新成功，"+affectedLine+"行受影响");
		} catch (SQLException e) {
			logger.debug("更新错误");
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return affectedLine;
	}
	/**
     * SQL 查询指定行和列的结果  ,一行单列，或一行多列。
     * @param sql SQL语句
     * @param params 参数数组，若没有参数则为null
     * @return 结果集
     */
	public static HashMap<String,Object> queryOne(String sql,Object... params){
		HashMap<String,Object> map = null;
		ResultSetMetaData rsmd = null;
		int columnCount = 0;
		conn = getConn();
		try {
			logger.debug("开始查询一个结果");
			pst = conn.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i+1, params[i]);
				}
			}
			rst = pst.executeQuery();
			rsmd = rst.getMetaData();
			columnCount = rsmd.getColumnCount();
			if(rst.next()){
				map = new HashMap<String,Object>();
				for (int i = 0; i < columnCount; i++) {
					map.put(rsmd.getColumnLabel(i+1), rst.getObject(i+1));
				}
			}
			logger.debug("查询一个结果成功");
		} catch (Exception e) {
			logger.debug("查询一个结果失败");
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return map;
	}
	/**
     * SQL 查询将查询结果直接放入ResultSet中
     * @param sql SQL语句
     * @param params 参数数组，若没有参数则为null
     * @return 结果集
     */
	public static ResultSet executeQueryRS(String sql,Object... params){
		conn = getConn();
		try {
			logger.debug("开始查询结果集");
			pst = conn.prepareStatement(sql);
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					pst.setObject(i+1,params[i]);
				}
			}
			rst = pst.executeQuery();
			logger.debug("查询结果集成功");
		} catch (SQLException e) {
			logger.debug("查询结果集失败");
			e.printStackTrace();
		}
		return rst;//不能关闭资源，否则返回的rst是null
	}
    /**
     * 获取结果集，并将结果放在List中 (重点)，达到可以在jsp中间接获取到ResultSet的集(表内容)。
     * @param sql  SQL语句
     * params  参数，没有则为null
     * @return List 结果集
     */
//	MetaData元数据
//	ResultSetMetaData存储了 ResultSet的MetaData。
//	查询表中所有的不允许为空的字段，
//List list = exectueQuery("select column_name from user_tab_columns where table_name = 'PRODUCT' and nullable = 'Y'");
//	exectueQuery("select * from emp where id=?","12");
	public static List<HashMap> exectueQuery(String sql,Object... params){
		rst = executeQueryRS(sql,params);
		ResultSetMetaData rsmd = null;
//		获取集列数
		int columnCount = 0;
		try {
			logger.debug("开始查询List集合");
			rsmd = rst.getMetaData();//获取有关ResultSet结果集数据的信息，如结果集的字段数，字段名。但不是获取数据内容。
//			通过ResultSet的元数据，获得结果集列数
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<HashMap> list = new ArrayList<HashMap>();//可根据情况，改变泛型<Object>
		try {
			//将ResultSet的结果保存到List中
			while(rst.next()){
				HashMap<String,Object> map = new HashMap<String,Object>();
				for (int i = 0; i < columnCount; i++) {
					map.put(rsmd.getColumnLabel(i+1), rst.getObject(i+1));//rsmd.getColumnLabel(i),通过元数据获取具有唯一性的字段名，rs.getObject(i)每一个字段名对应一个字段的值。
//					用getColumnLabel查出的是咱们在后面重新定义的字段名，如果没有定义就是默认字段名，并且是大写字符串！
				}
				list.add(map);//每一个map代表一行记录，把所有的记录存在list中。
			}
			logger.debug("查询List成功");
		} catch (SQLException e) {
			logger.debug("查询List失败");
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return list;
	}
	 /**
     * 存储过程带有一个输出参数的方法 ,out参数放在过程的最后一个？
     * @param sql 存储过程语句
     * @param params 参数数组
     * @param outParamPos 输出参数位置
     * @param sqlType 输出参数类型   所有的输出参数类型都是int!!??包括游标
     * @return 输出参数的值
     * Object obj = queryProcedure("{call com.queryUser(?,?)}", 2, OracleTypes.CURSOR, "www.hao123.com");
     */
	public static HashMap<String,Object> queryProcedure(String sql,int outParamPos,int sqlType,Object... params){
		HashMap<String,Object> map = null;
		ResultSetMetaData rsmd = null;
		int columnCount = 0;
		conn = getConn();
		try {
			logger.debug("开始调用过程");
			cst = conn.prepareCall(sql);//sql="{call procedurename(?,?...)}";
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					cst.setObject(i+1, params[i]);
				}
			}
			cst.registerOutParameter(outParamPos, sqlType);
//			在查询之前注册，不需要查询的结果集，所以使用无返回值的execute()方法，输出参数在callableStatement对象中。
			cst.execute();
			rst = (ResultSet)cst.getObject(outParamPos);//object可以转ResultSet，但是用ResultSet前不能关闭。
			rsmd = rst.getMetaData();
			columnCount = rsmd.getColumnCount();
			if(rst.next()){
				map = new HashMap<String,Object>();
				for (int i = 0; i < columnCount; i++) {
					map.put(rsmd.getColumnLabel(i+1), rst.getObject(i+1));
				}
			}
			logger.debug("调用过程成功");
		} catch (SQLException e) {
			logger.debug("调用过程失败");
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return map;
	}
//	调用带包的方法,注意for循环避开第一个?,cst.setObject(i+2, params[i]);必须从i+2开始！
//	函数的返回值只有一个，不像过程可以返回游标，所以函数返回的只能是一个Object.
	public static Object queryFunction(String sql,int outParamPos,int sqlType,Object... params){
		Object obj = null;
		int columnCount = 0;
		conn = getConn();
		try {
			logger.debug("开始调用函数");
			cst = conn.prepareCall(sql);//sql="{?=call functionname(?,?...)}";
			if(params!=null){
				for (int i = 0; i < params.length; i++) {
					cst.setObject(i+2, params[i]);
				}
			}
			cst.registerOutParameter(outParamPos, sqlType);
//			在查询之前注册，不需要查询的结果集，所以使用无返回值的execute()方法，输出参数在callableStatement对象中。
			cst.execute();
			obj = cst.getObject(outParamPos);//object可以转ResultSet，但是用ResultSet前不能关闭。
			logger.debug("调用函数成功");
		} catch (SQLException e) {
			logger.debug("调用函数失败");
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return obj;
	}
//	查询表允许为空的字段，请传入表名如PRODUCT，
	public static List<HashMap> nullList(String tablename){
		String sql = "select column_name from user_tab_columns where table_name = '"+tablename+"' and nullable = 'Y'";
		List<HashMap> maplist = exectueQuery(sql);
		List nullList = new ArrayList();
		for(int i =0;i<maplist.size();i++){
			maplist.get(i).get("COLUMN_NAME");
			nullList.add(maplist.get(i).get("COLUMN_NAME").toString().toLowerCase());
		}
		System.out.println("-----nullList:"+nullList);
		return null;
	}
	//	只关闭连接
	public static void closeConn(){
		if(conn != null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
//	关闭全部
	public static void closeAll(){
		if(conn!=null){
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pst!=null){
			try {
				pst.close();
				pst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(rst!=null){
			try {
				rst.close();
				rst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(cst!=null){
			try {
				cst.close();
				cst = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.debug("全部关闭成功");
	}
	public static void main(String[] args) {
		nullList("PRODUCT");
//		OracleTypes,用于向数据规定数据类型。
	}
}

package com.ibeetl.bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.SQLLoader;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.gen.GenConfig;
import org.beetl.sql.ext.gen.GenFilter;

import com.ibeetl.bbs.model.BbsUser;


public class Test {

	public static void main(String[] args) throws Exception {
		MySqlStyle style = new MySqlStyle();
		
		MySqlConnectoinSource cs = new MySqlConnectoinSource();
		SQLLoader loader = new ClasspathLoader("/sql");
		SQLManager 	sql = new SQLManager(style,loader,cs,new UnderlinedNameConversion(), new Interceptor[]{new DebugInterceptor()});
//		sql.genPojoCodeToConsole("project");
		
		
//		GenConfig cfg = new GenConfig();
//		cfg.setPreferDate(true);
//		sql.genALL("com.ibeetl.bbs.model", cfg, new GenFilter(){
//			public boolean accept(String tableName){
//				if(tableName.startsWith("bbs_message")){
//					return true;
//				}else{
//					return false;
//				}
////				 return false; //全部生成，当心覆盖
//			}
//		});
		
		
		BbsUser bbsUser = sql.unique(BbsUser.class, 1);
		System.out.println(bbsUser.getBalance());

	
	}
	
	
	static class MySqlConnectoinSource implements ConnectionSource {
		
		private Connection _getConn(){
			String driver = MysqlDBConfig.driver;
	        String dbName = MysqlDBConfig.dbName;
	        String password = MysqlDBConfig.password;
	        String userName = MysqlDBConfig.userName;
	        String url = MysqlDBConfig.url;
	        Connection conn = null;
	        try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, userName,
		                password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}

		public Connection getMaster() {
			return _getConn();
		}

		public Connection getConn(String sqlId, boolean isUpdate, String sql, List paras) {
			return _getConn();
		}

		

		public boolean isTransaction() {
			// TODO Auto-generated method stub
			return false;
		}

		public Connection getSlave() {
			return this.getMaster();
		}

		public void forceBegin(boolean isMaster) {
			// TODO Auto-generated method stub
			
		}

		public void forceEnd() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Connection getMetaData() {
			// TODO Auto-generated method stub
			return null;
		}

		
		

	}
	
	static class MysqlDBConfig {
		public static String driver = "com.mysql.jdbc.Driver";
	    public static String dbName = "bbs";
		public static String password = "123456";
		public static String userName = "root";
		public static String url = "jdbc:mysql://127.0.0.1:3306/" + dbName+"?useUnicode=true&characterEncoding=UTF-8";

	}


}

package com.gs.dao;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * @author gaoshen
 * 
 *         dbcp 瀹炵敤绫伙紝鎻愪緵浜哾bcp杩炴帴锛屼笉鍏佽缁ф壙锛?
 * 
 *         璇ョ被闇?鏈変釜鍦版柟鏉ュ垵濮嬪寲 DS 锛岄?杩囪皟鐢╥nitDS
 *         鏂规硶鏉ュ畬鎴愶紝鍙互鍦ㄩ?杩囪皟鐢ㄥ甫鍙傛暟鐨勬瀯閫犲嚱鏁板畬鎴愯皟鐢紝鍙互鍦ㄥ叾瀹冪被涓皟鐢紝涔熷彲浠ュ湪鏈被涓姞涓?釜static{}鏉ュ畬鎴愶紱
 */
public final class DbcpBean implements Closeable{
	private static DbcpBean dbcpBean = null;

	/** 鏁版嵁婧?static */
	private static DataSource DS;

	/** 鑾峰緱鏁版嵁婧愯繛鎺ョ姸鎬?*/
	protected static Map<String, Integer> getDataSourceStats()
			throws SQLException {
		BasicDataSource bds = (BasicDataSource) DS;
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("active_number", bds.getNumActive());
		map.put("idle_number", bds.getNumIdle());
		return map;
	}

	/**
	 * 鍒涘缓鏁版嵁婧愶紝闄や簡鏁版嵁搴撳锛岄兘浣跨敤纭紪鐮侀粯璁ゅ弬鏁帮紱
	 * 
	 * @param connectURI
	 *            鏁版嵁搴?
	 * @return
	 */
	protected static void initDS(String connectURI) {
		initDS(connectURI, "root", "zhouking", "com.mysql.jdbc.Driver", 5, 100,
				30, 10000);
	}

	/**
	 * 鎸囧畾鎵?湁鍙傛暟杩炴帴鏁版嵁婧?
	 * 
	 * @param connectURI
	 *            鏁版嵁搴?
	 * @param username
	 *            鐢ㄦ埛鍚?
	 * @param pswd
	 *            瀵嗙爜
	 * @param driverClass
	 *            鏁版嵁搴撹繛鎺ラ┍鍔ㄥ悕
	 * @param initialSize
	 *            鍒濆杩炴帴姹犺繛鎺ヤ釜鏁?
	 * @param maxActive
	 *            鏈?ぇ婵?椿杩炴帴鏁?
	 * @param maxIdle
	 *            鏈?ぇ闂茬疆杩炴帴鏁?
	 * @param maxWait
	 *            鑾峰緱杩炴帴鐨勬渶澶х瓑寰呮绉掓暟
	 * @return
	 */
	protected static void initDS(String connectURI, String username,
			String pswd, String driverClass, int initialSize, int maxActive,
			int maxIdle, int maxWait) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClass);
		ds.setUsername(username);
		ds.setPassword(pswd);
		ds.setUrl(connectURI);
		ds.setInitialSize(initialSize); // 鍒濆鐨勮繛鎺ユ暟锛?
		ds.setMaxActive(maxActive);
		ds.setMaxIdle(maxIdle);
		ds.setMaxWait(maxWait);
		DS = ds;
	}

	/** 鍏抽棴鏁版嵁婧?*/
	protected static void shutdownDataSource() throws SQLException {
		BasicDataSource bds = (BasicDataSource) DS;
		bds.close();
	}

	/** 榛樿鐨勬瀯閫犲嚱鏁?*/
	private DbcpBean() {
	}

	/** 鏋勯?鍑芥暟锛屽垵濮嬪寲浜?DS 锛屾寚瀹?鏁版嵁搴?*/
	private DbcpBean(String connectURI) {
		initDS(connectURI);
	}

	/** 鏋勯?鍑芥暟锛屽垵濮嬪寲浜?DS 锛屾寚瀹?鎵?湁鍙傛暟 */
	private DbcpBean(String connectURI, String username, String pswd,
			String driverClass, int initialSize, int maxActive, int maxIdle,
			int maxWait) {
		initDS(connectURI, username, pswd, driverClass, initialSize, maxActive,
				maxIdle, maxWait);
	}

	/** 浠庢暟鎹簮鑾峰緱涓?釜杩炴帴 */
	protected Connection getConn() {
		try {
			return DS.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected synchronized static DbcpBean getInstance() {
		if (dbcpBean == null) {
			dbcpBean = new DbcpBean("jdbc:mysql://localhost:3306/sso");
		}
		return dbcpBean;
	}

	public void close() throws IOException {
		try {
			shutdownDataSource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DbcpBean db = new DbcpBean("jdbc:mysql://localhost:3306/sso");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = db.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from user limit 1 ");
			System.out.println("Results:");
			int numcols = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= numcols; i++) {
					System.out.print("\t" + rs.getString(i) + "\t");
				}
				System.out.println("");
			}
			System.out.println(getDataSourceStats());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				if (db != null)
					shutdownDataSource();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	

}

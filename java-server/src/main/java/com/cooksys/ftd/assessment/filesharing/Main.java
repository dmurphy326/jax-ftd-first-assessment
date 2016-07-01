package com.cooksys.ftd.assessment.filesharing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);

	// MySQL connection info
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/awesome_schema";
	private static String username = "root";
	private static String password = "bondstone";
	
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName(driver); // register jdbc driver class
		// ExecutorService executor = Executors.newCachedThreadPool(); // initialize thread pool


		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			 UserDao ud = new UserDao(conn);
			 log.info(ud.getUserByUsername("BabyDoll").toString());
			 
			 FileDao fd = new FileDao(conn);
			 fd.getFiles(ud.getUserByUsername("BabyDoll").get());
			} catch (SQLException e) {
			log.error("Connection to MySQL failed: next stop flippin' burgers!!!", e);
		}
	}
}

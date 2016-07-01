package com.cooksys.ftd.assessment.filesharing.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.User;


public class UserDao {
	
	private Connection conn;
	private User user;

	Logger log = LoggerFactory.getLogger(UserDao.class);
		
	public UserDao(Connection conn) {
		super();
		this.conn = conn;
	}

	public void addUser(User user) throws SQLException {
		PreparedStatement insertUser = conn.prepareStatement(
				"insert into user (username, password) values(?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
		
		insertUser.setString(1, user.getUsername());
		insertUser.setString(2, user.getPassword());
		
		insertUser.executeUpdate();
		
		ResultSet userKeys = insertUser.getGeneratedKeys();
		userKeys.next();
		userKeys.getInt(1);
	}
	
	public Optional<User> getUserByUsername(String username) throws SQLException {
		
		Integer userID = null;
		String usernameSQL = null;
		String hashedWord = null;
		Optional<User> userOptional = Optional.empty();

		String sql = "select * from user where username = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();

		log.info("Retrieving user info...");
		while (rs.next()) {				
			userID = rs.getInt("user_id");
			usernameSQL = rs.getString("username");
			hashedWord = rs.getString("password");
			
			log.info("User Object: {} retrieved", usernameSQL);		
		}
		
		user = new User(userID, usernameSQL, hashedWord);
		userOptional = Optional.of(user);
		return userOptional;
	}
		
	public Connection getConnection() {
		return conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}


}

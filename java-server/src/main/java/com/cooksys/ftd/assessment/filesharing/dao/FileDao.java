package com.cooksys.ftd.assessment.filesharing.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.File;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

public class FileDao {

	private Connection conn;
	private File file;
	private List<File> fileList = new ArrayList<File>();

	Logger log = LoggerFactory.getLogger(FileDao.class);
	
	public FileDao(Connection conn) {
		super();
		this.conn = conn;
	}

	/**
	 * 
	 * @param user
	 *            object
	 * @return list of files that are associate with a particular user
	 * @throws SQLException
	 */
	public List<File> getFiles(User user) throws SQLException {

		Integer fileID = null;
		Integer userID = user.getUserId();
		String absolutePath = null;
		Blob fileData = null;

		String sql = "select * from file where user_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userID);
		ResultSet rs = stmt.executeQuery();

		log.info("Retrieving file(s)...");
		while (rs.next()) {
			fileID = rs.getInt("file_id");
			userID = rs.getInt("user_id");
			absolutePath = rs.getString("absolute_path");
			fileData = rs.getBlob("file_data");

			file = new File(fileID, userID, absolutePath, fileData);
			fileList.add(file);
		}
		
		if(fileList.isEmpty()) 
		{
			log.info("Retrieved 0 file(s) from user: {}", user.getUsername());
		} else {
			log.info("Retrieved {} file(s) from user: {}", fileList.size(), user.getUsername());
		}

		
		return fileList;
	}

	/**
	 * 
	 * @param user
	 *            object
	 * @param file
	 *            object
	 * 
	 *            adds files that are associated with a particular user and add
	 *            them to the database
	 * @throws SQLException
	 */
	public void addFile(User user, File file) throws SQLException {
		Integer userID = user.getUserId();

		PreparedStatement insertFile = conn.prepareStatement(
				"insert into file (user_id, absolute_path, file_data) values(?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
		
		insertFile.setInt(1, userID);
		insertFile.setString(2, file.getAbsolutePath());
		insertFile.setBlob(3, file.getFileData());
		
		insertFile.executeUpdate();

		ResultSet fileKeys = insertFile.getGeneratedKeys();
		fileKeys.next();
		fileKeys.getInt(1); 
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}

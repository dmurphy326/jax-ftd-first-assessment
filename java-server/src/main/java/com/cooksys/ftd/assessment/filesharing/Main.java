package com.cooksys.ftd.assessment.filesharing;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.User;


public class Main {

	private static Logger log = LoggerFactory.getLogger(Main.class);

	// MySQL connection info
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/awesome_schema";
	private static String username = "root";
	private static String password = "bondstone";
	

	
	public static void main(String[] args) throws ClassNotFoundException, JAXBException{
		Class.forName(driver); // register jdbc driver class
		// ExecutorService executor = Executors.newCachedThreadPool(); // initialize thread pool
		
		
				JAXBContext jc = JAXBContext.newInstance(User.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
				unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
				StreamSource json = new StreamSource(new StringReader("{\"user\":{\"userID\":1,\"username\":\"EvilBeast\",\"password\":\"java\"}}"));
				User user = unmarshaller.unmarshal(json, User.class).getValue();
		


				// Print the employee data to console
				log.info("Username: " + user.getUsername());

		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			
						 
			} catch (SQLException e) {
			log.error("Connection to MySQL failed: next stop flippin' burgers!!!", e);
		}
	}
}

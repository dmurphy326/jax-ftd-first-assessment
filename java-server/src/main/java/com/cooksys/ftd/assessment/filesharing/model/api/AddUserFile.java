package com.cooksys.ftd.assessment.filesharing.model.api;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.model.db.File;
import com.cooksys.ftd.assessment.filesharing.model.db.User;

/**
 * 
 * this class sucks and will most likely be deleted
 *
 */
public class AddUserFile {
	private File file;
	private User user;

	Logger log = LoggerFactory.getLogger(AddUserFile.class);
	
	public File jsonToFile(String jsonString) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("com.cooksys.ftd.assessment.filesharing.model.api");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		StreamSource json = new StreamSource(new StringReader(jsonString));
		file = unmarshaller.unmarshal(json, File.class).getValue();
		
		return file;
	}

	public User jsonToUser(String jsonString) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("com.cooksys.ftd.assessment.filesharing.model.api");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		StreamSource json = new StreamSource(new StringReader(jsonString));
		user = unmarshaller.unmarshal(json, User.class).getValue();
		
		log.info(user.getUsername());
		return user;
	}

}

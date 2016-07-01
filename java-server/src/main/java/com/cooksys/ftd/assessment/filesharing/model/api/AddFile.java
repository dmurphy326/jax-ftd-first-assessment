package com.cooksys.ftd.assessment.filesharing.model.api;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import com.cooksys.ftd.assessment.filesharing.model.db.User;


public class AddFile{
		
	public void objectToJSON() throws JAXBException
	{
		JAXBContext jc = JAXBContext.newInstance("com.cooksys.ftd.assessment.filesharing.model.api");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, "application/json");
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, true);
		StreamSource json = new StreamSource(new StringReader("")); //string
		User user = unmarshaller.unmarshal(json, User.class).getValue();
	}
	


}

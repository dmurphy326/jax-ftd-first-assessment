package com.cooksys.ftd.assessment.filesharing.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class ClientHandler implements Runnable,Closeable{
	
	private BufferedReader reader;
	private PrintWriter writer;
	
	private UserDao userDao;
	private FileDao fileDao;
	
	private Socket client;
	
	Logger log = LoggerFactory.getLogger(ClientHandler.class);
	
	

	public ClientHandler(Socket client) throws IOException {
		super();
		this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		this.writer = new PrintWriter(client.getOutputStream(), true);
		this.client = client;
	}

	@Override
	public void run() {
		try {
			log.info("handling client...");
			
			while (!this.client.isClosed()) {
				log.info("connected successfully to client...");
				String message = reader.readLine();
				
				if ("disconnect".equalsIgnoreCase(message)) {
					log.info("User disconnected"); // will be returned to client using Respond<T>
					this.close();
				} else {
					log.info(message); // will be returned to client using Respond<T>
				}
			}
			
		} catch (IOException | NullPointerException e) {
			log.error("Issue with connection to client", e);
		}
		
	}

	@Override
	public void close() throws IOException {
		log.info("closing connection to client {}", this.client.getRemoteSocketAddress()); // may be pointing to null... fix it
		this.client.close();		
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public FileDao getFileDao() {
		return fileDao;
	}

	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

}

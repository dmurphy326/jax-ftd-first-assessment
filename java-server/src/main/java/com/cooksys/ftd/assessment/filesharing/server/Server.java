package com.cooksys.ftd.assessment.filesharing.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cooksys.ftd.assessment.filesharing.dao.FileDao;
import com.cooksys.ftd.assessment.filesharing.dao.UserDao;

public class Server implements Runnable {

	private Logger log = LoggerFactory.getLogger(Server.class);

	private ExecutorService executor;
	private ServerSocket serverSocket;

	private UserDao userDao;
	private FileDao fileDao;

	@Override
	public void run() {
		log.info("Server started...");
		try {
			while (true) {
				Socket socket = this.serverSocket.accept();
				ClientHandler handler = this.createClientHandler(socket);
				this.executor.execute(handler);
			}
		} catch (Exception e) {
			this.log.error("The server encountered a fatal error while listening for more connections", e);
		}

	}

	public ClientHandler createClientHandler(Socket socket) throws IOException {
		ClientHandler handler = new ClientHandler();

		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		handler.setReader(reader);
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		handler.setWriter(writer);

		handler.setFileDao(fileDao);
		handler.setUserDao(userDao);

		return handler;
	}
}

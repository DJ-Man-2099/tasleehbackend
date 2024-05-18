package com.armydev.tasleehbackend;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class TasleehbackendApplication {

	@Value("${socket-server.hostname}")
	private String host;

	@Value("${socket-server.port}")
	private Integer port;

	private SocketIOServer socketIOServer;

	@PostConstruct
	public void postConstruct() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			Configuration config = new Configuration();
			config.setHostname(host);
			config.setPort(port);
			socketIOServer = new SocketIOServer(config);
			socketIOServer.start();
		} catch (IOException e) {
			System.out.println("Socket.IO server is already running.");
		}
	}

	@PreDestroy
	public void preDestroy() {
		if (socketIOServer != null) {
			socketIOServer.stop();
		}
	}

	@Bean
	public SocketIOServer socketIOServer() {
		return socketIOServer;
	}

	public static void main(String[] args) {
		SpringApplication.run(TasleehbackendApplication.class, args);
	}

}

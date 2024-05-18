package com.armydev.tasleehbackend.socketlistener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SocketServer implements CommandLineRunner {

	private final SocketIOServer server;
	private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder().build();
	public static final String guaranteeEvent = "guaranteeNotification";
	public static final String annualEvent = "annualNotification";
	public static final String notificationsEvent = "notificationsEvent";

	public SocketServer(SocketIOServer server) {
		this.server = server;

	}

	@Override
	public void run(String... args) throws Exception {
		server.start();
	}

	public void sendMessage(String message, Object data) throws JsonProcessingException {
		var jsonString = mapper.writeValueAsString(data);
		server.getBroadcastOperations().sendEvent(message, jsonString);
	}

}

package com.armydev.tasleehbackend.socketlistener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

@Component
public class SocketServer implements CommandLineRunner {

	private final SocketIOServer server;

	public SocketServer(SocketIOServer server) {
		this.server = server;
	}

	@Override
	public void run(String... args) throws Exception {
		server.start();
	}
}

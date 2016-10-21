package com.hiddenswitch.proto3.common;

import com.hiddenswitch.proto3.common.ClientToServerMessage;

import java.io.Serializable;

/**
 * A class storing the information a client needs to connect to the correct game session server and game.
 */
public class ClientConnectionConfiguration implements Serializable {
	private final String host;
	private final int port;
	private final ClientToServerMessage firstMessage;

	/**
	 * Create a new client connection configuration. This will be returned to the client from the matchmaking service
	 * and helps the client connect to the correct host and identify itself to the server.
	 * @param host {String} The hostname or IP of the game session server.
	 * @param port {int} The port to connect to.
	 * @param firstMessage {ClientToServerMessage} The message the client should send once it establishes a connection.
	 *                     This message ought to contain some kind of tokens / IDs the server can use to identify
	 *                     (1) which player this connection represents and (2) which game this player should join.
	 *                     Remember, a single server process may manage many games, so it MUST receive some identifying
	 *                     information from the client in order to sort out which client belongs to which game.
	 *                     The server CANNOT tell this information to the client first, because the matchamking service
	 *                     has decided which two players should play together, not the server.
	 */
	public ClientConnectionConfiguration(String host, int port, ClientToServerMessage firstMessage) {
		this.host = host;
		this.port = port;
		this.firstMessage = firstMessage;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public ClientToServerMessage getFirstMessage() {
		return firstMessage;
	}
}

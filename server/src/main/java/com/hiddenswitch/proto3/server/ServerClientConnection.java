package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.GameState;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;
import com.hiddenswitch.proto3.net.common.ServerToClientMessage;
import com.hiddenswitch.proto3.net.util.IncomingMessage;
import com.hiddenswitch.proto3.net.util.Serialization;
import com.hiddenswitch.proto3.net.util.VertxBufferOutputStream;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.events.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerClientConnection implements RemoteUpdateListener {
	private NetSocket privateSocket;

	ServerClientConnection(NetSocket socket) {
		this.setPrivateSocket(socket);
	}

	private void sendMessage(ServerToClientMessage message) {
		try {
			sendMessage(getPrivateSocket(), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(NetSocket socket, ServerToClientMessage message) throws IOException {
		// Serialize message
		VertxBufferOutputStream out = new VertxBufferOutputStream();
		// Write the magic header
		out.getBuffer().appendBytes(IncomingMessage.MAGIC_BYTES);
		// Write an integer's worth of space
		out.getBuffer().appendInt(0);
		// Serialize the message
		int before = out.size();

		synchronized (this) {
			Serialization.serialize(message, out);
		}

		int messageSize = out.size() - before;
		// Set the second set of four bytes to the message length.
		socket.write(out.getBuffer().setInt(4, messageSize));
	}

	public void close() {
		privateSocket.close();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		sendMessage(new ServerToClientMessage(event));
	}

	@Override
	public void onGameEnd(Player winner) {
		sendMessage(new ServerToClientMessage(winner, true));

	}

	@Override
	public void setPlayers(Player localPlayer, Player remotePlayer) {
		sendMessage(new ServerToClientMessage(localPlayer, remotePlayer));
	}

	@Override
	public void onActivePlayer(Player activePlayer) {
		sendMessage(new ServerToClientMessage(activePlayer, false));
	}

	@Override
	public void onTurnEnd(Player activePlayer, int turnNumber, TurnState turnState) {
		sendMessage(new ServerToClientMessage(activePlayer, turnNumber, turnState));
	}

	@Override
	public void onUpdate(GameState state) {
		sendMessage(new ServerToClientMessage(state));
	}

	@Override
	public void onRequestAction(String id, GameState state, List<GameAction> availableActions) {
		sendMessage(new ServerToClientMessage(id, state, availableActions));
	}

	@Override
	public void onMulligan(String id, Player player, List<Card> cards) {
		sendMessage(new ServerToClientMessage(id, player, cards));
	}

	private NetSocket getPrivateSocket() {
		return privateSocket;
	}

	private void setPrivateSocket(NetSocket privateSocket) {
		this.privateSocket = privateSocket;
	}

}

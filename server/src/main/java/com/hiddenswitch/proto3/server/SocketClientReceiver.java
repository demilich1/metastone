package com.hiddenswitch.proto3.server;

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

class SocketClientReceiver implements RemoteUpdateListener {
	private NetSocket privateSocket;

	SocketClientReceiver(NetSocket socket) {
		this.setPrivateSocket(socket);
	}

	private void sendMessage(ServerToClientMessage message) {
		try {
			sendMessage(getPrivateSocket(), message);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void onUpdate(Player player1, Player player2, TurnState newState) {
		sendMessage(new ServerToClientMessage(player1, player2, newState));
	}

	@Override
	public void onRequestAction(List<GameAction> availableActions) {
		sendMessage(new ServerToClientMessage(availableActions));
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


	@Override
	public void onMulligan(Player player, List<Card> cards) {
		sendMessage(new ServerToClientMessage(player, cards));
	}

	private NetSocket getPrivateSocket() {
		return privateSocket;
	}

	private void setPrivateSocket(NetSocket privateSocket) {
		this.privateSocket = privateSocket;
	}

}

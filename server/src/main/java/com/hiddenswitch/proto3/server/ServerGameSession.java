package com.hiddenswitch.proto3.server;

import com.hiddenswitch.proto3.net.common.ClientConnectionConfiguration;
import com.hiddenswitch.proto3.net.common.ClientToServerMessage;
import com.hiddenswitch.proto3.net.common.NetworkBehaviour;
import com.hiddenswitch.proto3.net.common.RemoteUpdateListener;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerGameSession extends GameSession implements ServerCommunicationSend {
    private String host;
    private int port;
    private Lock gameSessionLock;
    SocketClientSession c1;
    SocketClientSession c2;
    SocketServerListener listener;
    boolean shouldRun = true;
    private PregamePlayerConfiguration player1;
    private PregamePlayerConfiguration player2;

    private ClientConnectionConfiguration getConfigurationFor(PregamePlayerConfiguration player) {
        // TODO: It's obviously insecure to allow the client to specify things like their player object
        return new ClientConnectionConfiguration(getHost(), getPort(),
                new ClientToServerMessage(player.getPlayer(), getGameId()));
    }

    @Override
    public ClientConnectionConfiguration getConfigurationForPlayer1() {
        return getConfigurationFor(player1);
    }

    @Override
    public ClientConnectionConfiguration getConfigurationForPlayer2() {
        return getConfigurationFor(player2);
    }

    public ServerGameSession(String host, int port, PregamePlayerConfiguration p1, PregamePlayerConfiguration p2) {
        this();
        setHost(host);
        setPort(port);
        //TODO: in PregamePlayerConfiguration should really contain a playerConfig object.
        this.player1 = p1;
        this.player2 = p2;
    }

    public ServerGameSession() {
        super(null, null);
        this.gameSessionLock = new ReentrantLock();
        this.listener = new SocketServerListener(this, gameSessionLock, getGameId());
    }


    public Lock getLock() {
        return gameSessionLock;
    }

    @Override
    public RemoteUpdateListener getPlayerListener(int player) {
        if (player == 0) {
            return c1;
        } else {
            return c2;
        }
    }

    public void registerClient(SocketClientSession client, ClientToServerMessage firstMessage) {
        if (c1 == null) {
            c1 = client;
            c1.setServerGameSession(this);
        } else {
            c2 = client;
            c2.setServerGameSession(this);
        }
        NetworkBehaviour networkBehaviour = new NetworkBehaviour(firstMessage.getPlayer1().getBehaviour());
        firstMessage.getPlayer1().setBehaviour(networkBehaviour);
        listener.onPlayerConnected(firstMessage.getPlayer1());
    }

    public void kill() {
        shouldRun = false;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketServerListener getListener() {
        return listener;
    }
}

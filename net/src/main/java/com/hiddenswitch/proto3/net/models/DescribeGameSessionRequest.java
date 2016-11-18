package com.hiddenswitch.proto3.net.models;

/**
 * Created by bberman on 11/18/16.
 */
public class DescribeGameSessionRequest {
    public DescribeGameSessionRequest() {
    }

    public DescribeGameSessionRequest(String gameId) {
        setGameId(gameId);
    }

    public String getGameId() {
        return this.gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public DescribeGameSessionRequest withGameId(String gameId) {
        setGameId(gameId);
        return this;
    }

    private String gameId;
}

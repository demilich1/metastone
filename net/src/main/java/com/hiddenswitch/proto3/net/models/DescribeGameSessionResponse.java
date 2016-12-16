package com.hiddenswitch.proto3.net.models;

import com.hiddenswitch.proto3.net.common.ServerGameContext;
import net.demilich.metastone.game.statistics.GameStatistics;
import net.demilich.metastone.game.statistics.Statistic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bberman on 11/18/16.
 */
public class DescribeGameSessionResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gameId;
    private Map<String, Object> statistics;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Map<String, Object> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Object> statistics) {
        this.statistics = statistics;
    }

    public static DescribeGameSessionResponse fromGameContext(ServerGameContext context) {
        DescribeGameSessionResponse response = new DescribeGameSessionResponse();
        response.setGameId(context.getGameId());
        response.setStatistics(new HashMap<>());
        GameStatistics statistics = context.getPlayer1().getStatistics().clone().merge(context.getPlayer2().getStatistics().clone());
        for (Map.Entry<Statistic, Object> entry : statistics.getStats().entrySet()) {
            response.getStatistics().put(entry.getKey().toString(), entry.getValue());
        }
        return response;
    }
}

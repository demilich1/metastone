package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.behaviour.Behaviour;
import net.demilich.metastone.game.behaviour.IBehaviour;
import net.demilich.metastone.game.cards.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkBehaviour extends Behaviour {
    private Logger logger = LoggerFactory.getLogger(NetworkBehaviour.class);
    private IBehaviour wrapBehaviour;

    public NetworkBehaviour(IBehaviour wrapBehaviour) {
        this.wrapBehaviour = wrapBehaviour;
    }

    @Override
    public String getName() {
        return getWrapBehaviour().getName();
    }

    @Override
    public List<Card> mulligan(GameContext context, Player player, List<Card> cards) {
        if (context instanceof ServerGameContext) {
            ServerGameContext serverContext = (ServerGameContext) context;
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            List<Card> discardedCards = new ArrayList<>();
            serverContext.mulligan(player, cards, (List<Card> c) -> {
                for (Card card : c) {
                    discardedCards.add(card);
                }
                atomicBoolean.set(true);
            });
            logger.debug("Waiting for mulligan response");
            while (!atomicBoolean.get()) {
                serverContext.cycleLock();
            }
            return discardedCards;
        } else {
            logger.debug("Requesting mulligan from wrapped behaviour.");

            return getWrapBehaviour().mulligan(context, player, cards);
        }
    }

    @Override
    public GameAction requestAction(GameContext context, Player player, List<GameAction> validActions) {
        if (context instanceof ServerGameContext) {
            ServerGameContext serverContext = (ServerGameContext) context;
            serverContext.requestAction(player, validActions);
            logger.debug("{}: Action requested from {}. {}", serverContext, player, validActions);

            while (serverContext.getPendingAction() == null) {
                serverContext.cycleLock();
            }
            logger.debug("{}: Action received from {}.", serverContext, player);

            GameAction action = serverContext.getPendingAction();
            serverContext.setPendingAction(null);
            return action;
        } else {
            logger.debug("Requesting action from wrapped behaviour. Player: {}, validActions: {}", player, validActions);
            return getWrapBehaviour().requestAction(context, player, validActions);
        }
    }

    @Override
    public void onGameOver(GameContext context, int playerId, int winningPlayerId) {
        if (context instanceof ServerGameContext) {
        	if (winningPlayerId != -1){
                ((ServerGameContext) context).sendGameOver(context.getPlayer(playerId), context.getPlayer(winningPlayerId));
        	} else {
                ((ServerGameContext) context).sendGameOver(context.getPlayer(playerId), null);
        	}
        } else {
            getWrapBehaviour().onGameOver(context, playerId, winningPlayerId);
        }
    }

    public IBehaviour getWrapBehaviour() {
        return wrapBehaviour;
    }
}

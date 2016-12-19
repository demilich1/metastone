package com.hiddenswitch.proto3.net.common;

import net.demilich.metastone.game.Environment;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.Player;
import net.demilich.metastone.game.TurnState;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.CardCollection;
import net.demilich.metastone.game.cards.costmodifier.CardCostModifier;
import net.demilich.metastone.game.spells.trigger.TriggerManager;
import net.demilich.metastone.game.targeting.IdFactory;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;

	public final Player player1;
	public final Player player2;
	public final CardCollection tempCards;
	public final HashMap<Environment, Object> environment;
	public final List<CardCostModifier> cardCostModifiers;
	public final TriggerManager triggerManager;
	public final int currentId;
	public final TurnState turnState;
	public final long timestamp;

	public GameState(GameContext fromContext) {
		this(fromContext, fromContext.getTurnState());
	}

	public GameState(GameContext fromContext, TurnState turnState) {
		this.timestamp = System.nanoTime();
		player1 = fromContext.getPlayer1();
		player2 = fromContext.getPlayer2();
		tempCards = fromContext.getTempCards();
		environment = SerializationUtils.clone(fromContext.getEnvironment());
		currentId = fromContext.getLogic().getIdFactory().getInternalId();
		triggerManager = SerializationUtils.clone(fromContext.getTriggerManager());
		cardCostModifiers = SerializationUtils.clone(new ArrayList<>(fromContext.getCardCostModifiers()));
		this.turnState = turnState;
	}

	public boolean isValid() {
		return player1 != null
				&& player2 != null
				&& environment != null
				&& triggerManager != null
				&& turnState != null;
	}
}

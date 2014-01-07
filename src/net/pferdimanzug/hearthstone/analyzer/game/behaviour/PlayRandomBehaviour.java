package net.pferdimanzug.hearthstone.analyzer.game.behaviour;

import java.util.List;
import java.util.Random;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.HeroPower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayRandomBehaviour implements IBehaviour {
	
	private static Logger logger = LoggerFactory.getLogger(PlayRandomBehaviour.class);
	
	private Player player;

	public PlayRandomBehaviour(Player player) {
		this.player = player;
	}

	@Override
	public GameAction requestAction(GameContext context) {
		for (Card card : player.getHand()) {
			if (!context.getLogic().canPlayCard(player, card)) {
				continue;
			}
			
			return card.play();
		}
		
		return tryHeroPower(context);
	}
	
	private GameAction tryHeroPower(GameContext context) {
		HeroPower heroPower = player.getHero().getHeroPower();
		if (heroPower.hasBeenUsed() || !context.getLogic().canPlayCard(player, heroPower)) {
			return null;
		}
		
		return heroPower.play();
	}

	@Override
	public Entity provideTargetFor(GameAction action, List<Entity> validTargets) {
		if (validTargets.isEmpty()) {
			return null;
		}
		
		Entity randomTarget = validTargets.get(new Random().nextInt(validTargets.size()));
		logger.debug(player.getName() + " picks random target: " + randomTarget.getName());
		return randomTarget;
	}

}

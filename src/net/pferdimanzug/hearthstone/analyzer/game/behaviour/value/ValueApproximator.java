package net.pferdimanzug.hearthstone.analyzer.game.behaviour.value;

import java.util.HashMap;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.actions.GameAction;
import net.pferdimanzug.hearthstone.analyzer.game.actions.PlayCardAction;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Card;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Execute;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Slam;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior.Whirlwind;
import net.pferdimanzug.hearthstone.analyzer.game.heroes.powers.ArmorUp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueApproximator implements IValueApproximator {
	
	private static Logger logger = LoggerFactory.getLogger(ValueApproximator.class);
	
	private final HashMap<Integer, IValueApproximator> cardValueApproximators = new HashMap<>();
	
	private final PhysicalAttackValueApproximator attackValueApproximator = new PhysicalAttackValueApproximator(); 
	private final BasicMinionValueApproximator minionValueApproximator = new BasicMinionValueApproximator();
	private final BasicWeaponValueApproximator weaponValueApproximator = new BasicWeaponValueApproximator();
	
	{
		// hero powers
		cardValueApproximators.put(new ArmorUp().getTypeId(), new ArmorUpValueApproximator());
		
		cardValueApproximators.put(new Slam().getTypeId(), new SlamValueApproximator());
		cardValueApproximators.put(new Whirlwind().getTypeId(), new WhirlwindValueApproximator());
		cardValueApproximators.put(new Execute().getTypeId(), new ExecuteValueApproximator());
	}
	
	@Override
	public float getValue(GameContext context, GameAction action, int playerId) {
		if (action instanceof PlayCardAction) {
			PlayCardAction playCardAction = (PlayCardAction) action;
			Card card = context.resolveCardReference(playCardAction.getCardReference());
			IValueApproximator approximator = cardValueApproximators.get(card.getTypeId());
			if (approximator != null) {
				return approximator.getValue(context, playCardAction, playerId);
			}
			
		}
		switch (action.getActionType()) {
		case BATTLECRY:
			break;
		case END_TURN:
			return 0;
		case EQUIP_WEAPON:
			return weaponValueApproximator.getValue(context, action, playerId);
		case HERO_POWER:
			break;
		case PHYSICAL_ATTACK:
			return attackValueApproximator.getValue(context, action, playerId);
		case SPELL:
			break;
		case SUMMON:
			return minionValueApproximator.getValue(context, action, playerId);
		case UNDEFINED:
			break;
		default:
			break;
		
		}
		logger.warn("Unable to approximate value for action {}", action);
		return 0;
	}

}

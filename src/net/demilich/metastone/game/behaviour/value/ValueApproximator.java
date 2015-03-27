package net.demilich.metastone.game.behaviour.value;

import java.util.HashMap;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.actions.GameAction;
import net.demilich.metastone.game.actions.PlayCardAction;
import net.demilich.metastone.game.cards.Card;
import net.demilich.metastone.game.cards.concrete.neutral.Spellbreaker;
import net.demilich.metastone.game.cards.concrete.neutral.TheCoin;
import net.demilich.metastone.game.cards.concrete.warrior.Execute;
import net.demilich.metastone.game.cards.concrete.warrior.Slam;
import net.demilich.metastone.game.heroes.powers.ArmorUp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValueApproximator implements IValueApproximator {
	
	private static Logger logger = LoggerFactory.getLogger(ValueApproximator.class);
	
	private final HashMap<Integer, IValueApproximator> cardValueApproximators = new HashMap<>();
	
	private final PhysicalAttackValueApproximator attackValueApproximator = new PhysicalAttackValueApproximator(); 
	private final BasicMinionValueApproximator minionValueApproximator = new BasicMinionValueApproximator();
	private final BasicWeaponValueApproximator weaponValueApproximator = new BasicWeaponValueApproximator();
	private final BattlecryValueApproximator battlecryValueApproximator = new BattlecryValueApproximator();
	
	private final Card theCoin = new TheCoin();
	
	{
		// hero powers
		cardValueApproximators.put(new ArmorUp().getTypeId(), new ArmorUpValueApproximator());
		
		cardValueApproximators.put(new Slam().getTypeId(), new SlamValueApproximator());
		cardValueApproximators.put(new Execute().getTypeId(), new ExecuteValueApproximator());
		cardValueApproximators.put(new Spellbreaker().getTypeId(), new SpellBreakerValueApproximator());
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
			return battlecryValueApproximator.getValue(context, action, playerId);
		case END_TURN:
			return 0.1f;
		case EQUIP_WEAPON:
			return weaponValueApproximator.getValue(context, action, playerId);
		case HERO_POWER:
			break;
		case PHYSICAL_ATTACK:
			return attackValueApproximator.getValue(context, action, playerId);
		case SPELL:
			PlayCardAction playCardAction = (PlayCardAction) action;
			Card card = context.resolveCardReference(playCardAction.getCardReference());
			if (card.getTypeId() == theCoin.getTypeId()) {
				// do not value playing The Coin by itself
				return -0.1f;
			}
			break;
		case SUMMON:
			return minionValueApproximator.getValue(context, action, playerId);
		case SYSTEM:
			break;
		default:
			break;
		
		}
		logger.warn("Unable to approximate value for action {}", action);
		return 0;
	}

}

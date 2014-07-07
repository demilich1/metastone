package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Actor;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.GameEventTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.TurnEndTrigger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuffSpell extends RevertableSpell {

	private static Logger logger = LoggerFactory.getLogger(BuffSpell.class);

	private int attackBonus;
	private int hpBonus;

	private IValueProvider attackValueProvider;
	private IValueProvider hpValueProvider;

	public BuffSpell(int attackBonus) {
		this(attackBonus, 0);
	}

	public BuffSpell(int attackBonus, int hpBonus) {
		this(attackBonus, hpBonus, null);
	}
	
	public BuffSpell(int attackBonus, int hpBonus, GameEventTrigger removeTrigger) {
		super(removeTrigger);
		this.attackBonus = attackBonus;
		this.hpBonus = hpBonus;
	}
	
	public BuffSpell(int attackBonus, int hpBonus, boolean temporary) {
		this(attackBonus, hpBonus, temporary ? new TurnEndTrigger() : null);
	}
	
	public BuffSpell(IValueProvider attackValueProvider, IValueProvider hpValueProvider) {
		super(null);
		this.attackValueProvider = attackValueProvider;
		this.hpValueProvider = hpValueProvider;
	}

	@Override
	protected void onCast(GameContext context, Player player, Entity target) {
		if (attackValueProvider != null) {
			attackBonus = attackValueProvider.provideValue(context, player, target);
		}
		if (hpValueProvider != null) {
			hpBonus = hpValueProvider.provideValue(context, player, target);
		}
		
		logger.debug("{} gains ({})", target, attackBonus + "/" + hpBonus);
		
		Actor targetActor = (Actor) target;
		
		if (attackBonus != 0) {
			targetActor.modifyTag(GameTag.ATTACK_BONUS, +attackBonus);
		}
		if (hpBonus != 0) {
			targetActor.modifyHpBonus(+hpBonus);
		}

		super.onCast(context, player, target);
	}

	@Override
	public Spell getReverseSpell() {
		return new BuffSpell(-attackBonus, -hpBonus);
	}

}

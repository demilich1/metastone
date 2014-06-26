package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TargetPlayer;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class WarsongCommander extends MinionCard {

	public WarsongCommander() {
		super("Warsong Commander", 2, 3, Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Whenever you summon a minion with 3 or less Attack, give it Charge. ");
	}

	@Override
	public Minion summon() {
		Minion warsongCommander = createMinion();
		Spell chargeSpell = new ApplyTagSpell(GameTag.CHARGE);
		chargeSpell.setTarget(EntityReference.EVENT_TARGET);
		//TODO: check interaction with aura buffs. Currently all auras from minions which where played
		// before the Warsong Commander will also be applied before. Comments on Hearthhead.com state
		// that auras are ALWAYS applied before other spell triggers, no matter when they were played.
		// Need to check this
		SpellTrigger trigger = new SpellTrigger(new BelowThreeAttackTrigger(), chargeSpell);
		warsongCommander.setSpellTrigger(trigger);
		return warsongCommander;
	}
	
	private class BelowThreeAttackTrigger extends MinionSummonedTrigger {
		
		public BelowThreeAttackTrigger() {
			super(TargetPlayer.SELF);
		}

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}
			SummonEvent summonEvent = (SummonEvent) event;
			if (summonEvent.getMinion().isDead()) {
				return false;
			}
			return summonEvent.getMinion().getAttack() <= 3;
		}
		
	}

}

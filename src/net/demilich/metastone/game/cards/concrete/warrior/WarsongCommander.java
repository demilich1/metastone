package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.SummonEvent;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class WarsongCommander extends MinionCard {

	public WarsongCommander() {
		super("Warsong Commander", 2, 3, Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Whenever you summon a minion with 3 or less Attack, give it Charge. ");
	}

	@Override
	public int getTypeId() {
		return 382;
	}
	
	@Override
	public Minion summon() {
		Minion warsongCommander = createMinion();
		SpellDesc chargeSpell = AddAttributeSpell.create(EntityReference.EVENT_TARGET, GameTag.CHARGE);
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

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.events.GameEvent;
import net.pferdimanzug.hearthstone.analyzer.game.events.SummonEvent;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.desc.SpellDesc;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.MinionSummonedTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.spells.trigger.SpellTrigger;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;

public class Undertaker extends MinionCard {

	public Undertaker() {
		super("Undertaker", 1, 2, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Whenever you summon a minion with Deathrattle, gain +1/+1.");
	}

	@Override
	public int getTypeId() {
		return 401;
	}

	@Override
	public Minion summon() {
		Minion undertaker = createMinion();
		SpellDesc buffSpell = BuffSpell.create(1, 1);
		buffSpell.setTarget(EntityReference.SELF);
		SpellTrigger trigger = new SpellTrigger(new MinionWithDeathRattleSummonedTrigger(), buffSpell);
		undertaker.setSpellTrigger(trigger);
		return undertaker;
	}

	private class MinionWithDeathRattleSummonedTrigger extends MinionSummonedTrigger {

		@Override
		public boolean fire(GameEvent event, Entity host) {
			if (!super.fire(event, host)) {
				return false;
			}

			SummonEvent summonEvent = (SummonEvent) event;
			return summonEvent.getMinion().hasTag(GameTag.DEATHRATTLES);
		}

	}
}

package net.demilich.metastone.game.cards.concrete.naxxramas;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionSummonedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class Undertaker extends MinionCard {

	public Undertaker() {
		super("Undertaker", 1, 2, Rarity.COMMON, HeroClass.ANY, 1);
		setDescription("Whenever you summon a minion with Deathrattle, gain +1 Attack");
	}

	@Override
	public int getTypeId() {
		return 401;
	}

	@Override
	public Minion summon() {
		Minion undertaker = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 1, 0);
		// set requiredAttribute to DEATHRATTLE
		SpellTrigger trigger = new SpellTrigger(new MinionSummonedTrigger(null), buffSpell);
		undertaker.setSpellTrigger(trigger);
		return undertaker;
	}

	
}

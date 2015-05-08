package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.spells.trigger.MinionDeathTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;
import net.demilich.metastone.game.targeting.EntityReference;

public class FlesheatingGhoul extends MinionCard {

	public FlesheatingGhoul() {
		super("Flesheating Ghoul", 2, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Whenever a minion dies, gain +1 Attack. ");
	}

	@Override
	public int getTypeId() {
		return 128;
	}

	@Override
	public Minion summon() {
		Minion flesheatingGhoul = createMinion();
		SpellDesc buffSpell = BuffSpell.create(EntityReference.SELF, 1, 0);
		SpellTrigger spellTrigger = new SpellTrigger(new MinionDeathTrigger(null), buffSpell);
		flesheatingGhoul.setSpellTrigger(spellTrigger);
		return flesheatingGhoul;
	}
}

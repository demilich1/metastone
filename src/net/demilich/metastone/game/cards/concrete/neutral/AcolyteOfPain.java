package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DrawCardSpell;
import net.demilich.metastone.game.spells.trigger.DamageReceivedTrigger;
import net.demilich.metastone.game.spells.trigger.SpellTrigger;

public class AcolyteOfPain extends MinionCard {

	public AcolyteOfPain() {
		super("Acolyte of Pain", 1, 3, Rarity.COMMON, HeroClass.ANY, 3);
		setDescription("Whenever this minion takes damage, draw a card.");
	}

	@Override
	public int getTypeId() {
		return 79;
	}

	@Override
	public Minion summon() {
		Minion acolyteOfPain = createMinion();
		SpellTrigger trigger = new SpellTrigger(new DamageReceivedTrigger(null), DrawCardSpell.create());
		acolyteOfPain.setSpellTrigger(trigger);
		return acolyteOfPain;
	}
}

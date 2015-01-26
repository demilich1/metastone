package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ShadowBolt extends SpellCard {

	public ShadowBolt() {
		super("Shadow Bolt", Rarity.FREE, HeroClass.WARLOCK, 3);
		setDescription("Deal $4 damage to a minion.");
		setSpell(DamageSpell.create(4));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 350;
	}
}

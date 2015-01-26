package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class InnerRage extends SpellCard {

	public InnerRage() {
		super("Inner Rage", Rarity.COMMON, HeroClass.WARRIOR, 0);
		setDescription("Deal $1 damage to a minion and give it +2 Attack.");
		setSpell(MetaSpell.create(DamageSpell.create(1), BuffSpell.create(2)));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 374;
	}
}

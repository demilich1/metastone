package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.ComboSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ColdBlood extends SpellCard {

	public ColdBlood() {
		super("Cold Blood", Rarity.COMMON, HeroClass.ROGUE, 1);
		setDescription("Give a minion +2 Attack. Combo: +4 Attack instead.");
		SpellDesc comboSpell = ComboSpell.create(BuffSpell.create(2, 0), BuffSpell.create(4, 0));
		setSpell(comboSpell);
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 289;
	}
}

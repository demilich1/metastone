package net.demilich.metastone.game.cards.concrete.warrior;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.desc.SpellDesc;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Charge extends SpellCard {

	public Charge() {
		super("Charge", Rarity.FREE, HeroClass.WARRIOR, 3);
		setDescription("Give a friendly minion +2 Attack and Charge.");
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		SpellDesc spell = MetaSpell.create(BuffSpell.create(2), ApplyTagSpell.create(GameTag.CHARGE));
		setSpell(spell);
	}

	@Override
	public int getTypeId() {
		return 364;
	}
}

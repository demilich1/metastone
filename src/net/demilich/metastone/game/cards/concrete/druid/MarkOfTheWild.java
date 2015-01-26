package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.spells.BuffSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class MarkOfTheWild extends SpellCard {

	public MarkOfTheWild() {
		super("Mark of the Wild", Rarity.FREE, HeroClass.DRUID, 2);
		setDescription("Give a minion Taunt and +2/+2. (+2 Attack/+2 Health)");
		setSpell(MetaSpell.create(BuffSpell.create(2, 2), ApplyTagSpell.create(GameTag.TAUNT)));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 13;
	}
}

package net.demilich.metastone.game.cards.concrete.druid;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.cards.concrete.tokens.druid.ForceOfNatureTreant;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ForceOfNature extends SpellCard {

	public ForceOfNature() {
		super("Force of Nature", Rarity.EPIC, HeroClass.DRUID, 6);
		setDescription("Summon three 2/2 Treants with Charge that die at the end of the turn.");

		//TODO: check if this card can be played when board is full
		setSpell(SummonSpell.create(new ForceOfNatureTreant(), new ForceOfNatureTreant(), new ForceOfNatureTreant()));
		setTargetRequirement(TargetSelection.NONE);
	}

	@Override
	public int getTypeId() {
		return 7;
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.druid.ForceOfNatureTreant;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

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

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.IAmMurlocSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class IAmMurloc extends SpellCard {

	public IAmMurloc() {
		super("I Am Murloc", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Summon three, four, or five 1/1 Murlocs.");

		setSpell(IAmMurlocSpell.create());
		setTargetRequirement(TargetSelection.NONE);

		setCollectible(false);
	}

	@Override
	public int getTypeId() {
		return 465;
	}
}

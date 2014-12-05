package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class RustyHorn extends SpellCard {

	public RustyHorn() {
		super("Rusty Horn", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a minion Taunt.");
		
		setSpell(ApplyTagSpell.create(GameTag.TAUNT));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 587;
	}
}

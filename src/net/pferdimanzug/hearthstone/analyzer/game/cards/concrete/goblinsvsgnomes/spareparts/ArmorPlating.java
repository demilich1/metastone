package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.spareparts;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ArmorPlating extends SpellCard {

	public ArmorPlating() {
		super("Armor Plating", Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Give a minion +1 Health.");
		
		setSpell(BuffSpell.create(0, 1));
		setTargetRequirement(TargetSelection.MINIONS);
		
		setCollectible(false);
	}



	@Override
	public int getTypeId() {
		return 583;
	}
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.mage;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.mage.Sheep;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.TransformMinionSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Polymorph extends SpellCard {

	public Polymorph() {
		super("Polymorph", Rarity.FREE, HeroClass.MAGE, 4);
		setDescription("Transform a minion into a 1/1 Sheep.");
		setTargetRequirement(TargetSelection.MINIONS);
		setSpell(TransformMinionSpell.create(new Sheep()));
	}
	
	@Override
	public int getTypeId() {
		return 70;
	}
}

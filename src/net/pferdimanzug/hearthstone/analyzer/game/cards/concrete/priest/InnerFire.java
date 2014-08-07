package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.InnerFireSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class InnerFire extends SpellCard {

	public InnerFire() {
		super("Inner Fire", Rarity.COMMON, HeroClass.PRIEST, 1);
		setDescription("Change a minion's Attack to be equal to its Health.");
		setSpell(InnerFireSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 266;
	}
	
}

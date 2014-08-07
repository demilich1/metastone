package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.DivineSpiritSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DivineSpirit extends SpellCard {

	public DivineSpirit() {
		super("Divine Spirit", Rarity.FREE, HeroClass.PRIEST, 2);
		setDescription("Double a minion's Health.");
		setSpell(DivineSpiritSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 262;
	}
	
}

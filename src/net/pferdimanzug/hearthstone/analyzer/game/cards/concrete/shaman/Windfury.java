package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.WindfurySpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Windfury extends SpellCard {

	public Windfury() {
		super("Windfury", Rarity.FREE, HeroClass.SHAMAN, 2);
		setDescription("Give a minion Windfury.");
		setSpell(new WindfurySpell());
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 332;
	}
}

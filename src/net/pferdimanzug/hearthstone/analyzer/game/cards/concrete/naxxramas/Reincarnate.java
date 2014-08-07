package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.naxxramas;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.ReincarnateSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Reincarnate extends SpellCard {

	public Reincarnate() {
		super("Reincarnate", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Destroy a minion, then return it to life with full Health.");
		setSpell(ReincarnateSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 396;
	}



	
}

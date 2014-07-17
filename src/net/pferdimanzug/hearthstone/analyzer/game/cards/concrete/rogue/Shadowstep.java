package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ReturnMinionToHandSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Shadowstep extends SpellCard {

	public Shadowstep() {
		super("Shadowstep", Rarity.COMMON, HeroClass.ROGUE, 0);
		setDescription("Return a friendly minion to your hand. It costs (2) less.");
		setSpell(new ReturnMinionToHandSpell(-2));
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
	}



	@Override
	public int getTypeId() {
		return 303;
	}
}

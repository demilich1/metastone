package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.ApplyTagSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Charge extends SpellCard {

	public Charge() {
		super("Charge", Rarity.FREE, HeroClass.WARRIOR, 0);
		setTargetRequirement(TargetSelection.FRIENDLY_MINIONS);
		setSpell(new ApplyTagSpell(GameTag.CHARGE));
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DiscardCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Soulfire extends SpellCard {

	public Soulfire() {
		super("Soulfire", Rarity.FREE, HeroClass.WARLOCK, 0);
		setSpell(new MetaSpell(new DamageSpell(4), new DiscardCardSpell()));
		setTargetRequirement(TargetSelection.ANY);
	}

}

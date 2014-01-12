package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.priest;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class HolySmite extends SpellCard {

	public HolySmite() {
		super("Holy Smite", Rarity.FREE, HeroClass.PRIEST, 1);
		setTargetRequirement(TargetSelection.ANY);
		setSpell(new DamageSpell(2));
	}

}

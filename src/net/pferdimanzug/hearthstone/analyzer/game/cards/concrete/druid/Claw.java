package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroArmorSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroAttackSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;

public class Claw extends SpellCard {

	public Claw() {
		super("Claw", Rarity.FREE, HeroClass.DRUID, 1);
		setTargetRequirement(TargetSelection.NONE);
		setSpell(new MetaSpell(new BuffHeroAttackSpell(2), new BuffHeroArmorSpell(2)));
	}

}

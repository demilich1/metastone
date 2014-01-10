package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.actions.TargetSelection;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffHeroArmorSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;

public class ShieldBlock extends SpellCard {

	public ShieldBlock() {
		super("Shield Block", Rarity.FREE, HeroClass.WARRIOR, 3);
		setSpell(new MetaSpell(new BuffHeroArmorSpell(5), new DrawCardSpell()));
		setTargetRequirement(TargetSelection.NONE);
	}
}

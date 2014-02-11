package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warrior;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class InnerRage extends SpellCard {

	public InnerRage() {
		super("Inner Rage", Rarity.COMMON, HeroClass.WARRIOR, 0);
		setDescription("Deal $1 damage to a minion and give it +2 Attack.");
		setSpell(new MetaSpell(new DamageSpell(1), new BuffSpell(2)));
		setTargetRequirement(TargetSelection.MINIONS);
	}

}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.paladin;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SetHpSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Equality extends SpellCard {

	public Equality() {
		super("Equality", Rarity.RARE, HeroClass.PALADIN, 2);
		setDescription("Change the Health of ALL minions to 1.");
		Spell spell = new SetHpSpell(1);
		spell.setTarget(EntityReference.ALL_MINIONS);
		setSpell(spell);
		setTargetRequirement(TargetSelection.NONE);
	}

}

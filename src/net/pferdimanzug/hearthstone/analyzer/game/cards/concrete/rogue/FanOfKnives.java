package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.EntityReference;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FanOfKnives extends SpellCard {

	public FanOfKnives() {
		super("Fan of Knives", Rarity.FREE, HeroClass.ROGUE, 3);
		setDescription("Deal $1 damage to all enemy minions. Draw a card.");
		setTargetRequirement(TargetSelection.NONE);
		Spell damageSpell = new DamageSpell(1);
		damageSpell.setTarget(EntityReference.ENEMY_MINIONS);
		Spell drawCardSpell = new DrawCardSpell();
		drawCardSpell.setTarget(EntityReference.NONE);
		setSpell(new MetaSpell(damageSpell, drawCardSpell));
	}
	
}

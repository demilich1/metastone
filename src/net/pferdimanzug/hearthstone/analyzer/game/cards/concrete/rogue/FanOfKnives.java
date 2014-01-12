package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.Spell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetKey;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class FanOfKnives extends SpellCard {

	public FanOfKnives() {
		super("Fan of Knives", Rarity.FREE, HeroClass.ROGUE, 3);
		Spell damage = new DamageSpell(1);
		damage.setTarget(TargetKey.ENEMY_MINIONS);
		Spell draw = new DrawCardSpell();
		draw.setTarget(TargetKey.NONE);
		setSpell(new MetaSpell(damage, draw));
		setTargetRequirement(TargetSelection.NONE);
	}
	
}

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.druid;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Starfire extends SpellCard {

	public Starfire() {
		super("Starfire", Rarity.FREE, HeroClass.DRUID, 6);
		setDescription("Deal $5 damage.");
		setSpell(new MetaSpell(new DamageSpell(5), new DrawCardSpell()));
		setTargetRequirement(TargetSelection.ANY);
	}

}

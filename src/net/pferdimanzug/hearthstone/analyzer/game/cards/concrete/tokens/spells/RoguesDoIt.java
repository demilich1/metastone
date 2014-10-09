package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MetaSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class RoguesDoIt extends SpellCard {

	public RoguesDoIt() {
		super("Rogues Do it...", Rarity.FREE, HeroClass.ANY, 4);
		setDescription("Deal 4 damage. Draw a card.");
		setCollectible(false);

		setSpell(MetaSpell.create(DamageSpell.create(4), DrawCardSpell.create()));
		setTargetRequirement(TargetSelection.ANY);
	}

}
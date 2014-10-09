package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.tokens.spells;

import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DrawCardSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class ExcessManaCard extends SpellCard {

	public ExcessManaCard() {
		super("Excess Mana", Rarity.FREE, HeroClass.DRUID, 0);
		setDescription("Draw a card. (You can only have 10 Mana in your tray.)");
		setCollectible(false);
		setSpell(DrawCardSpell.create(1));
		setTargetRequirement(TargetSelection.NONE);
	}

}
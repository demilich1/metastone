package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.MinMaxDamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class Crackle extends SpellCard {

	public Crackle() {
		super("Crackle", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Deal 3-6 damage. Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);

		setSpell(MinMaxDamageSpell.create(3, 6));
		setTargetRequirement(TargetSelection.ANY);
	}

}

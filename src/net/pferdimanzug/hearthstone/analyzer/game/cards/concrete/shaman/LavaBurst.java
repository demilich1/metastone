package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.shaman;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.SpellCard;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class LavaBurst extends SpellCard {

	public LavaBurst() {
		super("Lava Burst", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Deal $5 damage. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);

		setSpell(new DamageSpell(5));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 324;
	}
}

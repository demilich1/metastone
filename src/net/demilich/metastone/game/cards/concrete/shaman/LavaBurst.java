package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LavaBurst extends SpellCard {

	public LavaBurst() {
		super("Lava Burst", Rarity.RARE, HeroClass.SHAMAN, 3);
		setDescription("Deal $5 damage. Overload: (2)");
		setTag(GameTag.OVERLOAD, 2);

		setSpell(DamageSpell.create(5));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 324;
	}
}

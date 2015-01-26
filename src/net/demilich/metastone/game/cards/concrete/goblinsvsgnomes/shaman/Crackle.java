package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.MinMaxDamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Crackle extends SpellCard {

	public Crackle() {
		super("Crackle", Rarity.COMMON, HeroClass.SHAMAN, 2);
		setDescription("Deal 3-6 damage. Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);

		setSpell(MinMaxDamageSpell.create(3, 6));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 576;
	}
}

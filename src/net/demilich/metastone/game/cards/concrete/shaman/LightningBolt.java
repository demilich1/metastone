package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class LightningBolt extends SpellCard {

	public LightningBolt() {
		super("Lightning Bolt", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Deal 3 damage. Overload: (1)");
		setTag(GameTag.OVERLOAD, 1);
		setSpell(DamageSpell.create(3));
		setTargetRequirement(TargetSelection.ANY);
	}



	@Override
	public int getTypeId() {
		return 325;
	}
}

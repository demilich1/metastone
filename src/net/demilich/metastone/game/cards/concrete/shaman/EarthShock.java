package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.spells.MetaSpell;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class EarthShock extends SpellCard {

	public EarthShock() {
		super("Earth Shock", Rarity.COMMON, HeroClass.SHAMAN, 1);
		setDescription("Silence a minion, then deal $1 damage to it.");
		setSpell(MetaSpell.create(SilenceSpell.create(), DamageSpell.create(1)));
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 316;
	}
}

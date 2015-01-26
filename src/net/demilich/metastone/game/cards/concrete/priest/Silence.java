package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Silence extends SpellCard {

	public Silence() {
		super("Silence", Rarity.COMMON, HeroClass.PRIEST, 0);
		setDescription("Silence a minion.");
		setSpell(SilenceSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}



	@Override
	public int getTypeId() {
		return 281;
	}
}

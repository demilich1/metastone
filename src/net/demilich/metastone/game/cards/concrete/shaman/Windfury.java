package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.ApplyTagSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Windfury extends SpellCard {

	public Windfury() {
		super("Windfury", Rarity.FREE, HeroClass.SHAMAN, 2);
		setDescription("Give a minion Windfury.");
		setSpell(ApplyTagSpell.create(GameTag.WINDFURY));
		setTargetRequirement(TargetSelection.MINIONS);
	}

	@Override
	public int getTypeId() {
		return 332;
	}
}

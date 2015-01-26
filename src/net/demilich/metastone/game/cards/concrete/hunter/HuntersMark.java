package net.demilich.metastone.game.cards.concrete.hunter;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.SetHpSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class HuntersMark extends SpellCard {

	public HuntersMark() {
		super("Hunter's Mark", Rarity.FREE, HeroClass.HUNTER, 0);
		setDescription("Change a minion's Health to 1.");
		setSpell(SetHpSpell.create(1));
		setTargetRequirement(TargetSelection.ENEMY_MINIONS);
	}

	@Override
	public int getTypeId() {
		return 37;
	}
}

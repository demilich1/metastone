package net.demilich.metastone.game.cards.concrete.priest;

import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.SpellCard;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.spells.custom.DivineSpiritSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DivineSpirit extends SpellCard {

	public DivineSpirit() {
		super("Divine Spirit", Rarity.FREE, HeroClass.PRIEST, 2);
		setDescription("Double a minion's Health.");
		setSpell(DivineSpiritSpell.create());
		setTargetRequirement(TargetSelection.MINIONS);
	}
	
	@Override
	public int getTypeId() {
		return 262;
	}
	
}

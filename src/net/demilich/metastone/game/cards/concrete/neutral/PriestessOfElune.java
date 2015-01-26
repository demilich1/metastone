package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.HealingSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class PriestessOfElune extends MinionCard {

	public PriestessOfElune() {
		super("Priestess of Elune", 5, 4, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Battlecry: Restore 4 Health to your hero.");
	}

	@Override
	public int getTypeId() {
		return 183;
	}



	@Override
	public Minion summon() {
		Battlecry battlecry = Battlecry.createBattlecry(HealingSpell.create(4), TargetSelection.FRIENDLY_HERO);
		Minion priestessOfElune = createMinion();
		priestessOfElune.setBattlecry(battlecry);
		return priestessOfElune;
	}
}

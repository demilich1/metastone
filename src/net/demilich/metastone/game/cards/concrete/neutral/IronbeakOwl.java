package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class IronbeakOwl extends MinionCard {

	public IronbeakOwl() {
		super("Ironbeak Owl", 2, 1, Rarity.COMMON, HeroClass.ANY, 2);
		setDescription("Battlecry: Silence a minion.");
		setRace(Race.BEAST);
	}

	@Override
	public int getTypeId() {
		return 146;
	}



	@Override
	public Minion summon() {
		Minion ironbeakOwl = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SilenceSpell.create(), TargetSelection.MINIONS);
		ironbeakOwl.setBattlecry(battlecry);
		return ironbeakOwl;
	}
}

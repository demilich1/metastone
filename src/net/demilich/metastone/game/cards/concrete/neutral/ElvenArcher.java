package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class ElvenArcher extends MinionCard {

	private static final int BATTLECRY_DAMAGE = 1;

	public ElvenArcher() {
		super("Elven Archer", 1, 1, Rarity.FREE, HeroClass.ANY, 1);
		setDescription("Battlecry: Deal 1 damage.");
	}

	@Override
	public int getTypeId() {
		return 123;
	}



	@Override
	public Minion summon() {
		Minion elvenArcher = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DamageSpell.create(BATTLECRY_DAMAGE), TargetSelection.ANY);
		elvenArcher.setBattlecry(battlecry);
		return elvenArcher;
	}
}

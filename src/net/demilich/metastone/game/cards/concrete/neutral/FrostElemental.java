package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class FrostElemental extends MinionCard {

	public FrostElemental() {
		super("Frost Elemental", 5, 5, Rarity.COMMON, HeroClass.ANY, 6);
		setDescription("Battlecry: Freeze a character.");
	}

	@Override
	public int getTypeId() {
		return 129;
	}

	@Override
	public Minion summon() {
		Minion frostElemental = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(AddAttributeSpell.create(GameTag.FROZEN), TargetSelection.ANY);
		frostElemental.setBattlecry(battlecry);
		return frostElemental;
	}
}

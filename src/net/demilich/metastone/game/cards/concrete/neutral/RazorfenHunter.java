package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.tokens.neutral.Boar;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class RazorfenHunter extends MinionCard {

	public RazorfenHunter() {
		super("Razorfen Hunter", 2, 3, Rarity.FREE, HeroClass.ANY, 3);
		setDescription("Battlecry: Summon a 1/1 Boar.");
	}

	@Override
	public int getTypeId() {
		return 189;
	}

	@Override
	public Minion summon() {
		Minion razorfenHunter = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new Boar()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		razorfenHunter.setBattlecry(battlecry);
		return razorfenHunter;
	}
}

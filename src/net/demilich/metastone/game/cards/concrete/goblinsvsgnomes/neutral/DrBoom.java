package net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.neutral;

import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.cards.concrete.goblinsvsgnomes.tokens.BoomBot;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SummonSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class DrBoom extends MinionCard {

	public DrBoom() {
		super("Dr. Boom", 7, 7, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Battlecry: Summon two 1/1 Boom Bots. WARNING: Bots may explode.");
	}

	@Override
	public int getTypeId() {
		return 509;
	}



	@Override
	public Minion summon() {
		Minion drBoom = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(SummonSpell.create(new BoomBot(), new BoomBot()), TargetSelection.NONE);
		battlecry.setResolvedLate(true);
		drBoom.setBattlecry(battlecry);
		return drBoom;
	}
}

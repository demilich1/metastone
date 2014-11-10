package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.goblinsvsgnomes.tokens.BoomBot;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.SummonSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class DrBoom extends MinionCard {

	public DrBoom() {
		super("Dr. Boom", 7, 7, Rarity.LEGENDARY, HeroClass.ANY, 7);
		setDescription("Battlecry: Summon two 1/1 Boom Bots. WARNING: Bots may explode.");
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

package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.rogue;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DamageSpell;
import net.pferdimanzug.hearthstone.analyzer.game.targeting.TargetSelection;

public class SI7Agent extends MinionCard {

	public SI7Agent() {
		super("SI:7 Agent", 3, 3, Rarity.RARE, HeroClass.ROGUE, 3);
		setDescription("Combo: Deal 2 damage.");
	}

	@Override
	public int getTypeId() {
		return 305;
	}

	@Override
	public Minion summon() {
		Minion si7Agent = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DamageSpell.create(2), TargetSelection.ANY);
		battlecry.setCondition((context, player) -> player.getHero().hasTag(GameTag.COMBO));
		si7Agent.setBattlecry(battlecry);
		return si7Agent;
	}
}

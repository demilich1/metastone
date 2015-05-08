package net.demilich.metastone.game.cards.concrete.rogue;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

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
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DamageSpell.create(2), TargetSelection.ANY);
		//battlecry.setCondition((context, player) -> player.getHero().hasStatus(GameTag.COMBO));
		si7Agent.setBattlecry(battlecry);
		return si7Agent;
	}
}

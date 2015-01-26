package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.Battlecry;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.DamageSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class StormpikeCommando extends MinionCard {
	
	public StormpikeCommando() {
		super("Stormpike Commando", 4, 2, Rarity.FREE, HeroClass.ANY, 5);
		setDescription("Battlecry: Deal 2 damage.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 207;
	}

	@Override
	public Minion summon() {
		Minion stormpikeCommando = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(DamageSpell.create(2), TargetSelection.ANY);
		stormpikeCommando.setBattlecry(battlecry);
		return stormpikeCommando;
	}
}

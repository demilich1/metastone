package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.SilenceSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Spellbreaker extends MinionCard {

	public Spellbreaker() {
		super("Spellbreaker", 4, 3, Rarity.COMMON, HeroClass.ANY, 4);
		setDescription("Battlecry: Silence a minion.");
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 203;
	}

	@Override
	public Minion summon() {
		Minion spellbreaker = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(SilenceSpell.create(), TargetSelection.MINIONS);
		spellbreaker.setBattlecry(battlecry);
		return spellbreaker;
	}
}

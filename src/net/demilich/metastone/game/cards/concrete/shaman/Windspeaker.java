package net.demilich.metastone.game.cards.concrete.shaman;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.spells.AddAttributeSpell;
import net.demilich.metastone.game.targeting.TargetSelection;

public class Windspeaker extends MinionCard {

	public Windspeaker() {
		super("Windspeaker", 3, 3, Rarity.FREE, HeroClass.SHAMAN, 4);
		setDescription("Battlecry: Give a friendly minion Windfury.");
	}

	@Override
	public int getTypeId() {
		return 333;
	}

	@Override
	public Minion summon() {
		Minion windspeaker = createMinion();
		BattlecryAction battlecry = BattlecryAction.createBattlecry(AddAttributeSpell.create(GameTag.WINDFURY), TargetSelection.FRIENDLY_MINIONS);
		windspeaker.setBattlecry(battlecry);
		return windspeaker;
	}
}

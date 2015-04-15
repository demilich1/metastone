package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DiscardSpell;

public class Doomguard extends MinionCard {

	public Doomguard() {
		super("Doomguard", 5, 7, Rarity.RARE, HeroClass.WARLOCK, 5);
		setDescription("Charge. Battlecry: Discard two random cards.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 338;
	}

	@Override
	public Minion summon() {
		Minion doomguard = createMinion(GameTag.CHARGE);
		BattlecryAction battlecry = BattlecryAction.createBattlecry(DiscardSpell.create(2));
		doomguard.setBattlecry(battlecry);
		return doomguard;
	}
}

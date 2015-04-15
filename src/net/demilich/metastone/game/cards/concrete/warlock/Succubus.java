package net.demilich.metastone.game.cards.concrete.warlock;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.DiscardSpell;

public class Succubus extends MinionCard {

	public Succubus() {
		super("Succubus", 4, 3, Rarity.FREE, HeroClass.WARLOCK, 2);
		setDescription("Battlecry: Discard a random card.");
		setRace(Race.DEMON);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 354;
	}

	@Override
	public Minion summon() {
		Minion succubus = createMinion();
		succubus.setBattlecry(BattlecryAction.createBattlecry(DiscardSpell.create()));
		return succubus;
	}
}

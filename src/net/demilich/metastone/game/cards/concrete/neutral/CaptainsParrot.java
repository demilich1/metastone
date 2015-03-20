package net.demilich.metastone.game.cards.concrete.neutral;

import net.demilich.metastone.game.GameTag;
import net.demilich.metastone.game.actions.BattlecryAction;
import net.demilich.metastone.game.cards.MinionCard;
import net.demilich.metastone.game.cards.Rarity;
import net.demilich.metastone.game.entities.heroes.HeroClass;
import net.demilich.metastone.game.entities.minions.Minion;
import net.demilich.metastone.game.entities.minions.Race;
import net.demilich.metastone.game.spells.custom.CaptainsParrotSpell;

public class CaptainsParrot extends MinionCard {

	public CaptainsParrot() {
		super("Captain's Parrot", 1, 1, Rarity.RARE, HeroClass.ANY, 2);
		setDescription("Battlecry: Put a random Pirate from your deck into your hand.");
		setRace(Race.BEAST);
		setTag(GameTag.BATTLECRY);
	}

	@Override
	public int getTypeId() {
		return 104;
	}

	@Override
	public Minion summon() {
		Minion captainsParrot = createMinion();
		captainsParrot.setBattlecry(BattlecryAction.createBattlecry(CaptainsParrotSpell.create()));
		return captainsParrot;
	}

	
}

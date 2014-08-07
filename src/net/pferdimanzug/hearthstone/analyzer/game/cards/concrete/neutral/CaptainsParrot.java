package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.custom.CaptainsParrotSpell;

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
		captainsParrot.setBattlecry(Battlecry.createBattlecry(CaptainsParrotSpell.create()));
		return captainsParrot;
	}

	
}

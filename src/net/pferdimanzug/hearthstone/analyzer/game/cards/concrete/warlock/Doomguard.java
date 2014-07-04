package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.warlock;

import net.pferdimanzug.hearthstone.analyzer.game.GameTag;
import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.DiscardCardSpell;

public class Doomguard extends MinionCard {

	public Doomguard() {
		super("Doomguard", 5, 7, Rarity.RARE, HeroClass.WARLOCK, 5);
		setDescription("Charge. Battlecry: Discard two random cards.");
		setRace(Race.DEMON);
	}

	@Override
	public Minion summon() {
		Minion doomguard = createMinion(GameTag.CHARGE);
		Battlecry battlecry = Battlecry.createBattlecry(new DiscardCardSpell(2));
		doomguard.setBattlecry(battlecry);
		return doomguard;
	}

}

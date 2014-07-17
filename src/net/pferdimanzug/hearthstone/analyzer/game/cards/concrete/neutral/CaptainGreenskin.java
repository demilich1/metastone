package net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral;

import net.pferdimanzug.hearthstone.analyzer.game.actions.Battlecry;
import net.pferdimanzug.hearthstone.analyzer.game.cards.MinionCard;
import net.pferdimanzug.hearthstone.analyzer.game.cards.Rarity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Race;
import net.pferdimanzug.hearthstone.analyzer.game.spells.BuffWeaponSpell;

public class CaptainGreenskin extends MinionCard {

	public CaptainGreenskin() {
		super("Captain Greenskin", 5, 4, Rarity.LEGENDARY, HeroClass.ANY, 5);
		setDescription("Battlecry: Give your weapon +1/+1.");
		setRace(Race.PIRATE);
	}

	@Override
	public int getTypeId() {
		return 103;
	}



	@Override
	public Minion summon() {
		Minion captainGreenskin = createMinion();
		Battlecry battlecry = Battlecry.createBattlecry(new BuffWeaponSpell(1, 1));
		battlecry.setCondition((context, player) -> player.getHero().getWeapon() != null);
		captainGreenskin.setBattlecry(battlecry);
		return captainGreenskin;
	}
}
